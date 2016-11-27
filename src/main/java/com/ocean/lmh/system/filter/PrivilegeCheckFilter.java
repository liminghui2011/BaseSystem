package com.ocean.lmh.system.filter;

import java.io.IOException;
import java.util.List;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ocean.lmh.base.cache.MemcachedHelper;
import com.ocean.lmh.base.constant.AppConstant;
import com.ocean.lmh.base.util.HttpRequestUtils;
import com.ocean.lmh.base.util.HttpResponseUtils;
import com.ocean.lmh.system.model.vo.UserInfo;

public class PrivilegeCheckFilter implements Filter {

	private static Logger log = LoggerFactory
			.getLogger(PrivilegeCheckFilter.class);

	private MemcachedHelper memcachedHelper;

	public void destroy() 
	{

	}

	@SuppressWarnings("unchecked")
	public void doFilter(ServletRequest inRequest, ServletResponse inResponse,
			FilterChain chain) throws IOException, ServletException 
	{
		HttpServletRequest request = (HttpServletRequest) inRequest;
		HttpServletResponse response = (HttpServletResponse) inResponse;
		HttpSession session = request.getSession();

		memcachedHelper = (MemcachedHelper) HttpRequestUtils.getBeanByName(
				session.getServletContext(), "mencached");

		UserInfo user = (UserInfo) session.getAttribute(AppConstant.USER_INFO);
		//Map<Integer, List<Integer>> statusmap = (Map<Integer, List<Integer>>) memcachedHelper
		//		.getValueFromMemcache(AppConstant.SYSTEM_STATUS_MAP);
		if (user == null) 
		{
			chain.doFilter(request, response);
			return;
		}

		// 用户已登录
		List<String> privilegeUriList = (List<String>) memcachedHelper
				.getValueFromMemcache(AppConstant.PRIVILEGE_URI_LIST);
		log.debug("privilege url list::" + privilegeUriList);
		
		List<String> allowUriList = user.getAllowUriList();
		String requestUri = HttpRequestUtils.getRequestUri(request);
		if (privilegeUriList.contains(requestUri)) 
		{			
			// 访问受权限保护的资源，判断有没有权限
			if (!allowUriList.contains(requestUri)) 
			{				
				// 没有权限
				String info = "没有权限";
				String deniedPage = HttpRequestUtils.getContextPath(request)
						+ AppConstant.SYSTEM_INFO_URL;
				session.setAttribute("info", info);
				HttpResponseUtils.sendResponse(request, response, info,
						deniedPage);
				return;
			} 
			
		}
		chain.doFilter(request, response);
	}

	public void init(FilterConfig filterConfig) throws ServletException {

	}

}
