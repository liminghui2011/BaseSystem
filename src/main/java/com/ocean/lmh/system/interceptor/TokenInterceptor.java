package com.ocean.lmh.system.interceptor;

import java.lang.reflect.Method;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.ocean.lmh.base.annotation.Token;

/**
 * 拦截所有的Url请求，判断是否要生成或移除Token防止表单重复提交的问题
 * @author xuyaling
 *
 */
public class TokenInterceptor extends HandlerInterceptorAdapter 
{
	/**
     * 防止表单重复提交的引用变量
     */
    public final static String SYSTEM_OTHERFOLDER_TOKEN = "token";
	
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception 
    {
        if (handler instanceof HandlerMethod)
        {
            HandlerMethod handlerMethod = (HandlerMethod) handler;
            Method method = handlerMethod.getMethod();
            Token annotation = method.getAnnotation(Token.class);
            
            //判断某个请求方法上是否使用了Token注解
            if (annotation != null) 
            {
            	//如果该请求方法使用了Token注解,并且annotation.save()值为true（默认为false）,则需要生成新的Token保存到session中
                boolean needSaveSession = annotation.save();
                if (needSaveSession) 
                {
                    request.getSession(false).setAttribute(SYSTEM_OTHERFOLDER_TOKEN, UUID.randomUUID().toString());
                }
                
                //如果该请求方法使用了@Token注解，并且annotation.remove()值为true（默认为false），则在本次表单提交操作完成后删除session中保存的Token标识
                boolean needRemoveSession = annotation.remove();
                if (needRemoveSession) 
                {
                    if (isRepeatSubmit(request)) 
                    {
                        return false;
                    }
                    request.getSession(false).removeAttribute(SYSTEM_OTHERFOLDER_TOKEN);
                }
            }
            return true;
        } 
        else 
        {
            return super.preHandle(request, response, handler);
        }
    }
 
    /**
     * 根据表单提交后的Token参数的值与session中保存的Token的值是否相等
     * 判断表单是否重复提交
     * @param request
     * @return 返回true则是重复提交
     */
    private boolean isRepeatSubmit(HttpServletRequest request) 
    {
        String serverToken = (String) request.getSession(false).getAttribute(SYSTEM_OTHERFOLDER_TOKEN);
        if (serverToken == null)
        {
            return true;
        }
        String clinetToken = request.getParameter(SYSTEM_OTHERFOLDER_TOKEN);
        if (clinetToken == null)
        {
            return true;
        }
        if (!serverToken.equals(clinetToken))
        {
            return true;
        }
        return false;
    }
}
