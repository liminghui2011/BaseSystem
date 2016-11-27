package com.ocean.lmh.base.exception;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.SimpleMappingExceptionResolver;

import com.ocean.lmh.base.annotation.Log;
import com.ocean.lmh.base.constant.ThreadLocalConstants;
import com.ocean.lmh.base.util.HttpRequestUtils;
import com.ocean.lmh.base.util.HttpResponseUtils;

/**
 * @author 凌剑东
 */
public class CustomizeExceptionResolver extends SimpleMappingExceptionResolver {

	private Logger			log	= LoggerFactory.getLogger ( CustomizeExceptionResolver.class );

	@Override
	protected ModelAndView doResolveException ( HttpServletRequest request , HttpServletResponse response , Object handler , Exception ex ) {
		String content = "";
		StringWriter sw = new StringWriter();
		ex.printStackTrace(new PrintWriter(sw));
		
		if ( handler instanceof HandlerMethod ){
			HandlerMethod h = ( HandlerMethod ) handler;
			Log logAnnotation = h.getMethodAnnotation ( Log.class );
			if ( logAnnotation != null ){
				content = logAnnotation.value ( );
				
				//系统抛出异常时不在数据库中记录日志，值保存到日志文件。
				//modify by xiaohe at 2013-08-31 start
				
				//UserInfo user = ( UserInfo ) request.getSession ( ).getAttribute ( AppConstant.USER_INFO );
				//String actor = user == null ? "system" : user.getUserid ( );
				//String uri = request.getRequestURI ( );
				//String paramInfo = "";
//				Map<String, String [ ]> params = request.getParameterMap ( );
//				for ( Map.Entry<String, String [ ]> entry : params.entrySet ( ) ){
//					String key = entry.getKey ( ) + ":";
//					String [ ] values = entry.getValue ( );
//					for ( int i = 0 ; i < values.length ; ++i ){
//						key += values [ i ] + ",";
//					}
//					key = key.substring ( 0 , key.length ( ) - 1 );
//					key += "<br/>";
//					paramInfo += key;
//				}

				//ActionLogDetail detail = new ActionLogDetail ( );
				//detail.setContent ( sw.toString ( ) );
//				logService.addLogDetail(detail);
//				ActionLog actionLog = new ActionLog ( );
//				actionLog.setContent ( content );
//				actionLog.setActor ( actor );
//				actionLog.setType ( ActionResult.ERROR );
//				actionLog.setParam ( paramInfo );
//				actionLog.setUri ( uri );
//				actionLog.setAddDate ( new Date() );
//				actionLog.setDetailId ( detail.getId() );
//				logService.addLog ( actionLog );
				//modify by xiaohe at 2013-08-31 end
				
			}
		}
		request.setAttribute(ThreadLocalConstants.EXCEPTION_INFO, sw.toString());
		
		log.error ( content + " 操作异常：" , ex );
		
		boolean isAjaxRequest = HttpRequestUtils.isAjaxRequest(request);
		if ( isAjaxRequest){
			try{
				HttpResponseUtils.writeStatus(response, "系统异常");
			}catch ( IOException e ){
				log.error ( "" , e );
			}
			return null;
		}
		return super.doResolveException ( request , response , handler , ex );
	}

}
