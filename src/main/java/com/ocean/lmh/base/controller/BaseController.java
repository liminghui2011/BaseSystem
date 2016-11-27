package com.ocean.lmh.base.controller;

import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import com.ocean.lmh.base.cache.MemcachedHelper;
import com.ocean.lmh.base.constant.AppConstant;
import com.ocean.lmh.system.model.vo.UserInfo;


public class BaseController
{
	@Resource(name = "mencached")
    private MemcachedHelper memcachedHelper;
	
    /**
     * 获取保存在Session中的操作员对象
     */
    public UserInfo getSessionUser(HttpServletRequest request){
        return (UserInfo) request.getSession().getAttribute(AppConstant.USER_INFO);
    }
    
    /**
	 * 从缓存中获取系统定义的参数信息
	 * @param paramKey 参数的键值
	 * @return 参数键paramKey对应的value
	 */
	@SuppressWarnings("unchecked")
	public Map<String, String> getParamsFromMemcached(String paramKey)
	{
		Map<String, Map<String, String>> paramMap = (Map<String, Map<String, String>>) 
    	memcachedHelper.getValueFromMemcache(AppConstant.PARAMETER_MAP);
		return paramMap.get(paramKey);
	}
}
