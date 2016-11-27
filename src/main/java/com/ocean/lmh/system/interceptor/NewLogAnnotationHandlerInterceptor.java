package com.ocean.lmh.system.interceptor;

import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.ocean.lmh.base.cache.MemcachedHelper;
import com.ocean.lmh.base.constant.AppConstant;
import com.ocean.lmh.base.constant.ThreadLocalConstants;
import com.ocean.lmh.base.wrapper.InfoLogServletResponse;
import com.ocean.lmh.base.wrapper.ResponseInfo;
import com.ocean.lmh.system.controller.MainController;
import com.ocean.lmh.system.model.vo.LogConfigValue;
import com.ocean.lmh.system.service.UserActionLogService;

public class NewLogAnnotationHandlerInterceptor extends HandlerInterceptorAdapter {
	
	private static Logger log	= LoggerFactory.getLogger(NewLogAnnotationHandlerInterceptor.class);
	
	@Resource ( name = "userActionLogService" )
	private UserActionLogService	userActionLogService;
	
	@Resource( name = "mencached" )
	private MemcachedHelper memcachedHelper;

	@SuppressWarnings("unchecked")
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		
		if ( (handler instanceof HandlerMethod) ){
			HandlerMethod handlerMethod = (HandlerMethod)handler;
			if(handlerMethod.getBeanType() == MainController.class){
				return super.preHandle(request, response, handler);
			}
		}
		
		
		request.setAttribute(ThreadLocalConstants.START_TIME, new Long(System.currentTimeMillis()));

		Map<String, LogConfigValue> configValues = (Map<String, LogConfigValue>) memcachedHelper.getValueFromMemcache(AppConstant.LOG_CONFIG_VALUE);

		String requestUri = parseUri(request);
		LogConfigValue logConfigValue = configValues.get(requestUri);
		
		if(logConfigValue == null){
			String method = request.getMethod().toUpperCase();
			String key = method.toUpperCase()+requestUri;
			logConfigValue = configValues.get(key);
		}
		
		log.debug("requestUri:"+requestUri);
		log.debug("LOG_CONFIG_VALUE(logConfigValue):"+logConfigValue);
		
		if(logConfigValue != null){
			userActionLogService.save(request, logConfigValue);
		}
		
		return super.preHandle(request, response, handler);
	}
	
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)throws Exception{
		ResponseInfo responseInfo = null;
		if(response instanceof ResponseInfo){
			responseInfo = (ResponseInfo)(response);
		}else{
			responseInfo = new InfoLogServletResponse(response);
		}
		userActionLogService.update(request, responseInfo);
		
		super.afterCompletion(request, response, handler, ex);
		
	}
	
	private String parseUri(HttpServletRequest request) {
		String requestUri = request.getRequestURI();
		int index = requestUri.indexOf("/", 1);
		return requestUri.substring(index);
	}
}
