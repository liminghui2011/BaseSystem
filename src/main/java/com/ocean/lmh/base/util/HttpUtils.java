package com.ocean.lmh.base.util;

import java.net.URLEncoder;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.ocean.lmh.base.constant.AppConstant;


public class HttpUtils {

	/**
	 * 获取请求信息（uri+请求参数）
	 * 
	 * @param request
	 * @param excludeParamList
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public static String getRequestInfo ( HttpServletRequest request , boolean trimContextPath , List<String> excludeParamList ) throws Exception {
		String uri = request.getRequestURI ( );
		if ( trimContextPath ){
			String contextPath = request.getContextPath ( );
			if ( uri.startsWith ( contextPath ) ){
				uri = uri.substring ( contextPath.length ( ) );
			}
		}
		Map<String, String [ ]> paramMap = ( HashMap<String, String [ ]> ) request.getParameterMap ( );
		uri = uri + "?";
		if ( !paramMap.isEmpty ( ) ){
			for ( Map.Entry<String, String [ ]> entry : paramMap.entrySet ( ) ){
				String key = entry.getKey ( );
				if ( !excludeParamList.contains ( key ) ){
					String [ ] values = entry.getValue ( );
					for ( int i = 0 ; i < values.length ; ++i ){
						uri += ( key + "=" + URLEncoder.encode ( values [ i ] , "utf-8" ) + "&" );
					}
				}
			}
		}
		return uri.substring ( 0 , uri.length ( ) - 1 );
	}

	public static String getRequestInfo ( HttpServletRequest request , boolean trimContextPath , String ... excludeParams ) throws Exception {
		return getRequestInfo ( request , trimContextPath , Arrays.asList ( excludeParams ) );
	}

	/**
	 * 保存请求URI和参数,及不做保存的参数
	 * 
	 * @param request
	 * @param scope
	 * @param name
	 * @param excludeParamList
	 * @throws Exception
	 */
	public static void markRequestInfo ( HttpServletRequest request , String scope , String name , List<String> excludeParamList ) throws Exception {
		String uri = getRequestInfo ( request , false , excludeParamList );
		if ( AppConstant.REQUEST_SCOPE.equals ( scope ) ){
			request.setAttribute ( name , uri );
		}else if ( AppConstant.SESSION_SCOPE.equals ( scope ) ){
			request.getSession ( ).setAttribute ( name , uri );
		}
	}

	public static void markRequestInfo ( HttpServletRequest request , String scope , String name , String ... excludeParams ) throws Exception {
		markRequestInfo ( request , scope , name , Arrays.asList ( excludeParams ) );
	}

	public static void markRequestInfo ( HttpServletRequest request , String name , String ... excludeParams ) throws Exception {
		markRequestInfo ( request , AppConstant.SESSION_SCOPE , name , Arrays.asList ( excludeParams ) );
	}

	/**
	 * 获取保存的请求信息
	 * 
	 * @param request
	 * @param scope
	 * @param trimContextPath
	 * @param name
	 * @param defaultValue
	 * @return
	 */
	public static String getMarkRequestInfo ( HttpServletRequest request , String scope , boolean trimContextPath , String name , String defaultValue ) {
		String info = null;
		if ( AppConstant.REQUEST_SCOPE.equals ( scope ) ){
			info = ( String ) request.getAttribute ( name );
		}else if ( AppConstant.SESSION_SCOPE.equals ( scope ) ){
			info = ( String ) request.getSession ( ).getAttribute ( name );
		}

		if ( info == null || info.trim ( ).length ( ) == 0 ){
			info = defaultValue;
		}

		if ( trimContextPath ){
			String contextPath = request.getContextPath ( );
			if ( info.startsWith ( contextPath ) ){
				info = info.substring ( contextPath.length ( ) );
			}
		}

		return info;
	}

	public static String getMarkRequestInfo ( HttpServletRequest request , String name , String defaultValue ) {
		return getMarkRequestInfo ( request , AppConstant.SESSION_SCOPE , true , name , defaultValue );
	}

	public static String getMarkRequestInfo ( HttpServletRequest request , String name ) {
		return getMarkRequestInfo ( request , AppConstant.SESSION_SCOPE , true , name , null );
	}
}
