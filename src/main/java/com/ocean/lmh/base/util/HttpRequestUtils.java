package com.ocean.lmh.base.util;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

public class HttpRequestUtils {

	/**
	 * 获取请求的URI,不包含ip、端口和项目名称 eg：in >
	 * http://127.0.0.1:8080/project/user/login.do out > user/login.do
	 * 
	 * @param request
	 * @return
	 */
	public static String getRequestUri(HttpServletRequest request) {
		String contextPath = getContextPath(request);
		String requestUri = request.getRequestURI().substring(
				contextPath.length() + 1); // 去掉上下文路径和"/"

		return requestUri;
	}

	/**
	 * 获取项目的URI eg: in > http://127.0.0.1:8080/project/user/login.do out >
	 * /project
	 * 
	 * @param request
	 * @return
	 */
	public static String getContextPath(HttpServletRequest request) {
		String contextPath = request.getSession().getServletContext()
				.getContextPath();

		return contextPath;
	}

	/**
	 * 获取项目的URL eg：in > http://127.0.0.1:8080/project/user/login.do out >
	 * http://127.0.0.1:8080/project/
	 * 
	 * @param request
	 * @return
	 */
	public static String getProjectUrl(HttpServletRequest request) {
		String url = request.getRequestURL().toString();

		int endIndex = StringUtils.getPosition(url, "/", 4);

		String hostProject = url.substring(0, endIndex);

		return hostProject;

	}

	/**
	 * eg：in > http://127.0.0.1:8080/project/user/login.do out >
	 * http://127.0.0.1:8080/
	 * 
	 * @param request
	 * @return
	 */
	public static String getProjectDomain(HttpServletRequest request) {
		String projectDomain = request.getScheme() + "://"
				+ request.getServerName() + ":" + request.getServerPort() + "/";

		return projectDomain;

	}

	/**
	 * 获取项目的绝对路径 eg: D:/server/tomcat6/webapps/ROOT/
	 * 
	 * @param request
	 * @return
	 */
	public static String getProjectAbsoultPath(HttpServletRequest request) {
		return request.getSession().getServletContext().getRealPath("/");
	}

	/**
	 * 获取项目Class文件的绝对路径 eg: D:/server/tomcat6/webapps/ROOT/WEB-INF/classes/
	 * 
	 * @param request
	 * @return
	 */
	public static String getProjectClassAbsoultPath() {
		return HttpRequestUtils.class.getResource("/").getPath().substring(1);
	}

	/**
	 * 判断请求内容是否为JSON格式
	 * 
	 * @param request
	 * @return true 表示为JSON格式
	 */
	public static boolean isJsonContent(HttpServletRequest request) {
		String contentType = request.getHeader("Content-Type");
		if (contentType == null
				|| contentType.indexOf("application/json") == -1) {
			return false;
		}

		return true;
	}

	/**
	 * 判断是否是AJAX请求
	 * 
	 * @param request
	 * @return true 表示是AJAX请求
	 */
	public static boolean isAjaxRequest(HttpServletRequest request) {

		boolean isAjaxRequest = "XMLHttpRequest".equals(request
				.getHeader("X-Requested-With"))
				|| request.getParameter("ajax") != null;

		return isAjaxRequest;
	}

	/**
	 * 根据传入的bean id的名称获取该bean的实例对象
	 * @param servletContext 上下文对象
	 * @param beanName bean id的名称
	 * @return 实例对象
	 */
	public static Object getBeanByName(ServletContext servletContext,String beanName) 
	{
		WebApplicationContext applicationContext = WebApplicationContextUtils.getWebApplicationContext(servletContext);
		return applicationContext.getBean(beanName);
	}

}
