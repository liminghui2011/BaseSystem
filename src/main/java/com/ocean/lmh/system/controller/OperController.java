package com.ocean.lmh.system.controller;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.ocean.lmh.base.annotation.Log;
import com.ocean.lmh.base.annotation.MarkRequest;
import com.ocean.lmh.base.constant.AppConstant;
import com.ocean.lmh.base.constant.PageConstants;
import com.ocean.lmh.base.controller.BaseController;
import com.ocean.lmh.base.message.ErrorActionResult;
import com.ocean.lmh.base.message.SuccessActionResult;
import com.ocean.lmh.base.model.entity.system.UserValue;
import com.ocean.lmh.base.model.request.system.UserReq;
import com.ocean.lmh.base.model.vo.PaginationBean;
import com.ocean.lmh.base.util.HttpUtils;
import com.ocean.lmh.base.util.JSONHelper;
import com.ocean.lmh.system.model.vo.UserInfo;
import com.ocean.lmh.system.service.OperService;
import com.ocean.lmh.system.service.RoleService;

/**
 * 操作员管理控制类，负责各项操作员相关的请求响应，对各项请求
 * 进行处理并根据处理结果返回到不同的页面中去。
 * @author [liminghui] 
 */
@Controller
@RequestMapping(value = "/system")
public class OperController extends BaseController{
	
	private static Logger log	= LoggerFactory.getLogger(OperController.class);
	
	@Resource(name = "operService")
	private OperService	operService;
	
	@Resource(name ="roleService")
	private RoleService roleService;
	

	/**
	 * 根据条件分页查询操作员列表
	 * @param user 操作员实体
	 * @param model 储存结果的实体
	 * @return 跳转地址
	 */
	@RequestMapping(value = "/user/list")
	@MarkRequest
	@Log("查询操作员列表")
	public String userList(@ModelAttribute("user") UserReq userReq, Model model, HttpServletRequest request) 
	{
		log.debug("userReq::"+userReq.toString());
		PaginationBean<UserValue> pb = operService.list(userReq, request);
		try 
	    {
			pb.setUrl(HttpUtils.getRequestInfo(request, true));
		}
	    catch (Exception e) 
	    {
	    	log.debug("get request url error",e);
		}
		UserInfo user = getSessionUser(request);
		model.addAttribute("operstatu", getParamsFromMemcached("oper_status"));
		model.addAttribute("pb", pb);
		model.addAttribute("userInfo", user);
		return PageConstants.User.LIST;
	}

	/**
	 * 初始化添加操作员的form表单信息
	 * @param model 储存结果的实体
	 */
	@RequestMapping(value = "/user/add", method = RequestMethod.GET)
	@Log("初始化添加表单")
	public String addForm(Model model) 
	{
		model.addAttribute("user", new UserValue());
		model.addAttribute("roles", roleService.getAllRoles());
		return PageConstants.User.ADD;
	}

	/**
	 * 新增操作员
	 * @param user 操作员实体
	 */
	@RequestMapping(value = "/user/add", method = RequestMethod.POST)
	@Log("新增操作员")
	public String addUser(@Valid @ModelAttribute("user") UserValue user, BindingResult result, RedirectAttributes ra, Model model,
			HttpServletRequest request) 
	{
		if (result.hasErrors()) 
		{
			return PageConstants.User.ADD;
		}
		
		operService.add(user, result, model);

		if (result.hasErrors()) 
		{
			return PageConstants.User.ADD;
		}
		ra.addFlashAttribute("actionResult", new SuccessActionResult());
		return "redirect:" + HttpUtils.getMarkRequestInfo(request, "returnURI", PageConstants.User.LIST);
	}

	/**
	 * 初始化修改表单
	 * @param user 操作员实体
	 * @return 跳转地址
	 */
	@RequestMapping(value = "/user/update", method = RequestMethod.GET)
	@Log("初始化修改表单")
	public String updateForm(@RequestParam Integer id, Model model,HttpServletRequest request) 
	{
		UserValue user = operService.getUserHasRolesById(id);
		UserInfo userInfo = getSessionUser(request);
		model.addAttribute("userInfo", userInfo);
		model.addAttribute("user", user);
		model.addAttribute("roles", roleService.getAllRoles());
		return PageConstants.User.UPDATE;
	}

	/**
	 * 修改操作员记录信息
	 * @param user 待修改的操作员实体
	 * @param result 封装错误结果的实体
	 * @param model 封装处理结果的实体
	 * @param request 请求对象
	 * @return 跳转地址
	 */
	@RequestMapping(value = "/user/update", method = RequestMethod.POST)
	@Log("修改操作员")
	public String updateUser(@Valid @ModelAttribute UserValue user, BindingResult result, RedirectAttributes ra, Model model,
			HttpServletRequest request) 
	{
		operService.update(user, result, model);
		if (result.hasErrors()) 
		{
			return PageConstants.User.UPDATE;
		}
		ra.addFlashAttribute("actionResult", new SuccessActionResult());
		return "redirect:" + HttpUtils.getMarkRequestInfo(request, "returnURI", PageConstants.User.LIST);
	}

	/**
	 * 根据操作员id删除操作员记录
	 * @param id 操作员id
	 * @param request 请求对象
	 * @return 跳转地址
	 */
	@RequestMapping(value = "/user/remove", method = RequestMethod.GET)
	@Log("删除用户")
	public String remove(Integer id, RedirectAttributes ra, HttpServletRequest request) 
	{
		operService.remove(id);
		ra.addFlashAttribute("actionResult", new SuccessActionResult());
		return "redirect:" + HttpUtils.getMarkRequestInfo(request, "returnURI", PageConstants.User.LIST);
	}

	/**
	 * 停用或启用操作员
	 * @param id 操作员id
	 * @param request 请求对象
	 * @return 跳转地址
	 */
	@RequestMapping(value = "/user/updateStatus", method = RequestMethod.GET)
	@Log("停用或启用操作员")
	public String disable(Integer id, String newStatus, RedirectAttributes ra, HttpServletRequest request) 
	{
		operService.updateStatus(id, newStatus);
		ra.addFlashAttribute("actionResult", new SuccessActionResult());
		return "redirect:" + HttpUtils.getMarkRequestInfo(request, "returnURI", PageConstants.User.LIST);
	}

	/**
	 * 初始化更改自己的密码表单
	 * @param user 操作员实体
	 * @param session
	 * @return 跳转地址
	 */
	@RequestMapping(value = "/user/change_self_password", method = RequestMethod.GET)
	@Log("初始化修改自己的密码表单")
	public String changeSelfPasswordForm(@ModelAttribute("user") UserReq user, HttpSession session) 
	{
		UserInfo ui = (UserInfo) session.getAttribute(AppConstant.USER_INFO);
		user.setUserid(ui.getUserid());
		return PageConstants.User.CHANGE_SELF_PASSWORD;
	}

	/**
	 * 更改自己的密码
	 * 
	 * @param user 操作员实体
	 * @param session
	 * @return 跳转地址
	 */
	@RequestMapping(value = "/user/change_self_password", method = RequestMethod.POST)
	@Log("修改自己的密码")
	public String changeSelfPassword(@ModelAttribute("user") UserReq userReq, BindingResult result,
			RedirectAttributes ra,HttpServletRequest request) 
	{
		operService.changeSelfPassword(userReq, result);
		if (result.hasErrors()) 
		{
			ra.addFlashAttribute("actionResult", new ErrorActionResult());
			return PageConstants.User.CHANGE_SELF_PASSWORD;
		}
		ra.addFlashAttribute("actionResult", new SuccessActionResult());
		return "redirect:/system/user/change_self_password.do";
	}

	/**
	 * 初始化修改其他操作员密码的表单
	 * @param id 操作员id
	 * @param model 结果集实体
	 * @return 跳转地址
	 */
	@RequestMapping(value = "/user/change_password", method = RequestMethod.GET)
	@Log("初始化修改密码表单")
	public String changePasswordForm(Integer id, Model model) 
	{
		UserReq userReq = operService.changePasswordForm(id);
		model.addAttribute("user", userReq);
		return PageConstants.User.CHANGE_PASSWORD;
	}

	/**
	 * 修改其他操作员的密码
	 * @param user 操作员实体
	 * @param request 请求实体
	 * @return 跳转地址
	 */
	@RequestMapping(value = "/user/change_password", method = RequestMethod.POST)
	@Log("修改密码")
	public String changePassword(@ModelAttribute("user") UserReq userReq, RedirectAttributes ra,
			HttpServletRequest request) 
	{
		operService.changePassword(userReq);
		ra.addFlashAttribute("actionResult", new SuccessActionResult());
		return "redirect:" + HttpUtils.getMarkRequestInfo(request, "returnURI", PageConstants.User.LIST);
	}
	
	@ResponseBody
	@RequestMapping(value = "/user/checkUser", produces = "application/json")
	@Log("检验账号名是否存在")
	public ResponseEntity<String> checkUserId(String userId)
	{
		Map<String, Object> jsonMap = new HashMap<String, Object>();
		boolean flag = operService.existsByProperty(userId,null);
		if (flag) 
		{
			jsonMap.put ("result", 0);
		}		
		String jsonStr = JSONHelper.toJson(jsonMap);
		return new ResponseEntity<String> ( jsonStr , HttpStatus.OK );
	}
}
