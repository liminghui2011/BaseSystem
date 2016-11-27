package com.ocean.lmh.system.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;

import com.ocean.lmh.base.cache.MemcachedHelper;
import com.ocean.lmh.base.constant.AppConstant;
import com.ocean.lmh.base.constant.PageConstants;
import com.ocean.lmh.base.message.ActionResult;
import com.ocean.lmh.base.model.entity.system.ActionLogValue;
import com.ocean.lmh.base.model.entity.system.MenuValue;
import com.ocean.lmh.base.model.entity.system.OperPrivilegeValue;
import com.ocean.lmh.base.model.entity.system.PrivilegeValue;
import com.ocean.lmh.base.model.entity.system.RoleValue;
import com.ocean.lmh.base.model.entity.system.UserValue;
import com.ocean.lmh.base.model.request.system.LoginReq;
import com.ocean.lmh.base.util.AssertHelper;
import com.ocean.lmh.base.util.JSONHelper;
import com.ocean.lmh.system.model.vo.UserInfo;

/**
 * 操作员登录时名称和密码验证，登录操作的业务逻辑实现，
 * 系统初始数据获取并放置到session或application中的业务类
 * @author liminghui
 */
@Transactional
@Service("loginService")
public class LoginService {
	
	private static Logger log	= LoggerFactory.getLogger(LoginService.class);
	
	@Resource(name = "operService")
	private OperService	operService;
	
	@Resource ( name = "logService" )
    private LogService  logService;
	
	@Resource(name = "roleService")
	private RoleService roleService;
	
	@Resource( name = "mencached" )
	private MemcachedHelper memcachedHelper;
	
	/**
	 * 操作员登录时的验证并将操作员数据及系统公用的数据放置到session或application中
	   * 　REQUIRED:业务方法需要在一个容器里运行。
	 *  如果方法运行时，已经处在一个事务中，那么加入到这个事务，否则自己新建一个新的事务。
	 *  @param loginReq 操作员登录参数实体[userid:xxx,password:xx]
	 *  @param request 页面穿过来的请求实体
	 */
	@SuppressWarnings("unchecked")
	@Transactional(propagation = Propagation.REQUIRED)
	public void login( LoginReq loginReq, BindingResult result, HttpServletRequest request)
	{	
		String userid = loginReq.getUserid();
		String password = loginReq.getPassword();
		String verifyCode = loginReq.getVerifyCode();
		String verifyCode2 = (String) request.getSession().getAttribute(AppConstant.VERIFY_CODE);
		if (!verifyCode.equals(verifyCode2)) 
		{
			log.info("验证码错误");
			result.rejectValue("verifyCode", "login.verifyCode.mismatch");
			return ;
		}
		
		UserValue user = operService.getUserByUserIdAndPass(userid, password);
		if (checkUserIdAndPassword(user,result)) 
		{
			return;
		}
		
		Map<String, UserInfo> userHolder = (Map<String, UserInfo>) memcachedHelper.getValueFromMemcache(AppConstant.USER_HOLDER);
		if (checkIsLogin(loginReq, userHolder, userid, password, request, result))
		{
			return;
		}

		// 更新登录时间
		log.info("login do business");
		operService.updateLoginTime(userid);

		UserInfo userInfo = getUserInfoHaveValue(user, request);

		// 创建新session
		request.getSession().invalidate();
		HttpSession session = request.getSession(true);
		String sessionid = session.getId();
		userHolder.put(sessionid, userInfo);
		Map<String, String> sessionidHolder = (Map<String, String>) memcachedHelper.getValueFromMemcache(AppConstant.SESSIONID_HOLDER);
		log.debug("sessionidHolder:"+sessionidHolder);
		
		// 替换旧的操作员sessionid
		String oldSessionid = sessionidHolder.put(userid, sessionid);
		userHolder.remove(oldSessionid);
		memcachedHelper.setValueToMemcache(AppConstant.SESSIONID_HOLDER, 0, sessionidHolder);
		memcachedHelper.setValueToMemcache(AppConstant.USER_HOLDER, 0, userHolder);

		MenuValue tree = (MenuValue) memcachedHelper.getValueFromMemcache(AppConstant.SYSTEM_MENU_TREE);
		
		// 复制一棵树
		String treeJSON = JSONHelper.toJson(tree);
		MenuValue userTree = JSONHelper.toObject(treeJSON, MenuValue.class);
		
		// 根据操作员能访问的地址，对比菜单的链接地址，生成最终的菜单
		generateTree(userTree, userInfo.getAllowUriList(), session);
		userTree = setMenuEnableByChild(userTree);
		request.getSession().setAttribute(AppConstant.USER_MENU_TREE, userTree);
		request.getSession().setAttribute(AppConstant.USER_INFO, userInfo);
		request.getSession().setAttribute(AppConstant.USER_ID, userInfo.getUserid());
		
		log.debug("USER_MENU_TREE:" + userTree);
		
		//保存操作员登录时的操作日志
		saveActionLog(userInfo,request);
		
		if (userTree != null) 
		{
			int mid1 = 0;
			for (MenuValue node : userTree.getChildren()) 
			{
				if (node.isEnable()) 
				{
					mid1 = node.getId();
					break;
				}
			}
			
			//将菜单树的根菜单的id存到session中
			session.setAttribute("mid1", String.valueOf(mid1));
		}
		
		log.info("login do business over");
	}
	
	/**
	 * 保存操作员登录的操作日志
	 * @param userId 操作员编号
	 * @param request 
	 */
	private void saveActionLog(UserInfo user,HttpServletRequest request)
	{
		ActionLogValue actionLog = new ActionLogValue();
		actionLog.setContent("登录系统");
		actionLog.setActor(user.getUserid());
		actionLog.setType(ActionResult.SUCCESS);
		actionLog.setParam("userid:" + user.getUserid() + "<br/>" + "password:***");
		actionLog.setUri(PageConstants.Login.LOGIN);
		actionLog.setAddDate(new Date());
		actionLog.setLoginIp(request.getRemoteAddr());
		String roleNames = (String) request.getSession().getAttribute(user.getUserid());
		if (AssertHelper.isEmptyString(roleNames)) 
		{
			roleNames = roleService.getRoleNamesByIds(user,request);
		}
		actionLog.setRoleName(roleNames);
		logService.addLog(actionLog);		
	}
	
	/**
	 * 根据登录操作员所具备的权限来初始化前台将要显示的菜单信息
	 * @param tree 根菜单树，具备所有的菜单
	 * @param allowUriList 操作员具备的菜单项集合
	 * @param session 
	 */
	private void generateTree(MenuValue tree, List<String> allowUriList, HttpSession session) 
	{
		if (tree == null) 
		{
			return;
		}
		MenuValue parent = tree.getParent();
		String uri = tree.getUri();
		if (StringUtils.hasText(uri)) 
		{
			if (uri.toLowerCase().startsWith("http://") || uri.toLowerCase().startsWith("https://")) 
			{
				// 外链都显示
				tree.setEnable(true);
			} else 
			{
				// 内链
				for (String allowUri : allowUriList) 
				{
					if (uri.startsWith(allowUri)) 
					{
						tree.setEnable(true);
					}
				}
				tree.setUri(uri);
			}
		}

		for (MenuValue child : tree.getChildren()) 
		{
			generateTree(child, allowUriList, session);
		}

		if (tree.isEnable()) 
		{
			if (parent != null) 
			{
				parent.setEnable(true);				
				tree.setParent(parent);
			}
		}
	}
	
	/**
	 * 获取操作员可以访问的URL列表
	 * @param user 操作员实体
	 * @return 此操作员具有的菜单项集合
	 */
	private List<String> getAllowUriList(UserValue user) 
	{
		List<String> allowUriList = new ArrayList<String>();
		for (RoleValue role : user.getRoles()) 
		{
			for (PrivilegeValue privilege : role.getPrivileges()) 
			{
				allowUriList.add(privilege.getUri());
				for (OperPrivilegeValue oper : privilege.getOperPrivilege()) 
				{
					allowUriList.add(oper.getUri());
				}
			}
		}
		return allowUriList;
	}
	
	/**
	 * 校验用户是否已经在别处登录
	 * @return true为已经在别处登录，false为未登录
	 */
	private boolean checkIsLogin(LoginReq loginReq,Map<String, UserInfo> userHolder,
			String userid,String password,HttpServletRequest request,BindingResult result)
	{
		if (!loginReq.isForceLogin()) 
		{
			// 非强行登录
			for (UserInfo u : userHolder.values()) 
			{
				if (u.getUserid().equals(userid)) 
				{
					// 账号已经在别处登录
					result.reject("login.status.logined");
					request.setAttribute("userid", userid);
					request.setAttribute("password", password);
					return true;
				}
			}
		}
		return false;
	}
	
	/**
	 * 验证登录用户的用户名和密码，当根据输入的用户名和密码
	 * 查询不到记录和能查到记录但是此记录已经被禁用时返回错误
	 * 提示信息。
	 * @return 存在以上两种情况时返回true，否则返回false
	 */
	private boolean checkUserIdAndPassword(UserValue user,BindingResult rs)
	{
		if (user == null) 
		{
			//"账号或密码错误"
			rs.reject("login.info.mismatch"); 
			return true;
		}
		else 
		{
			if (AppConstant.USER_DISABLE.equals(user.getStatus())) 
			{
				// 账号已禁用
				rs.reject("login.status.disable"); 
				return true;
			}
			if (AppConstant.USER_DELETE.equals(user.getStatus()))
			{
				//账号已删除
				rs.reject("login.status.delete");
				return true;
			}
		}
		return false;
	}
	
	/**
	 * 构造一个储存登录用户基本信息的值对象，作为前台展示的基本数据
	 * @param user 登录的用户记录
	 * @param request 
	 * @return 值对象
	 */
	public UserInfo getUserInfoHaveValue(UserValue user,HttpServletRequest request)
	{
		UserInfo userInfo = new UserInfo();
		userInfo.setId(user.getId());
		userInfo.setUserid(user.getUserid());
		userInfo.setNickname(user.getNickname());
		userInfo.setCity(user.getCity());
		userInfo.setStatus(user.getStatus());
		userInfo.setAllowUriList(getAllowUriList(user));
		userInfo.setLastLoginDate(user.getLastLoginDate());
		
		StringBuffer sb = new StringBuffer();
		for (RoleValue role : user.getRoles())
		{
			sb.append(role.getId()+",");
		}
		String ids = sb.toString();
		if (ids.length()>0)
		{
			ids = ids.substring(0,ids.length()-1);
		}
		userInfo.setRoleIds(ids);
		String roleNames = (String) request.getSession().getAttribute(user.getUserid());
		if (AssertHelper.isEmptyString(roleNames)) 
		{
			roleNames = roleService.getRoleNamesByIds(userInfo,request);
		}
		userInfo.setRoleName(roleNames);
		
		return userInfo;
	}
	
	private MenuValue setMenuEnableByChild(MenuValue tree)
	{
		if (tree != null)
		{
			List<MenuValue> list = tree.getChildren();
			for (MenuValue menuValue : list)
			{
				List<MenuValue> childList = menuValue.getChildren();
				for (MenuValue childMenu : childList) 
				{
					if (childMenu.isEnable())
					{
						menuValue.setEnable(true);
						break;
					}
				}
			}
		}
		return tree;
	}
}
