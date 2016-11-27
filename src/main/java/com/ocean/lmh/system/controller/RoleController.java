package com.ocean.lmh.system.controller;


import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
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
import com.ocean.lmh.base.constant.PageConstants;
import com.ocean.lmh.base.message.ErrorActionResult;
import com.ocean.lmh.base.message.SuccessActionResult;
import com.ocean.lmh.base.model.entity.system.RoleValue;
import com.ocean.lmh.base.model.request.system.RoleReq;
import com.ocean.lmh.base.model.vo.PaginationBean;
import com.ocean.lmh.base.util.HttpUtils;
import com.ocean.lmh.base.util.JSONHelper;
import com.ocean.lmh.system.service.RoleService;

/**
 * 系统角色管理控制类，负责对系统角色的各项请求进行管理，根据不同的请求
 * 调用不同的业务逻辑方法，然后返回不同的结果页面
 * @author liminghui
 *
 */
@Controller
@RequestMapping ( value = "/system" )
public class RoleController {

	@Resource ( name = "roleService" )
	private RoleService				roleService;
	
	private static Logger log = LoggerFactory.getLogger(PrivilegeController.class);

	/**
	 * 根据条件查询角色列表信息
	 * @param roleReq 封装查询条件的请求实体
	 * @param model 储存操作结果
	 * @return 跳转地址
	 */
	@RequestMapping ( value = "/role/list" )
	@MarkRequest
	@Log ( "查询角色" )
	public String list ( @ModelAttribute ( "role" ) RoleReq roleReq , Model model,
			HttpServletRequest request) 
	{
		PaginationBean<RoleValue> pb = roleService.getRoleList(roleReq);
		try 
	    {
			pb.setUrl(HttpUtils.getRequestInfo(request, true));
		}
	    catch (Exception e) 
	    {
	    	log.debug("get request url error",e);
		}
		model.addAttribute("pb", pb);
		return PageConstants.Role.LIST;
	}

	/**
	 * 查看角色的详细信息
	 * @param id 角色id
	 * @param model 储存操作结果
	 * @return 跳转地址
	 */
	@RequestMapping ( value = "/role/view" , method = RequestMethod.GET )
	public String view ( @RequestParam Integer id , Model model ) 
	{
		RoleValue role = roleService.getRoleInfos(id);
		model.addAttribute("role", role);
		model.addAttribute("groupMap", roleService.getPrivilegeMapByGroupName());
		return PageConstants.Role.VIEW;
	}


	/**
	 * 初始化新增表单
	 * @param role 角色实体
	 * @return 跳转地址
	 */
	@RequestMapping ( value = "/role/add" , method = RequestMethod.GET )
	public String addForm ( Model model ) 
	{
		RoleValue role = new RoleValue();
		model.addAttribute("role", role);
		model.addAttribute("groupMap", roleService.getPrivilegeMapByGroupName());
		return PageConstants.Role.ADD;
	}

	/**
	 * 新增角色
	 * @param role 待添加的角色
	 * @return 跳转地址
	 */
	@RequestMapping ( value = "/role/add" , method = RequestMethod.POST )
	@Log ( "新增角色" )
	public String add ( @Valid @ModelAttribute ( "role" ) RoleValue role , BindingResult result , RedirectAttributes ra , Model model ,
			HttpServletRequest request ) 
	{	
		roleService.add(role, result, model);
		if ( result.hasErrors ( ) )
		{
			model.addAttribute("groupMap", roleService.getPrivilegeMapByGroupName());
			return PageConstants.Role.ADD;
		}
		ra.addFlashAttribute ( "actionResult" , new SuccessActionResult ( ) );
		return "redirect:" + HttpUtils.getMarkRequestInfo ( request , "returnURI" , "system/role/list.do" );
	}

	/**
	 * 初始化修改表单
	 * @param id 角色id
	 * @param model 储存操作结果
	 * @return 跳转地址
	 */
	@RequestMapping ( value = "/role/update" , method = RequestMethod.GET )
	public String updateForm ( @RequestParam Integer id , Model model ) 
	{
		RoleValue role = roleService.getRoleInfos(id);
		model.addAttribute("role", role);
		model.addAttribute("groupMap", roleService.getPrivilegeMapByGroupName());
		return PageConstants.Role.UPDATE;
	}

	/**
	 * 修改角色
	 * @param role 待修改的角色实体 
	 * @param model 储存操作结果
	 * @return 跳转地址
	 */
	@RequestMapping ( value = "/role/update" , method = RequestMethod.POST )
	@Log ( "修改角色" )
	public String update ( @Valid @ModelAttribute ( "role" ) RoleValue role , BindingResult result , RedirectAttributes ra , Model model ,
			HttpServletRequest request ) 
	{
		roleService.update(role, result, model);
		if ( result.hasErrors ( ) )
		{
			model.addAttribute("groupMap", roleService.getPrivilegeMapByGroupName());
			return  PageConstants.Role.UPDATE;
		}
		ra.addFlashAttribute ( "actionResult" , new SuccessActionResult ( ) );
		return "redirect:" + HttpUtils.getMarkRequestInfo ( request , "returnURI" );
	}

	/**
	 * 删除角色
	 * @param id 角色id
	 * @return 跳转地址
	 */
	@RequestMapping ( value = "/role/remove" , method = RequestMethod.GET )
	@Log ( "删除角色" )
	public String remove ( @RequestParam Integer id , RedirectAttributes ra , HttpServletRequest request ) 
	{
		if (roleService.checkRoleInOperById(id))
		{
			ra.addFlashAttribute ( "actionResult" , new ErrorActionResult("role.delete.error") );
		}
		else
		{
			roleService.remove ( id );
			ra.addFlashAttribute ( "actionResult" , new SuccessActionResult ( ) );
		}		
		return "redirect:" + HttpUtils.getMarkRequestInfo ( request , "returnURI" );
	}
	
	@ResponseBody
	@RequestMapping(value = "/role/checkRole", produces = "application/json")
	@Log("检验角色名是否存在")
	public ResponseEntity<String> checkRoleId(String roleName,Integer roleId)
	{
		Map<String, Object> jsonMap = new HashMap<String, Object>();
		boolean flag = roleService.existsByName(roleName, roleId);
		if (flag) 
		{
			jsonMap.put ("result", 0);
		}		
		String jsonStr = JSONHelper.toJson(jsonMap);
		return new ResponseEntity<String> ( jsonStr , HttpStatus.OK );
	}
}
