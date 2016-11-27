package com.ocean.lmh.system.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ocean.lmh.base.util.HttpResponseUtils;
import com.ocean.lmh.base.wrapper.InfoLogServletRequest;
import com.ocean.lmh.base.wrapper.InfoLogServletResponse;

public class RequestResponseSetFilter implements Filter{
	
	private static Logger log	= LoggerFactory.getLogger(RequestResponseSetFilter.class);
	
	public void init(FilterConfig paramFilterConfig) throws ServletException {
		
	}
	
	public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain chain) throws IOException, ServletException {

		HttpServletRequest httpRequest = (HttpServletRequest) servletRequest;
		InfoLogServletRequest request = new InfoLogServletRequest(httpRequest);
		HttpServletResponse httpResponse = (HttpServletResponse)servletResponse;
		InfoLogServletResponse response = new InfoLogServletResponse(httpResponse);
		
		chain.doFilter(request, response);

		byte[] datas = response.getResponseData();
		httpResponse.setHeader("Content-Length", new Integer(datas.length).toString());
		httpResponse.setHeader("Content-Type", response.getContentType());
		HttpResponseUtils.write(httpResponse, datas);
		
		log.debug("response.getResponseData()::"+datas);
	}

	public void destroy() {
		
	}

}
