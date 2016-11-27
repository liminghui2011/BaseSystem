package com.ocean.lmh.system.interceptor;

import java.util.Date;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.ocean.lmh.base.annotation.Log;
import com.ocean.lmh.base.constant.AppConstant;
import com.ocean.lmh.base.message.ActionResult;
import com.ocean.lmh.base.model.entity.system.ActionLogValue;
import com.ocean.lmh.base.util.AssertHelper;
import com.ocean.lmh.system.model.vo.UserInfo;
import com.ocean.lmh.system.service.LogService;
import com.ocean.lmh.system.service.RoleService;

public class LogAnnotationHandlerInterceptor extends HandlerInterceptorAdapter {
	
	private static Logger log	= LoggerFactory.getLogger(LogAnnotationHandlerInterceptor.class);
	
	@Resource ( name = "logService" )
	private LogService	logService;
	
	@Resource(name = "roleService")
	private RoleService roleService;
	
	@SuppressWarnings("unchecked")
	@Override
	public void postHandle ( HttpServletRequest request , HttpServletResponse response , Object handler , ModelAndView modelAndView ) throws Exception {
		
		if ( handler instanceof HandlerMethod ){
			HandlerMethod h = ( HandlerMethod ) handler;

			Log logAnnotation = h.getMethodAnnotation ( Log.class );
			if ( logAnnotation != null ){
				String content = logAnnotation.value ( );
				String uri = request.getRequestURI ( );
				UserInfo user = ( UserInfo ) request.getSession ( ).getAttribute ( AppConstant.USER_INFO );
				String actor = user == null ? "system" : user.getUserid ( );
				String paramInfo = "";
				Map<String, String [ ]> params = request.getParameterMap ( );
				for ( Map.Entry<String, String [ ]> entry : params.entrySet ( ) ){
					String key = entry.getKey ( ) + ":";
					String [ ] values = entry.getValue ( );
					for ( int i = 0 ; i < values.length ; ++i ){
						key += values [ i ] + ",";
					}
					key = key.substring ( 0 , key.length ( ) - 1 );
					key += "<br/>";
					paramInfo += key;
				}

				ActionLogValue actionLog = new ActionLogValue ( );
				actionLog.setContent ( content );
				actionLog.setActor ( actor );
				actionLog.setType ( ActionResult.SUCCESS );
				actionLog.setParam ( paramInfo );
				actionLog.setUri ( uri );
				actionLog.setAddDate ( new Date() );
				actionLog.setLoginIp(request.getRemoteAddr());
				if (user != null)
				{
					String roleNames = (String) request.getSession().getAttribute(user.getUserid());
					log.debug("logAnnotation rolename :: "+roleNames);
					if (AssertHelper.isEmptyString(roleNames)) 
					{
						roleNames = roleService.getRoleNamesByIds(user,request);
					}			
					actionLog.setRoleName(roleNames);
				}				
				logService.addLog( actionLog );				
			}
		}
		
		super.postHandle ( request , response , handler , modelAndView );
	}
}
