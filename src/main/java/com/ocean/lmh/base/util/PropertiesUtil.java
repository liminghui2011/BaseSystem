package com.ocean.lmh.base.util;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.PropertyResourceBundle;

public class PropertiesUtil {

	private static final String BASE_PROPERTIES_NAME = "config";

	/**
	 * 根据给定的key值从配置文件中获取对应的值
	 * 
	 * @param key
	 * @return  该key对应的值
	 */
	public static String readValue(String key) {
		PropertyResourceBundle props = getProperties();
		return props.getString(key);
	}

	/**
	 * 读取所以的配置信息
	 * 
	 * @return map结果集合
	 */
	public static Map<String, String> readProperties() {
		PropertyResourceBundle props = getProperties();
		Map<String, String> map = new HashMap<String, String>();
		Enumeration<String> en = props.getKeys();
		while (en.hasMoreElements()) {
			String key = (String) en.nextElement().toString();
			String Property = props.getString(key);
			map.put(key, Property);
		}
		return map;
	}

	private static PropertyResourceBundle getProperties() {
		PropertyResourceBundle resourceBundle = (PropertyResourceBundle) PropertyResourceBundle
	    .getBundle(BASE_PROPERTIES_NAME);
		return resourceBundle;
	}
}
