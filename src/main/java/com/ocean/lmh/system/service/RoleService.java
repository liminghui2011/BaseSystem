package com.ocean.lmh.system.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

import com.ocean.lmh.base.constant.SqlKeyConstants;
import com.ocean.lmh.base.dao.BaseDaoInterface;
import com.ocean.lmh.base.model.entity.system.OperPrivilegeValue;
import com.ocean.lmh.base.model.entity.system.PrivilegeValue;
import com.ocean.lmh.base.model.entity.system.RolePrivilegeValue;
import com.ocean.lmh.base.model.entity.system.RoleValue;
import com.ocean.lmh.base.model.entity.system.UserRoleValue;
import com.ocean.lmh.base.model.entity.system.UserValue;
import com.ocean.lmh.base.model.request.system.RoleReq;
import com.ocean.lmh.base.model.vo.PaginationBean;
import com.ocean.lmh.base.util.AssertHelper;
import com.ocean.lmh.system.model.vo.UserInfo;

/**
 * 系统管理模块中的角色管理业务逻辑类，负责对角色在数据库的相关操作，
 * 包括角色的添加、删除、修改、查询等。
 * @author liminghui
 *
 */
@Transactional
@Repository
@Service("roleService")
public class RoleService {
	
	private static Logger log	= LoggerFactory.getLogger(RoleService.class);
	
	@Resource(name = "mybatisBaseDao")
	private BaseDaoInterface baseDao;

	@Resource(name = "privilegeService")
	private PrivilegeService	privilegeService;
	
	public RoleService() {
		System.err.println("roleservice test");
	}

	/**
	 * 可根据角色名可选的查询角色列表信息，角色名参数封装在roleReq中
	 * @param roleReq 封装查询参数的实体
	 * @return 角色列表集合
	 */
	@Transactional(propagation = Propagation.SUPPORTS)
	public PaginationBean<RoleValue> getRoleList(RoleReq roleReq) 
	{
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("name", roleReq.getName());
		PaginationBean<UserValue> pb = new PaginationBean<UserValue>(roleReq.getCurrent(), 0, roleReq.getPageSize());
        map.put("page", pb);
        return this.baseDao.queryForListPageByMap(SqlKeyConstants.ROLE_SELECT_BYPAGE,map);
	}

	/**
	 * 根据角色id获取角色相关的信息，保存此角色具备的所有权限信息
	 * @param id 角色id
	 * @return 角色信息
	 */
	@Transactional(propagation = Propagation.SUPPORTS)
	public RoleValue getRoleInfos(Integer id) 
	{
		RoleValue role = getRoleById(id);
		
		//获取此角色具备的菜单权限信息
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("id", id);
		List<PrivilegeValue> list = this.baseDao.queryForList(SqlKeyConstants.PRIVILEGE_SELECT_BYROLEID, map);
		role.setPrivileges(list);
		
		//获取此角色具备的操作权限信息
		List<OperPrivilegeValue> operList = this.baseDao.queryForList(SqlKeyConstants.OPERPRIVILEGE_SELECT_BYROLEID, map);
		List<Integer> operIds = new ArrayList<Integer>();
		for (OperPrivilegeValue oper : operList) {
			operIds.add(oper.getOpId());
		}
		role.setOperPrivilege(operIds);
		
		for (PrivilegeValue privilege : role.getPrivileges()) {
			role.getAssignedPrivilegeIds().add(privilege.getId());
		}
		return role;
	}

	/**
	 * 根据id删除角色信息记录，此删除操作包括三个步骤：1删除角色权限中间表的记录，2删除用户角色中间表的记录
	 * 3删除角色表中的记录
	 * @param id 角色id
	 */
	@Transactional(propagation = Propagation.REQUIRED)
	public void remove(Integer id) 
	{
		//先删除此角色在角色权限中间表相关联的权限记录
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("roleid", id);
		this.baseDao.deleteData(SqlKeyConstants.ROLE_PRIVILEGE_DELETE, map);
		
		//再删除此角色在用户角色中间表相关联的角色记录
		this.baseDao.deleteData(SqlKeyConstants.ROLE_USER_DELETE, map);
		
		//最后再删除此角色记录
		this.baseDao.deleteData(SqlKeyConstants.ROLE_DELETE_BYPRIMARYKEY, id);
	}
	
	/**
	 * 判断某个角色是否被某个账号在使用
	 * @param roleId 角色id
	 * @return 如果此角色被账号使用则返回true，否则返回false
	 */
	public boolean checkRoleInOperById(Integer roleId)
	{
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("roleId", roleId);
		List<UserRoleValue> list = this.baseDao.queryForList(SqlKeyConstants.ROLE_USER_SELECT, map);
		if (AssertHelper.isNotEmptyCollection(list))
		{
			return true;
		}
		return false;
	}

	/**
	 * 根据角色id更新角色信息记录
	 * @param role 待更新的角色实体
	 */
	@Transactional(propagation = Propagation.REQUIRED)
	public void update(RoleValue role) 
	{
		//删除原有的角色权限记录
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("roleid", role.getId());
		this.baseDao.deleteData(SqlKeyConstants.ROLE_PRIVILEGE_DELETE, map);
		
		//添加修改后的菜单权限记录
		List<PrivilegeValue> privileges = role.getPrivileges();
		for (PrivilegeValue privilege : privileges) 
		{
			RolePrivilegeValue rp = new RolePrivilegeValue();
			rp.setRoleId(role.getId());
			rp.setPrivilegeId(privilege.getId());
			rp.setOperId(0);			
			this.baseDao.insertData(SqlKeyConstants.ROLE_PRIVILEGE_INSERT, rp);
		}
		
		List<Integer> operPrivilegeIds = role.getOperPrivilege();
		if (AssertHelper.isNotEmptyCollection(operPrivilegeIds)) 
		{			
			//添加修改后的操作权限记录
			Map<String, Object> mapIds = new HashMap<String, Object>();
			mapIds.put("list", role.getOperPrivilege());
			List<OperPrivilegeValue> operList = this.baseDao.queryForList(SqlKeyConstants.OPERPRIVILEGE_SELECT_BYIDS, mapIds);
			for (OperPrivilegeValue oper : operList) 
			{
				RolePrivilegeValue rp = new RolePrivilegeValue();
				rp.setRoleId(role.getId());
				rp.setPrivilegeId(oper.getPrivilegeId());
				rp.setOperId(oper.getOpId());
				this.baseDao.insertData(SqlKeyConstants.ROLE_PRIVILEGE_INSERT, rp);
			}
		}		
		
		this.baseDao.updateData(SqlKeyConstants.ROLE_UPDATE_BYPRIMARYKEY, role);
	}

	/**
	 * 将所有的权限进行分组后返回，数据格式为权限组名作为键，该组下的权限集合作为值
	 * @return map map集合
	 */
	public Map<String, List<PrivilegeValue>> getPrivilegeMapByGroupName() 
	{
		List<PrivilegeValue> allPrivileges = privilegeService.getAllPrivilege();
		
		//建立唯一的权限组名的Set集合，作为下面构造下拉菜单map的key值
		Set<String> groups = new HashSet<String>();
		for (PrivilegeValue privilege : allPrivileges) 
		{
			groups.add(privilege.getGroupName());
		}
		
		//为各个权限组中设置该组的菜单权限值
		Map<String, List<PrivilegeValue>> groupMap = new HashMap<String, List<PrivilegeValue>>();
		for (String group : groups) 
		{
			groupMap.put(group, new ArrayList<PrivilegeValue>());
		}
		Map<String, Object> paramMap = new HashMap<String, Object>();
		for (PrivilegeValue privilege : allPrivileges) 
		{
			//为各个菜单权限设置其拥有的操作权限集合
			paramMap.put("privilegeId", privilege.getId());
			List<OperPrivilegeValue> operList = this.baseDao.queryForList(
					SqlKeyConstants.OPERPRIVILEGE_SELECT_BYNAMEANDID, paramMap);
			privilege.setOperPrivilege(operList);
			
			String group = privilege.getGroupName();
			if (groups.contains(privilege.getGroupName())) 
			{
				groupMap.get(group).add(privilege);
			}
		}
		
		return groupMap;
	}

	/**
	 * 添加一个角色
	 * @param role 待添加的角色实体
	 * @param result
	 * @param model 储存操作结果
	 */
	@Transactional(propagation = Propagation.REQUIRED)
	public void add(RoleValue role, BindingResult result, Model model) 
	{		
		if (checkPrivileges(role, result, model)) 
		{
			Map<String, Object> map = new HashMap<String, Object>();
			
			this.baseDao.insertData(SqlKeyConstants.ROLE_INSERT_ROLE, role);
			
			//将此角色具备的菜单权限插入到角色权限关联表中
			List<PrivilegeValue> privileges = role.getPrivileges();
			for (PrivilegeValue privilege : privileges) 
			{
				RolePrivilegeValue rp = new RolePrivilegeValue();
				rp.setRoleId(role.getId());
				rp.setPrivilegeId(privilege.getId());				
				rp.setOperId(0);				
				this.baseDao.insertData(SqlKeyConstants.ROLE_PRIVILEGE_INSERT, rp);
			}
			
			//将此角色具备的操作权限插入到角色权限关联表中
			List<Integer> idList = role.getOperPrivilege();
			if (AssertHelper.isNotEmptyCollection(idList)) 
			{
				map.put("list", idList);
				List<OperPrivilegeValue> operList = this.baseDao.queryForList(SqlKeyConstants.OPERPRIVILEGE_SELECT_BYIDS, map);
				for (OperPrivilegeValue oper : operList) 
				{
					RolePrivilegeValue rp = new RolePrivilegeValue();
					rp.setRoleId(role.getId());
					rp.setPrivilegeId(oper.getPrivilegeId());
					rp.setOperId(oper.getOpId());
					this.baseDao.insertData(SqlKeyConstants.ROLE_PRIVILEGE_INSERT, rp);
				}
			}
		}
	}

	/**
	 * 更新一个角色记录信息
	 * @param role 待更新的角色记录信息
	 * @param result
	 * @param model
	 */
	@Transactional(propagation = Propagation.REQUIRED)
	public void update(RoleValue role, BindingResult result, Model model) 
	{
		if (checkPrivileges(role, result, model)) 
		{
			update(role);
		}
	}

	/**
	 * 校验用户是否拥有权限项，并判断此角色是否存在
	 * @param role 角色实体
	 * @param result 封装错误提示信息
	 * @param model 储存数据的模型
	 * @return 具有权限且用户存在返回true，其他情况返回false
	 */
	private boolean checkPrivileges(RoleValue role, BindingResult result, Model model) 
	{
		List<Integer> assignedPrivilegeIds = role.getAssignedPrivilegeIds();
		
		if (AssertHelper.isEmptyCollection(assignedPrivilegeIds)
				|| assignedPrivilegeIds.size()<1) 
		{
			result.rejectValue("assignedPrivilegeIds", "role.privileges.required");
			return false;
		}
		
		List<PrivilegeValue> privileges = (List<PrivilegeValue>) privilegeService.findByIds(assignedPrivilegeIds);
		role.setPrivileges(privileges);
		model.addAttribute("assignedPrivilegeIds", assignedPrivilegeIds);
		model.addAttribute("groupMap", getPrivilegeMapByGroupName());

		if (existsByName(role.getName(), role.getId())) 
		{
			result.rejectValue("name", "role.name.exists");
			return false;
		}

		return true;
	}
	
	/**
	 * 根据角色id获取角色记录
	 * @param roleid 角色id
	 * @return 角色记录
	 */
	public RoleValue getRoleById(Integer roleid)
	{
		return this.baseDao.query(SqlKeyConstants.ROLE_SELECT_BYPRIMARYKEY, roleid);
	}
	
	/**
	 * 根据id和name判断此角色名是否已经存在（如：name=? and id!=?）
	 * @param name 角色名
	 * @param id 角色id
	 * @return 存在返回true，不存在返回false
	 */
    public boolean existsByName(String name,Integer id)
    {   
    	log.debug("param::"+name);
    	Map<String, Object> map = new HashMap<String, Object>();
    	if (id != null)
    	{
    		map.put("id", id);
		}
        if (AssertHelper.isNotEmptyString(name))
        {
        	map.put("name", name.trim());
		}        
        List<RoleValue> list = this.baseDao.queryForList(SqlKeyConstants.ROLE_SELECT_BYNAMEANDID, map);
        if (AssertHelper.isNotEmptyCollection(list)) 
        {
			return true;
		}
        return false;
    }
    
    /**
     * 获取所有的角色信息 
     * @return 
     */
    public List<RoleValue> getAllRoles()
    {
    	return this.baseDao.queryForList(SqlKeyConstants.ROLE_SELECT_ALLROLES, new HashMap<String, Object>());
    }
    
    /**
     * 根据操作员具备的角色id获取相应的角色名称，此操作主要用在
     * 操作员登录以及记录操作员的各种操作日志
     * @param user 操作员信息
     * @return 角色名称
     */
    public String getRoleNamesByIds(UserInfo user,HttpServletRequest request)
    {
    	String roleNames = "";
    	String roleIds = user.getRoleIds();
		log.debug("user role ids::"+roleIds);
		if (AssertHelper.isNotEmptyString(roleIds))
		{
			for (String idString : roleIds.split(",")) 
			{
				Integer id = Integer.valueOf(idString);
				RoleValue role = getRoleById(id);
				roleNames = roleNames + role.getName() + ",";
			}
			roleNames = roleNames.substring(0, roleNames.length()-1);
			request.getSession().setAttribute(user.getUserid(), roleNames);
		}
		return roleNames;
    }
}
