package com.ocean.lmh.base.util;

import java.util.Collection;
import java.util.Map;

/**
 * 非空判断工具类
 * 
 * @author liminghui
 */

public final class AssertHelper
{
    /**
     * 判断字符串是否为空
     * 3种为空情况：null/空字符串/纯空格字符串
     * @param  str 源字符串
     * @return  true为空，false为非空
     */
    public static boolean isEmptyString(String str)
    {
        if(null == str || "".equals(str.trim()))
        {
            return true;
        }
        return false;
    }
    
    /**
     * 判断字符串是否不为空
     * 3种为空情况：null/空字符串/纯空格字符串
     * @param str 源字符串
     * @return  true为非空，false为空
     */
    public static boolean isNotEmptyString(String str)
    {
        if(null == str || "".equals(str.trim()))
        {
            return false;
        }
        return true;
    }
    
    /**
     * 判断集合类是否不为空
     * 3种为空情况：null/集合大小为0
     * @param collect     源集合对象
     * @return  true为非空，false为空
     */
    public static <T> boolean isNotEmptyCollection(Collection<T> collect)
    {
        if(null == collect || collect.isEmpty())
        {
            return false;
        }
        
        return true;
    }
    
    /**
     * 判断集合类是否为空
     * 3种为空情况：null/集合大小为0
     * @param collect     源集合对象
     * @return  true为空，false为非空
     */
    public static <T> boolean isEmptyCollection(Collection<T> collect)
    {
        if(null == collect || collect.isEmpty())
        {
            return true;
        }
        
        return false;
    }
    
    /**
     * 判断Map类是否为空
     * 3种为空情况：null/集合大小为0
     * @param collect     源Map对象
     * @return  true为空，false为非空
     */
    public static <T> boolean isEmptyMap(Map<T,T> map)
    {
        if(null == map || map.isEmpty())
        {
            return true;
        }
        
        return false;
    }
    
    /**
     * 判断集合类是否为空
     * 3种为空情况：null/集合大小为0
     * @param collect     源集合对象
     * @return  true为非空，false为空
     */
    public static <T> boolean isNotEmptyMap(Map<T,T> map)
    {
        if(null == map || map.isEmpty())
        {
            return false;
        }
        
        return true;
    }
}
