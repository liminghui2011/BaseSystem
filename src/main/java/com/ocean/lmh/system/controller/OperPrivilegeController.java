package com.ocean.lmh.system.controller;

import java.util.List;

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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.ocean.lmh.base.annotation.Log;
import com.ocean.lmh.base.message.ErrorActionResult;
import com.ocean.lmh.base.message.SuccessActionResult;
import com.ocean.lmh.base.model.entity.system.OperPrivilegeValue;
import com.ocean.lmh.system.service.OperPrivilegeService;
import com.ocean.lmh.system.service.PrivilegeService;

/**
 * 操作权限菜单管理的控制类，负责对与操作权限相关的请求进行响应和处理
 * 然后分发到不同的结果页面。
 * @author liminghui
 *
 */
@RequestMapping("/operprivilege")
@Controller
public class OperPrivilegeController {
	
	private static Logger log = LoggerFactory.getLogger(OperPrivilegeService.class);
	
	@Resource(name = "operPrivilegeService")
	private OperPrivilegeService operPrivilegeService;
	
	@Resource ( name = "privilegeService" )
	private PrivilegeService	privilegeService;
	
	/**
	 * 根据父菜单id查询其下的所有操作权限菜单记录信息
	 * @param privilegeId 父菜单id
	 * @param model 储存操作数据
	 * @return 跳转地址
	 */
	@RequestMapping(value="/list")
	@Log("查看操作权限列表")
	public String getOperPrivilegeList(@ModelAttribute("operprivilege")OperPrivilegeValue operprivilege, 
			Integer privilegeId,Model model)
	{
		log.debug("privilegeId :: " + privilegeId);
		List<OperPrivilegeValue> list = operPrivilegeService.getAllOperPrivilegesByPid(privilegeId);
		model.addAttribute("oplist", list);
		model.addAttribute("privilege", privilegeService.getPrivilege(privilegeId));
		return "system/privilege/operprivilegelist";
	}
	
	/**
	 * 新增或者修改操作权限信息
	 * @param privilege 待添加或修改的权限实体
	 * @param result 结果集实体
	 * @return 跳转地址
	 */
	@RequestMapping ( value = "/add" , method = RequestMethod.POST )
	@Log ( "新增操作权限" )
	public String addOperPrivilege ( @Valid @ModelAttribute ( "operprivilege" ) OperPrivilegeValue operPrivilege , BindingResult result , RedirectAttributes ra ,
			Integer privilegeId, HttpServletRequest request ) 
	{
		if (operPrivilege.getOpId()!=null) 
		{
			operPrivilegeService.updateOperPrivivelgeById(operPrivilege);
		}else
		{
			operPrivilegeService.addOperPrivilege(operPrivilege, privilegeId, result);
		}		
		
		if ( result.hasErrors ( ) )
		{
			ra.addFlashAttribute ( "actionResult" , new ErrorActionResult ( ) );
			return "redirect:list.do?privilegeId="+privilegeId;
		}		
		ra.addFlashAttribute ( "actionResult" , new SuccessActionResult ( ) );
		return "redirect:list.do?privilegeId="+privilegeId;
	}
	
	/**
	 * 修改操作权限信息
	 * @param opId 操作权限id
	 * @param model 储存操作结果
	 * @return 跳转地址
	 */
	@RequestMapping ( value = "/update" , method = RequestMethod.GET )
	@Log ( "修改操作权限" )
	public String updateOperPrivilege(Integer opId,Model model)
	{
		OperPrivilegeValue operPrivilege = operPrivilegeService.getOperPrivilegeById(opId);
		Integer privilegeId = operPrivilege.getPrivilegeId();
		List<OperPrivilegeValue> list = operPrivilegeService.getAllOperPrivilegesByPid(privilegeId);
		model.addAttribute("oplist", list);
		model.addAttribute("privilege", privilegeService.getPrivilege(privilegeId));
		model.addAttribute("operprivilege", operPrivilege);
		return "system/privilege/operprivilegelist";
	}
	
	/**
	 * 删除操作权限信息
	 * @param opId 操作权限id
	 * @param model 储存操作结果
	 * @return 跳转地址
	 */
	@RequestMapping ( value = "/remove"  )
	@Log ( "删除操作权限" )
	public String deleteOperPrivilege(Integer opId,Model model)
	{
		OperPrivilegeValue operPrivilege = operPrivilegeService.getOperPrivilegeById(opId);
		Integer privilegeid = operPrivilege.getPrivilegeId();
		operPrivilegeService.deleteOperPrivilegeById(opId,privilegeid);
		return "redirect:list.do?privilegeId="+privilegeid;
	}
}
