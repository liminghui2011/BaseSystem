
package com.ocean.lmh.base.cache;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import javax.annotation.Resource;

import org.dom4j.DocumentException;
import org.jfree.util.Log;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.ocean.lmh.base.constant.AppConstant;
import com.ocean.lmh.system.service.GlobalResourceService;

import net.rubyeye.xmemcached.MemcachedClient;
import net.rubyeye.xmemcached.exception.MemcachedException;

/**
 * 操作缓存的工具类，包括获取缓存客户端实例，往缓存保存数据以及从缓存获取数据的方法
 * @author liminghui
 */
@Service("mencached")
public class MemcachedHelper
{
	private static Logger log	= LoggerFactory.getLogger(MemcachedHelper.class);
	
    @Resource(name = "memcachedClient")
    private MemcachedClient client;
    
    @Resource(name = "globalResourceService")
    private GlobalResourceService resourceService;
    
    public MemcachedHelper() throws IOException
    {
    }

    public boolean setValue(String key, int exp, Object obj)
            throws TimeoutException, InterruptedException, MemcachedException
    {
        return client.set(key, exp, obj);
    }

    public Object getValue(String key) throws TimeoutException,
            InterruptedException, MemcachedException
    {
        return client.get(key);
    }
    
    public boolean deleteValue(String key) throws TimeoutException, 
    	InterruptedException,  	MemcachedException
    {
    	return client.delete(key);
    }
    
	/**
	 * 获取系统各项参数的工厂方法，根据传入的参数键key获取其对应的参数值
	 * 并将这个值以传入的key作为键添加到缓存中去。
	 * @param paramKey 参数键
	 * @param groupId
	 * @param terraceType 如果是根据分组id和平台类型获取待跳转的首页时需要groupId和terraceType这两个参数，其他情况都为null
	 * @return 根据传入的key去数据库获取相应的值
	 */
    public Object paramsFactory(String paramKey,String groupId,String terraceType)
    {
    	if (AppConstant.PARAMETER_MAP.equals(paramKey)) 
    	{
			//系统预置的各项参数键值对
    		return resourceService.loadParameter();
		}
    	else if (AppConstant.PROVINCE_MAP.equals(paramKey))
    	{
    		//加载各个省份的信息    		
    		try 
    		{
    			return resourceService.loadProvince();
			} 
    		catch (IOException e) 
			{
				Log.error("load province info error",e);
			}
    	}
    	else if (AppConstant.CITY_MAP.equals(paramKey))
    	{
    		//加载各省份中各个城市的信息
    		try 
    		{
    			return resourceService.loadCity();
			} 
    		catch (IOException e) 
			{
				Log.error("load city info error",e);
			}
    	}
    	else if (AppConstant.SYSTEM_MENU_TREE.equals(paramKey))
    	{
    		//加载所有菜单树信息
    		resourceService.loadAllTree();
    	}
    	else if (AppConstant.PRIVILEGE_URI_LIST.equals(paramKey))
    	{
    		//加载系统所有的权限url信息
    		return resourceService.loadPrivilegeUriList();
    	}
    	else if (AppConstant.LOG_CONFIG_VALUE.equals(paramKey))
    	{
    		//加载系统日志配置文件信息
    		try 
    		{
    			return resourceService.loadLogConfigValue();
			}
    		catch (IOException e) 
			{
				Log.error("get inputstream error",e);
			}
    		catch (DocumentException e) 
			{
				Log.error("read xml document error",e);
			}
    	}
    	else if (AppConstant.SYSTEM_STATUS_MAP.equals(paramKey))
    	{
    		//加载系统各种审核状态信息
    		return resourceService.loadSystemStatus();
    	}
		return null;
    }
    
    /**
     * 通过传入的key获取缓存中的数据
     * @param key 传入的key值
     * @return 缓存中key对应的value
     */
    public Object getValueFromMemcache(String key)
    {
    	Object object = null;
    	try {
			object = getValue(key);
			if (object == null)
			{
				object = paramsFactory(key,null,null);
			}
		} catch (TimeoutException e) 
		{
			log.error("get value from cache occur error TimeoutException",e);
			paramsFactory(key,null,null);
		} 
		catch (InterruptedException e) 
		{
			log.error("get value from cache occur error InterruptedException",e);
			paramsFactory(key,null,null);
		} 
		catch (MemcachedException e) 
		{
			log.error("get value from cache occur error MemcachedException",e);
			paramsFactory(key,null,null);
		}
		return object;
    }
    
    /**
     * 将传入的value值保存到键为key的缓存中去
     * @param key 传入的key值
     * @param value 待保存的value值
     * @param time 需要保存的时间长度，0为永不过期；显示设置的值不能大于2529000秒(30天)
     * 				当超过这个值时，会把时间理解为unix时间戳格式也就是距离1970.01.01的秒数偏移量。
     */
    public void setValueToMemcache(String key,Integer time, Object value)
    {
    	try {
    		if (value != null) 
    		{
    			setValue(key,time,value);
			}			
		} catch (TimeoutException e) 
		{
			log.error("set value to cache occur error TimeoutException",e);
			paramsFactory(key,null,null);
		} 
		catch (InterruptedException e) 
		{
			log.error("set value to cache occur error InterruptedException",e);
			paramsFactory(key,null,null);
		} 
		catch (MemcachedException e) 
		{
			log.error("set value to cache occur error MemcachedException",e);
			paramsFactory(key,null,null);
		}
    }

    /**
     * 从缓存中移除指定key值的value值
     * @param key 
     */
    public void deleteValueFromMemcache(String key)
    {
    	try {
			deleteValue(key);
		} catch (TimeoutException e) 
		{
			log.error("delete value from cache occur error TimeoutException",e);
		} 
		catch (InterruptedException e) 
		{
			log.error("delete value from cache occur error InterruptedException",e);
		} 
		catch (MemcachedException e) 
		{
			log.error("delete value from cache occur error MemcachedException",e);
		}
    }
}
