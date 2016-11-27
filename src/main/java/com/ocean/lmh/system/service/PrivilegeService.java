package com.ocean.lmh.system.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;

import com.ocean.lmh.base.cache.MemcachedHelper;
import com.ocean.lmh.base.constant.AppConstant;
import com.ocean.lmh.base.constant.SqlKeyConstants;
import com.ocean.lmh.base.dao.BaseDaoInterface;
import com.ocean.lmh.base.model.entity.system.PrivilegeValue;
import com.ocean.lmh.base.model.entity.system.UserValue;
import com.ocean.lmh.base.model.request.system.PrivilegeReq;
import com.ocean.lmh.base.model.vo.PaginationBean;
import com.ocean.lmh.base.util.AssertHelper;
/**
 * 系统权限管理业务类，负责对系统权限的各种操作进行响应，主要是对
 * 权限的添加、删除、修改、查询等操作提供相应的方法。
 * @author liminghui
 */
@Transactional
@Service("privilegeService")
public class PrivilegeService {

	private static Logger log = LoggerFactory.getLogger(PrivilegeService.class);

	@Resource(name = "mybatisBaseDao")
	private BaseDaoInterface baseDao;
	
	@Resource(name = "mencached")
	private MemcachedHelper memcachedHelper;
	
	@Resource(name = "operPrivilegeService")
	private OperPrivilegeService operPrivilegeService;

	/**
	 * 添加一条权限记录信息
	 * @param privilege 待添加的权限实体
	 * @param result 结果实体
	 */
	@Transactional(propagation = Propagation.REQUIRED)
	public void addPrivilege(PrivilegeValue privilege, BindingResult result) 
	{
		if (checkIsExist(privilege, result)) 
		{
			return;
		}
		this.baseDao.insertData(SqlKeyConstants.PRIVILEGE_INSERT, privilege);
		setPrivilegeToMemcached();
	}

	/**
	 * 修改一条权限记录信息
	 * @param privilege 待修改的权限实体
	 * @param result 操作结果对象
	 */
	@Transactional(propagation = Propagation.REQUIRED)
	public void updatePrivilege(PrivilegeValue privilege, BindingResult result) 
	{
		log.debug("privilege::"+privilege.toString());
		if (checkIsExist(privilege, result)) 
		{
			return;
		}
		this.baseDao.updateData(SqlKeyConstants.PRIVILEGE_UPDATE, privilege);
		setPrivilegeToMemcached();
	}

	/**
	 * 判断权限记录是否已经存在，判断条件为权限名为传入的name，主键不为传入的id。
	 * @param privilege 权限实体
	 * @param result
	 * @return 存在返回true，不存在返回false
	 */
	private boolean checkIsExist(PrivilegeValue privilege, BindingResult result) 
	{

		if (existsByName(privilege.getName(), privilege.getId())) 
		{
			result.rejectValue("name", "privilege.name.exists");
			return true;
		}
		return false;
	}

	/**
	 * 根据条件分页获取权限记录列表信息
	 * @param privilege 封装有条件的权限实体，包含[权限名name,权限分组名groupName]
	 * @return 符合条件的结果集实体
	 */
	@Transactional(propagation = Propagation.SUPPORTS)
	public PaginationBean<PrivilegeValue> privilegeList(PrivilegeReq privilege) 
	{
		Map<String, Object> map = new HashMap<String, Object>();
		if (AssertHelper.isNotEmptyString(privilege.getName())) 
		{
			map.put("name", privilege.getName());
		}
		if (AssertHelper.isNotEmptyString(privilege.getGroup())) 
		{
			map.put("groupName", privilege.getGroup());
		}
		PaginationBean<UserValue> pb = new PaginationBean<UserValue>(privilege.getCurrent(), 0, privilege.getPageSize());
        map.put("page", pb);
		return this.baseDao.queryForListPageByMap(
				SqlKeyConstants.PRIVILEGE_SELECT_BYPAGE, map);
	}

	/**
	 * 根据id删除权限记录信息
	 * @param id 权限id
	 * @param request 请求对象
	 */
	@Transactional(propagation = Propagation.REQUIRED)
	public void remove(Integer id, HttpServletRequest request) 
	{
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("privilegeid", id);
		
		//删除菜单权限与角色关联的记录
		this.baseDao.deleteData(SqlKeyConstants.ROLE_PRIVILEGE_DELETE, map);
		
		//删除此菜单权限下的所有操作权限记录
		this.baseDao.deleteData(SqlKeyConstants.OPERPRIVILEGE_DELETE_BYPRIVILEGEID, id);
		
		this.baseDao.deleteData(SqlKeyConstants.PRIVILEGE_DELETE_BYID, id);
		setPrivilegeToMemcached();
	}

	/**
	 * 获取所有的菜单权限路径信息（uri）
	 * @return 权限路径集合
	 */
	@Transactional(propagation = Propagation.SUPPORTS)
	public List<String> getPrivilegeUriList() 
	{
		return this.baseDao.queryForList(
				SqlKeyConstants.PRIVILEGE_GET_PRIVILEGEURILIST,
				new HashMap<String, Object>());
	}

	/**
	 * 根据权限id获取权限记录信息
	 * @param id 权限id
	 * @return 权限实体
	 */
	@Transactional(propagation = Propagation.SUPPORTS)
	public PrivilegeValue getPrivilege(Integer id) 
	{
		return this.baseDao.query(SqlKeyConstants.PRIVILEGE_GET_BYID, id);
	}

	/**
	 * 根据id和name判断此权限是否已经存在
	 * @param name 权限名
	 * @param id 权限id
	 * @return 存在返回true，不存在返回false
	 */
	public boolean existsByName(String name, Integer id) 
	{
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("id", id);
		map.put("name", name);
		List<PrivilegeValue> list = this.baseDao.queryForList(
				SqlKeyConstants.PRIVILEGE_SELECT_BYNAMEANDID, map);
		if (AssertHelper.isNotEmptyCollection(list)) 
		{
			return true;
		}
		return false;
	}

	/**
	 * 获取所有的权限记录，按group_name和privilege_id排序
	 * @return 所有的权限集合
	 */
	public List<PrivilegeValue> getAllPrivilege() 
	{
		return this.baseDao.queryForList(
				SqlKeyConstants.PRIVILEGE_SELECT_ORDERGROUPID,
				new HashMap<String, Object>());
	}
	
	/**
	 * 获取指定权限id集合的权限记录信息
	 * @param ids 权限id集合
	 * @return  符合条件的权限记录集
	 */
	public List<PrivilegeValue> findByIds ( List<?> ids ) {
		if (ids == null) 
		{
			return new ArrayList<PrivilegeValue>();
		} else 
		{
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("list", ids);
			return this.baseDao.queryForList(SqlKeyConstants.PRIVILEGE_SELECT_BYLISTIDS,map);
		}
	}

	/**
	 * 当添加、更新和删除菜单权限或者是操作权限时从新将修改后的权限放入缓存中
	 */
	public void setPrivilegeToMemcached()
	{
		List<String> list = getPrivilegeUriList();
		List<String> operList = operPrivilegeService.getOperPrivilegeUriList();
		list.addAll(operList);
		memcachedHelper.setValueToMemcache(AppConstant.PRIVILEGE_URI_LIST, 0, list);
	}
}
