package com.ocean.lmh.base.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 防止表单重复提交的枚举类<br>
 * 原理：
 * 		给所有的url请求加一个拦截器，在拦截器里面用java的UUID生成一个随机的UUID并把这个UUID放到session里面，<br>
 * 		然后在浏览器做数据提交的时候将此UUID提交到服务器。服务器在接收到此UUID后，检查一下该UUID是否已经被提交，<br>
 * 		如果已经被提交，则不让逻辑继续执行下去…<br>
 * 使用方法：<br>
 * 		在controller中记录列表方法上增加@Token(save=true)，在某项操作controller方法上添加@Token(remove=true)，<br>
 * 		比如添加操作，则必须在添加到数据库的方法上加@Token(remove=true)，而不是在进入添加页面的请求方法上，其他的类似,<br>
 *		另外，在view里在form里增加下面代码：<br>
 *		<input type="hidden" name="token" value="${token}" />
 * 详细实现代码请参考TokenInterceptor
 * @see com.lutongnet.cps.system.interceptor.TokenInterceptor		
 * @author xuyaling
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Token
{
    boolean save() default false;
    boolean remove() default false;
}
