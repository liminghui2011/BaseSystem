package com.ocean.lmh.system.controller;


import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.ocean.lmh.base.annotation.Log;
import com.ocean.lmh.base.constant.PageConstants;
import com.ocean.lmh.base.model.request.system.LoginReq;
import com.ocean.lmh.system.model.vo.MessageEntity;
import com.ocean.lmh.system.service.JmsMessageService;
import com.ocean.lmh.system.service.LoginService;

/**
 * 操作员登录、退出时的请求控制类，负责对操作员在登录和退出时的请求进行响应，然后进行结果分发
 * @author liminghui 
 * @since 2013-08-28
 */
@Controller
@RequestMapping ( value = "/system" )
public class LoginController {
	
	
	@Resource ( name = "loginService" )
	private LoginService	loginService;
	
	@Resource ( name = "jmsMessageService" )
	private JmsMessageService	jmsMessageService;

	/**
	 * 初始化操作员登录页面
	 * @param model 储存数据的实体
	 * @return 跳转地址
	 */
	@RequestMapping ( value = "/login_index" , method = RequestMethod.GET )
	@Log("系统登录页面")
	public String loginForm ( Model model ) 
	{
		model.addAttribute ( "login" , new LoginReq ( ) );
		return PageConstants.Login.LOGIN;
	}
	
	/**
	 * 操作员退出系统
	 * @param session
	 * @return 跳转地址
	 */
	@RequestMapping ( value = "/logout" , method = RequestMethod.GET )
	public String logout ( HttpSession session, Integer outType ) 
	{
		session.invalidate ( );
		
		//如果outType为0则是注销并关闭当前页面，为1则注销当前账号回到登录页面
		if (outType == 0) 
		{
			return "redirect:/system/close_window.do";
		}
		else
		{
			return "redirect:/system/login_index.do";
		}		
	}
	
	@RequestMapping(value = "/close_window")
	public String closePage()
	{
		return PageConstants.Base.CLOSE;
	}

	/**
	 * 操作员登录系统
	 * @param loginReq 登录时的请求参数[姓名，密码]
	 * @param result 储存错误信息的实体
	 * @param request 请求对象
	 * @return 跳转地址
	 */
	@RequestMapping ( value = "/login" , method = RequestMethod.POST )
	public String login ( @Valid @ModelAttribute ( "login" ) LoginReq loginReq , BindingResult result , HttpServletRequest request ) 
	{
		if ( result.hasErrors ( ) )
		{
			return PageConstants.Login.LOGIN;
		}
		
		loginService.login(loginReq, result, request);
		jmsMessageService.sendMessage(new MessageEntity("admin","123456"));
		Object returnObj = request.getAttribute("userid");
		if(returnObj != null) 
		{
			return PageConstants.Login.LOGIN_FORCE;
		}
		
		if ( result.hasErrors ( ) )
		{
			return PageConstants.Login.LOGIN;
		}
		return "redirect:/system/index.do";
	}
	
	@RequestMapping(value = "/index")
	public String index() 
	{
		return PageConstants.Base.INDEX;
	}
}
