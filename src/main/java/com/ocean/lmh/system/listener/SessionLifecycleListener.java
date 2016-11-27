package com.ocean.lmh.system.listener;

import java.util.Map;

import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ocean.lmh.base.cache.MemcachedHelper;
import com.ocean.lmh.base.constant.AppConstant;
import com.ocean.lmh.base.util.HttpRequestUtils;
import com.ocean.lmh.system.model.vo.UserInfo;


public class SessionLifecycleListener implements HttpSessionListener {

	private Logger	log	= LoggerFactory.getLogger ( SessionLifecycleListener.class );
	
	private MemcachedHelper memcachedHelper;
	
	public void sessionCreated ( HttpSessionEvent event ) {
		HttpSession session = event.getSession ( );
		log.debug ( "session create:" + session.getId ( ) );
	}

	@SuppressWarnings ( "unchecked" )
	public void sessionDestroyed ( HttpSessionEvent event ) {
		HttpSession session = event.getSession ( );
		log.debug ( "session destroy:" + session.getId ( ) );
		memcachedHelper = (MemcachedHelper) HttpRequestUtils.getBeanByName(session.getServletContext(), "mencached");
		Map<String, UserInfo> userHolder = ( Map<String, UserInfo> ) memcachedHelper.getValueFromMemcache(AppConstant.USER_HOLDER);
		userHolder.remove ( session.getId ( ) );
		memcachedHelper.setValueToMemcache(AppConstant.USER_HOLDER, 0, userHolder);
	}	
	

}
