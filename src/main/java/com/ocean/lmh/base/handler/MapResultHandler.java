package com.ocean.lmh.base.handler;

import java.util.Map;

import org.apache.ibatis.reflection.DefaultReflectorFactory;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.SystemMetaObject;
import org.apache.ibatis.session.ResultContext;
import org.apache.ibatis.session.ResultHandler;

/**
 * 用于输出Map类型result的handler类
 * 〈功能详细描述〉
 * @author     肖贺
 * @see         org.apache.ibatis.session.ResultHandler,org.apache.ibatis.executor.result.DefaultMapResultHandler<K, V>
 */
@SuppressWarnings("rawtypes")
public class MapResultHandler<K,V> implements ResultHandler
{
    /**
     * 用于保存结果集的map
     */
    private Map<K,V> resultMap;
    
    /**
     * 用于从结果集的值对象中提取结果集map的key
     */
    private String mapKey;
    
    public MapResultHandler(){}
    
    //考虑map的value不是值对象的情况
    public MapResultHandler(Map<K,V> resultMap,String mapKey)
    {
        this.resultMap = resultMap;
        this.mapKey = mapKey;
    }

    @SuppressWarnings("unchecked")
	@Override
    public void handleResult(ResultContext context)
    {
        V value = (V) context.getResultObject();
        MetaObject mo = MetaObject.forObject(context, SystemMetaObject.DEFAULT_OBJECT_FACTORY, 
        		SystemMetaObject.DEFAULT_OBJECT_WRAPPER_FACTORY, new DefaultReflectorFactory());
        K key = (K) mo.getValue(mapKey);
        resultMap.put(key, value);
    }

    public Map<K,V> getResultMap()
    {
        return resultMap;
    }

    public void setResultMap(Map<K,V> map)
    {
        this.resultMap = map;
    }

    public String getMapKey()
    {
        return mapKey;
    }

    public void setMapKey(String mapKey)
    {
        this.mapKey = mapKey;
    }
}
