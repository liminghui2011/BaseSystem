package com.ocean.lmh.system.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.ocean.lmh.base.annotation.Log;
import com.ocean.lmh.base.annotation.MarkRequest;
import com.ocean.lmh.base.constant.PageConstants;
import com.ocean.lmh.base.message.SuccessActionResult;
import com.ocean.lmh.base.model.entity.system.PrivilegeValue;
import com.ocean.lmh.base.model.request.system.PrivilegeReq;
import com.ocean.lmh.base.model.vo.PaginationBean;
import com.ocean.lmh.base.util.HttpUtils;
import com.ocean.lmh.system.service.PrivilegeService;

/**
 * 系统权限管理控制类，负责所有与权限相关的请求的响应，
 * 接受到请求后调用相应的业务方法进行处理，然后根据处理结果返回相应的结果页面
 * @author liminghui 
 */
@Controller
@RequestMapping ( value = "/system" )
public class PrivilegeController {

	@Resource ( name = "privilegeService" )
	private PrivilegeService	privilegeService;
	
	private static Logger log = LoggerFactory.getLogger(PrivilegeController.class);
	
	/**
	 * 初始化新增表单
	 * @param model 封装结果参数
	 * @return 跳转地址
	 */
	@RequestMapping ( value = "/privilege/add" , method = RequestMethod.GET )
	@Log("新增权限表单")
	public String addForm ( Model model ) 
	{
		model.addAttribute ( "privilege" , new PrivilegeValue ( ) );
		return PageConstants.Privilege.ADD;
	}

	/**
	 * 新增权限信息
	 * @param privilege 待添加的权限实体
	 * @param result 结果集实体
	 * @return 跳转地址
	 */
	@RequestMapping ( value = "/privilege/add" , method = RequestMethod.POST )
	@Log ( "新增权限" )
	public String add ( @Valid @ModelAttribute ( "privilege" ) PrivilegeValue privilege , BindingResult result , RedirectAttributes ra ,
			HttpServletRequest request ) 
	{
		if ( result.hasErrors ( ) )
		{
			return PageConstants.Privilege.ADD;
		}
		
		privilegeService.addPrivilege(privilege, result);
		
		if ( result.hasErrors ( ) )
		{
			return PageConstants.Privilege.ADD;
		}		
		ra.addFlashAttribute ( "actionResult" , new SuccessActionResult ( ) );
		return "redirect:" + HttpUtils.getMarkRequestInfo ( request , "returnURI" ,  PageConstants.Privilege.LIST );
	}

	/**
	 * 根据条件分页查询权限列表信息
	 * @param privilege 封装查询条件的实体
	 * @return 跳转地址
	 */
	@RequestMapping ( value = "/privilege/list" )
	@MarkRequest
	@Log ( "查询权限" )
	public String list ( @ModelAttribute ( "privilege" ) PrivilegeReq privilege , Model model,
			HttpServletRequest request) 
	{
		PaginationBean<PrivilegeValue> pb = privilegeService.privilegeList(privilege);
		try 
	    {
			pb.setUrl(HttpUtils.getRequestInfo(request, true));
		}
	    catch (Exception e) 
	    {
	    	log.debug("get request url error",e);
		}
		model.addAttribute("pb", pb);
		return  PageConstants.Privilege.LIST;
	}

	/**
	 * 删除权限
	 * @param id 权限id
	 * @return 跳转地址
	 */
	@RequestMapping ( value = "/privilege/remove" , method = RequestMethod.GET )
	@Log ( "删除权限" )
	public String remove ( @RequestParam Integer id , RedirectAttributes ra , HttpServletRequest request ) 
	{
		privilegeService.remove(id, request);
		ra.addFlashAttribute ( "actionResult" , new SuccessActionResult ( ) );
		return "redirect:" + HttpUtils.getMarkRequestInfo ( request , "returnURI" );
	}

	/**
	 * 初始化修改表单
	 * @param id 权限id
	 * @param model 封装结果参数
	 * @return 跳转地址
	 */
	@RequestMapping ( value = "/privilege/update" , method = RequestMethod.GET )
	public String updateForm ( @RequestParam Integer id , Model model ) 
	{
		PrivilegeValue privilege = privilegeService.getPrivilege(id);
		model.addAttribute("privilege", privilege);
		return  PageConstants.Privilege.UPDATE;
	}

	/**
	 * 修改权限
	 * @param privilege 待修改的权限实体
	 * @param result 结果集实体
	 * @param request 请求对象
	 * @return 跳转地址
	 */
	@RequestMapping ( value = "/privilege/update" , method = RequestMethod.POST )
	@Log ( "修改权限" )
	public String update ( @Valid @ModelAttribute ( "privilege" ) PrivilegeValue privilege , BindingResult result , RedirectAttributes ra , 	HttpServletRequest request ) 
	{
		if ( result.hasErrors ( ) )
		{
			return PageConstants.Privilege.UPDATE;
		}
		
		privilegeService.updatePrivilege(privilege, result);
		
		if ( result.hasErrors ( ) )
		{
			return PageConstants.Privilege.UPDATE;
		}
		ra.addFlashAttribute ( "actionResult" , new SuccessActionResult ( ) );
		return "redirect:" + HttpUtils.getMarkRequestInfo ( request , "returnURI" );
	}
}
