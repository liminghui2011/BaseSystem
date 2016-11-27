package com.ocean.lmh.system.listener;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ocean.lmh.base.constant.AppConstant;
import com.ocean.lmh.base.util.HttpRequestUtils;
import com.ocean.lmh.system.model.vo.UserInfo;
import com.ocean.lmh.system.service.GlobalResourceService;

/**
 * 系统启动时，加载数据字典
 *
 */
public class SystemParamsLoadingListener implements ServletContextListener {
	
	private static Logger log = LoggerFactory.getLogger(SystemParamsLoadingListener.class);
	
	
	public void contextInitialized(ServletContextEvent servletContextEvent) {
		ServletContext servletContext = servletContextEvent.getServletContext();		
		GlobalResourceService resourceService = (GlobalResourceService) HttpRequestUtils.getBeanByName(servletContext, "globalResourceService");
		
		resourceService.loadParameter();
		log.info("加载系统参数成功");
		
		resourceService.loadAllTree();
		log.info("加载所有菜单树成功");
		
		resourceService.loadPrivilegeUriList();
		log.info("加载系统权限URI成功");

		
		try 
		{
			resourceService.loadProvince();
			resourceService.loadCity();
			log.info("加载省份、地市信息成功");
		}
		catch (IOException e) 
		{
			log.error("加载省份、地市信息失败", e);
			throw new RuntimeException(e);
		}
		
		try 
		{
			resourceService.loadLogConfigValue();
			log.info("加载日志配置文件成功");
		} 
		catch (Exception e)
		{
			log.error("加载日志配置文件失败", e);
			throw new RuntimeException(e);
		}
		
		try 
		{
			resourceService.loadSystemStatus();
			log.info("加载系统预置状态参数成功");
		} 
		catch (Exception e) 
		{
			log.error("加载系统预置状态参数失败", e);
			throw new RuntimeException(e);
		}
		
		// 当前正在使用的用户列表
		Map<String, UserInfo> userHolder = new HashMap<String, UserInfo>();
		resourceService.setParamsToMemcache(AppConstant.USER_HOLDER, 0, userHolder);

		// 用户ID和SessionId关联
		Map<String, String> sessionidHolder = new HashMap<String, String>();
		resourceService.setParamsToMemcache(AppConstant.SESSIONID_HOLDER, 0, sessionidHolder);
	}

	public void contextDestroyed(ServletContextEvent servletContextEvent) {
	}


}
