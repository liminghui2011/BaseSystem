package com.ocean.lmh.system.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;

import com.ocean.lmh.base.constant.AppConstant;
import com.ocean.lmh.base.constant.SqlKeyConstants;
import com.ocean.lmh.base.dao.BaseDaoInterface;
import com.ocean.lmh.base.model.entity.system.OperPrivilegeValue;
import com.ocean.lmh.base.util.AssertHelper;

/**
 * 系统菜单权限下的操作权限管理业务逻辑类，负责对操作权限的添加、修改、删除和查询等操作。
 * @author liminghui
 */
@Transactional
@Service("operPrivilegeService")
public class OperPrivilegeService {
	
	private static Logger log = LoggerFactory.getLogger(OperPrivilegeService.class);
	
	@Resource(name = "mybatisBaseDao")
	private BaseDaoInterface baseDao;
	
	@Autowired
	private Properties systemstatus;
	
	@Resource(name = "privilegeService")
	private PrivilegeService privilegeService;
	
	/**
	 * 添加一条操作权限记录信息
	 * @param privilege 父菜单权限实体记录
	 */
	@Transactional(propagation = Propagation.REQUIRED)
	public void addOperPrivilege(OperPrivilegeValue operPrivilege, Integer pId, BindingResult result)
	{
		log.debug("privilege values ::" + operPrivilege);
		if (checkIsExist(operPrivilege.getOperName(), pId, result)) {
			return;
		}
		operPrivilege.setPrivilegeId(pId);
		this.baseDao.insertData(SqlKeyConstants.OPERPRIVILEGE_INSERT, operPrivilege);
		privilegeService.setPrivilegeToMemcached();
	}
	
	/**
	 * 根据父菜单Id获取此菜单下的所有操作权限菜单记录
	 * @param privilegeId 父菜单id值
	 * @return 操作权限菜单记录集合
	 */
	@Transactional(propagation = Propagation.SUPPORTS)
	public List<OperPrivilegeValue> getAllOperPrivilegesByPid(Integer privilegeId)
	{
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("privilegeId", privilegeId);
		return this.baseDao.queryForList(SqlKeyConstants.OPERPRIVILEGE_SELECT_BYPARENTID, map);
	}
	
	/**
	 * 判断操作权限记录是否已经存在，判断条件为操作权限名为传入的name，主键不为传入的id。
	 * @param opName 操作权限名
	 * @param pId 父权限id
	 * @param result
	 * @return 存在返回true，不存在返回false
	 */
	@Transactional(propagation = Propagation.SUPPORTS)
	private boolean checkIsExist(String opName, Integer pId, BindingResult result) 
	{
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("name", opName);
		map.put("privilegeId", pId);
		List<OperPrivilegeValue> list = this.baseDao.queryForList(SqlKeyConstants.OPERPRIVILEGE_SELECT_BYNAMEANDID, map);
		if (AssertHelper.isNotEmptyCollection(list)) 
		{
			result.rejectValue("operName", "privilege.name.exists");
			return true;
		}
		return false;
	}
	
	/**
	 * 根据操作权限id获取一条操作权限记录信息
	 * @param opId 操作权限id
	 * @return 操作权限实体
	 */
	@Transactional(propagation = Propagation.SUPPORTS)
	public OperPrivilegeValue getOperPrivilegeById(Integer opId)
	{
		return this.baseDao.query(SqlKeyConstants.OPERPRIVILEGE_SELECT_BYPRIMARYKEY, opId);
	}
	
	/**
	 * 根据id更新操作权限记录的信息
	 * @param operPrivilege 待更新的操作权限实体
	 */
	@Transactional(propagation = Propagation.REQUIRED)
	public void updateOperPrivivelgeById(OperPrivilegeValue operPrivilege)
	{
		this.baseDao.updateData(SqlKeyConstants.OPERPRIVILEGE_UPDATE_BYPRIMARYKEY, operPrivilege);
		privilegeService.setPrivilegeToMemcached();
	}
	
	/**
	 * 根据id删除一条操作权限记录，并删除此操作权限在角色权限表（system_role_privilege）中的记录
	 * @param opId 操作权限id值
	 */
	@Transactional(propagation = Propagation.REQUIRED)
	public void deleteOperPrivilegeById(Integer opId,Integer privilegeId)
	{
		//删除此操作权限在角色权限关联表中的记录
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("opId", opId);
		map.put("privilegeid", privilegeId);
		this.baseDao.deleteData(SqlKeyConstants.ROLE_PRIVILEGE_DELETE, map);
		
		this.baseDao.deleteData(SqlKeyConstants.OPERPRIVILEGE_DELETE_BYPRIMARYKEY, opId);
		privilegeService.setPrivilegeToMemcached();
	}
	
	/**
	 * 获取所有的操作权限路径信息（uri）
	 * @return 权限路径集合
	 */
	@Transactional(propagation = Propagation.SUPPORTS)
	public List<String> getOperPrivilegeUriList() 
	{
		//如果从配置文件中获取不到预先配置的值则取默认值1
		Integer statusLevel = 1;
		String message = (String) systemstatus.get(AppConstant.SYSTEM_STATUS_LEVEL);
		log.debug("system level value::"+message);
		if (AssertHelper.isNotEmptyString(message)) 
		{
			statusLevel = Integer.valueOf(message);
		}
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("statusLevel", statusLevel);
		return this.baseDao.queryForList(
				SqlKeyConstants.OPERPRIVILEGE_SELECT_ALLURI,map);
	}
}
