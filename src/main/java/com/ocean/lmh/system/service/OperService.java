package com.ocean.lmh.system.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.codec.digest.DigestUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

import com.ocean.lmh.base.constant.AppConstant;
import com.ocean.lmh.base.constant.SqlKeyConstants;
import com.ocean.lmh.base.dao.BaseDaoInterface;
import com.ocean.lmh.base.model.entity.system.OperPrivilegeValue;
import com.ocean.lmh.base.model.entity.system.PrivilegeValue;
import com.ocean.lmh.base.model.entity.system.RoleValue;
import com.ocean.lmh.base.model.entity.system.UserRoleValue;
import com.ocean.lmh.base.model.entity.system.UserValue;
import com.ocean.lmh.base.model.request.system.UserReq;
import com.ocean.lmh.base.model.vo.PaginationBean;
import com.ocean.lmh.base.util.AssertHelper;
/**
 * 系统操作员管理的业务类，提供与操作员相关的各类操作方法
 * 包括获取操作员列表、添加、修改、删除、更改状态、修改密码等操作
 * @author liminghui
 */
@Transactional
@Service ( "operService" )
public class OperService {
	
	private static Logger log	= LoggerFactory.getLogger(OperService.class);

	
	@Resource(name ="roleService")
	private RoleService roleService;
	
	@Resource(name = "mybatisBaseDao")
	private BaseDaoInterface baseDao;
	
	public OperService() {
		System.err.println("OperService test");
	}
	
	/**
	 * 根据条件分页获取操作员列表信息
	 * @param userReq 操作员查询条件包含[userid,nickname,status]
	 * @param request 请求对象
	 * @return 封装好的结果集实体
	 */
	@Transactional(propagation=Propagation.SUPPORTS)
	public PaginationBean<UserValue> list(UserReq userReq, HttpServletRequest request) 
	{
		Map<String, Object> map = new HashMap<String, Object>();
		if (AssertHelper.isNotEmptyString(userReq.getName())) 
		{
			map.put("userid", userReq.getName());
		}
		if (AssertHelper.isNotEmptyString(userReq.getName())) 
		{
			map.put("nickname", userReq.getName());
		}
		if (AssertHelper.isNotEmptyString(userReq.getStatus())) 
		{
			map.put("status", userReq.getStatus());
		}
		map.put("deleteStatu", AppConstant.USER_DELETE);
		PaginationBean<UserValue> pb = new PaginationBean<UserValue>(userReq.getCurrent(), 0, userReq.getPageSize());
        map.put("page", pb);
		pb = this.baseDao.queryForListPageByMap(SqlKeyConstants.SYSTEMUSER_SELECT_PAGE, map);
		
		List<UserValue> userList = pb.getDataList();
		Map<String, Object> rolemap = new HashMap<String, Object>();
		for (UserValue userValue : userList) 
		{
			rolemap.put("id", userValue.getId());
			List<RoleValue> roleList = this.baseDao.queryForList(SqlKeyConstants.ROLE_SELECT_ROLESBYUSERID, rolemap);
			userValue.setRoles(roleList);
		}		
		pb.setDataList(userList);
		return pb;
	}

	/**
	 * 根据id获取操作员信息，包括此操作员具有的角色信息
	 * @param id 操作员id
	 * @return 操作员对象
	 */
	@Transactional(propagation = Propagation.SUPPORTS)
	public UserValue getUserHasRolesById(Integer id) 
	{
		UserValue user = getUserById(id);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("id", user.getId());
		List<RoleValue> list = this.baseDao.queryForList(SqlKeyConstants.ROLE_SELECT_ROLESBYUSERID, map);
		user.setRoles(list);
		return user;
	}

	/**
	 * 根据id删除操作员记录信息
	 * @param id 操作员id
	 */
	@Transactional(propagation=Propagation.REQUIRED)
	public void remove(Integer id) 
	{
		//先删除此用户在用户角色关联表中相关联的记录
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("userid", id);
		this.baseDao.deleteData(SqlKeyConstants.ROLE_USER_DELETE, map);
		
		updateStatus(id, AppConstant.USER_DELETE);
	}
	
	/**
	 * 根据操作员id更新操作员的状态
	 * @param id 操作员id
	 * @param status 待更新的状态
	 */
	@Transactional(propagation=Propagation.REQUIRED)
	public void updateStatus(Integer id, String status) 
	{
		UserValue user = this.baseDao.query(SqlKeyConstants.SYSTEMUSER_SELECT_BYPRIMARYKEY, id);
		user.setStatus(status);
		this.baseDao.updateData(SqlKeyConstants.SYSTEMUSER_UPDATE_BYPRIMARYKEY, user);
	}
	
	/**
	 * 初始化修改操作员密码的form表单
	 * @param id 操作员id
	 * @return 封装操作员信息的请求对象
	 */
	@Transactional(propagation=Propagation.SUPPORTS)
	public UserReq changePasswordForm(Integer id) 
	{
		UserValue dbUser = this.baseDao.query(SqlKeyConstants.SYSTEMUSER_SELECT_BYPRIMARYKEY, id);
		UserReq userReq = new UserReq();
		userReq.setUserid(dbUser.getUserid());
		userReq.setId(id);		
		return userReq;
	}
	
	/**
	 * 修改操作员的密码
	 * @param userReq 封装有新操作员密码的实体
	 */
	@Transactional(propagation=Propagation.REQUIRED)
	public void changePassword(UserReq userReq)
	{
		UserValue user = this.baseDao.query(SqlKeyConstants.SYSTEMUSER_SELECT_BYPRIMARYKEY, userReq.getId());
		String password = userReq.getNewPassword ( );
		user.setPassword(DigestUtils.md5Hex(password));
		this.baseDao.updateData(SqlKeyConstants.SYSTEMUSER_UPDATE_BYPRIMARYKEY, user);
	}
	
	/**
	 * 根据操作员id更新操作员的登录时间
	 * @param userid 操作员编号
	 */
	@Transactional(propagation=Propagation.REQUIRES_NEW)
	public void updateLoginTime ( String userid ) 
	{
		UserValue user = new UserValue();
		user.setUserid(userid);
		user.setLastLoginDate(new Date());
		this.baseDao.updateData(SqlKeyConstants.SYSTEMUSER_UPDATE_BYUSERID, user);
	}
	
	
	/**
	 * 更新操作员的基本信息，包括此操作员所拥有的角色信息
	 * @param user 待更新的操作员实体
	 */
	@Transactional(propagation=Propagation.REQUIRED)
	public void update ( UserValue user) 
	{
		UserValue dbUser = this.baseDao.query(SqlKeyConstants.SYSTEMUSER_SELECT_BYPRIMARYKEY, user.getId());
		dbUser.setRemark(user.getRemark());
	
		String city = user.getCity();
		if(AssertHelper.isNotEmptyString(city))
		{
			dbUser.setCity(city);
		}
		if (AppConstant.USER_PASSWORD_ON.equals(user.getIsreset()))
		{
			dbUser.setPassword(DigestUtils.md5Hex(AppConstant.SYSTEM_INIT_PASSWORD));
		}
		dbUser.setNickname(user.getNickname());
		//删除此用户原有的角色
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("userid", dbUser.getId());
		this.baseDao.deleteData(SqlKeyConstants.ROLE_USER_DELETE, map);
		
		//添加新选择的角色
		for (RoleValue role : user.getRoles()) 
		{
			Integer roleid = role.getId();
			if (roleid != null) 
			{
				UserRoleValue ur = new UserRoleValue();
				ur.setRoleId(role.getId());
				ur.setUserId(dbUser.getId());
				this.baseDao.insertData(SqlKeyConstants.ROLE_USER_INSERT, ur);
			}			
		}
		
		this.baseDao.updateData(SqlKeyConstants.SYSTEMUSER_UPDATE_BYPRIMARYKEY, dbUser);
	}

	
	/**
	 * 修改自己的密码信息
	 * @param userReq 封装有新密码的实体对象
	 * @param result 结果集实体
	 * @param request 请求对象
	 */
	@Transactional(propagation = Propagation.REQUIRED)
	public void changeSelfPassword(UserReq userReq , BindingResult result )
	{
		String userid = userReq.getUserid ( );
		String oldPassword = userReq.getOldPassword ( );
		String newPassword = userReq.getNewPassword ( );
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("userid", userid);
		map.put("password", DigestUtils.md5Hex(oldPassword));
		List<UserValue> list = this.baseDao.queryForList(SqlKeyConstants.SYSTEMUSER_SELECT_ALLBYPROPERTY, map);
		if ( AssertHelper.isEmptyCollection(list) )
		{
			result.rejectValue ( "oldPassword" , "user.oldpassword.mismatch" );
			return;
		}
		UserValue user = new UserValue();
		user.setUserid(userid);
		user.setPassword(DigestUtils.md5Hex(newPassword));
		this.baseDao.updateData(SqlKeyConstants.SYSTEMUSER_UPDATE_BYUSERID, user);
	}
	
	/**
	 * 根据操作员的编号和密码获取操作员记录信息
	 * @param userid 操作员编号
	 * @param password 密码
	 * @return 操作员记录
	 */
	@Transactional(propagation = Propagation.SUPPORTS)
	public UserValue getUserByUserIdAndPass(String userId, String password)
	{	
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("userid", userId);
		map.put("password", DigestUtils.md5Hex(password));
		List<UserValue> list = this.baseDao.queryForList(SqlKeyConstants.SYSTEMUSER_SELECT_ALLBYPROPERTY, map);
		UserValue user = new UserValue();
		if (AssertHelper.isNotEmptyCollection(list)) 
		{
			user = list.get(0);
			
			//获取此用户所拥有的角色
			map.clear();
			map.put("id", user.getId());
			List<RoleValue> roleList = this.baseDao.queryForList(SqlKeyConstants.ROLE_SELECT_ROLESBYUSERID, map);
			
			//获取这些角色具备的权限
			for (RoleValue role : roleList) 
			{
				map.put("id", role.getId());
				List<PrivilegeValue> privileges = this.baseDao.queryForList(SqlKeyConstants.PRIVILEGE_SELECT_BYROLEID, map);
				for (PrivilegeValue privilegeValue : privileges) 
				{
					map.put("privilegeId", privilegeValue.getId());
					map.put("roleid", role.getId());
					List<OperPrivilegeValue> operlist = this.baseDao.queryForList(SqlKeyConstants.OPERPRIVILEGE_SELECT_BYPRIVILEGEID, map);
					privilegeValue.setOperPrivilege(operlist);
				}				
				role.setPrivileges(privileges);
			}
			user.setRoles(roleList);			
			
			return user;
		}
		return null;
	}
	
	/**
	 * 保存一个操作员记录信息
	 * @param user 待保存的操作员实体
	 * @param model 封装结果集实体
	 * @param result 封装出错结果实体
	 */
	@Transactional(propagation = Propagation.REQUIRED)
	public void add(UserValue user , BindingResult result , Model model)
	{
		model.addAttribute("roles", roleService.getAllRoles());
		
		log.debug("add() User:"+user);
		boolean emptyRole = isEmptyhasRole(user);

		if (emptyRole) 
		{
			result.rejectValue("roles", "user.roles.min");
			return;
		}
		if (existsByProperty( user.getUserid(), user.getId())) 
		{
			result.rejectValue("userid", "user.userid.exists");
			return;
		}		
		
		List<RoleValue> roles = new ArrayList<RoleValue>();
		for (RoleValue role : user.getRoles()) 
		{
			if (role.getId() != null) 
			{
				roles.add(roleService.getRoleById(role.getId()));
			}
		}
		
		//设置系统初始化密码
		user.setPassword(DigestUtils.md5Hex(AppConstant.SYSTEM_INIT_PASSWORD));
		user.setStatus(AppConstant.USER_ENABLE);
		this.baseDao.insertData(SqlKeyConstants.SYSTEMUSER_INSERT_SYSTEMUSER, user);
		
		//保存此用户具有的角色
		for (RoleValue role : roles) {
			UserRoleValue ur = new UserRoleValue();
			ur.setRoleId(role.getId());
			ur.setUserId(user.getId());
			this.baseDao.insertData(SqlKeyConstants.ROLE_USER_INSERT, ur);
		}
	}
	
	/**
	 * 判断id值不为id，而用户id值为传进去的值的记录是否存在
	 * @param userid 用户id
	 * @param id 记录id
	 * @return 存在返回true，不存在返回false
	 */
	public boolean existsByProperty(String userid, Integer id) {
		Map<String, Object> map = new HashMap<String, Object>();
		if (AssertHelper.isNotEmptyString(userid))
		{
			map.put("userid", userid.trim());
		}
		if (id != null) 
		{
			map.put("id", id);
		}		
		List<UserValue> list = this.baseDao.queryForList(SqlKeyConstants.SYSTEMUSER_SELECT_BYNAMEANDID, map);
		if (AssertHelper.isNotEmptyCollection(list)) 
		{
			return true;
		}
		return false;
	}

	/**
	 * 判断用户是否具备某一个角色
	 * @param user
	 * @return 具备则返回true，否则返回false
	 */
	private boolean isEmptyhasRole(UserValue user)
	{
		for (RoleValue role : user.getRoles()) 
		{
			if (role.getId() != null) 
			{
				return false;
			}
		}
		return true;
	}
	
	/**
	 * 更新操作员记录，在更新之前先对此操作员进行角色检查，如果他关联的
	 * 角色记录为空则不允许进行修改
	 * @param user 待修改的实体
	 * @param result 封装错误结果的实体
	 * @param model 封装结果集的实体
	 */
	@Transactional(propagation = Propagation.REQUIRED)
	public void update(UserValue user , BindingResult result , Model model)
	{
		model.addAttribute ( "roles" , roleService.getAllRoles ( ) );
		boolean emptyRole = isEmptyhasRole(user);
	
		if ( emptyRole )
		{
			result.rejectValue ( "roles" , "user.roles.min" );
			return ;
		}
		
		update(user);
	}

	
	/**
	 * 根据id获取系统用户信息
	 * @param userid 用户id
	 * @return
	 */
	public UserValue getUserById(Integer userid)
	{
		return this.baseDao.query(SqlKeyConstants.SYSTEMUSER_SELECT_BYPRIMARYKEY, userid);
	}
	
}
