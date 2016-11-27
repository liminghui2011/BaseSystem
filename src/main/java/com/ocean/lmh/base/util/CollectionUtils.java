package com.ocean.lmh.base.util;

import java.lang.reflect.Array;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * 字符串数据与Collection对象转化方法集合
 *
 */
public class CollectionUtils {
	
	
	/**
	 * json字符串转化为Map数据结构
	 * @param dataStr 传入字符传
	 * @param splitSymbol 字符串分隔符
	 * @return Map<String, String>
	 */
	public static Map<String, String> parseToMap(String dataStr, String splitSymbol){
		
		if(dataStr==null){
			return new HashMap<String, String>();
		}
		
		dataStr = dataStr.trim();
		dataStr = dataStr.substring(0, dataStr.length());
		String[] paramsStr = dataStr.split(splitSymbol);
		
		Map<String, String> map = new HashMap<String, String>();
		for (String param : paramsStr) {
			int index = param.indexOf("=");
			if(index != -1){
				String key = param.substring(0, index);
				String value = param.substring(index+1);
				if(!"null".equalsIgnoreCase(value)){
					map.put(key.trim(), value.trim());
				}
			}
		}
		
		return map;
	}
	
	
	/**
	 * 字符串转化为Map数据结构, 默认已';'为分割字符
	 * @param mapStr 传入字符传
	 * @return Map<String, String>
	 */
	public static Map<String, String> parseToMap(String mapStr){
		return parseToMap(mapStr,",");
	}
	
	/**
	 * 将json字符串转化为Map对象
	 * @param jsonStr 输入JSON字符串
	 * @param splitSymbol 数据分隔符 
	 * @return Map<String, String>
	 */
	public static Map<String, String> jsonParseToMap(String jsonStr, String splitSymbol){
		if(jsonStr == null){
			return new HashMap<String, String>();
		}
		
		String dataStr = jsonStr.trim();
		dataStr = dataStr.substring(1, dataStr.length() -1 );
		
		return parseToMap(dataStr, splitSymbol);
	}
	
	/**
	 * 将json字符串转化为Map对象， 数据分隔符为","
	 * @param jsonStr 输入JSON字符串
	 * @return Map<String, String>
	 */
	public static Map<String, String> jsonParseToMap(String jsonStr){
		return parseToMap(jsonStr, ",");
	}
	
	
	
	/**
	 * 获取数组对象具体数值
	 * eg: {1,2,3,4,5} --> 1,2,3,4,5
	 * 
	 * @param valueObj 传入对象
	 * @return String 输入数值，数值间用逗号(',')分隔
	 */
	@SuppressWarnings("rawtypes")
	public static String getArraysString(Object valueObj){
		
		Class clazz = valueObj.getClass();
		
		if(clazz.isArray()){
			StringBuilder arrayString = new StringBuilder();
			int length = Array.getLength(valueObj);
			
			for(int i = 0 ; i < length; i++){
				Object obj = Array.get(valueObj, i);
				
				if(i != 0){
					arrayString.append(",");
				}
				arrayString.append(obj.toString());
			}
			return arrayString.toString();
		}
		
		return valueObj.toString();
	}
	
	/**
	 * 将Map对象转化为字符串，主要针对Map对象中存有数组对象
	 * @param map 
	 * @return String 
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static String toString(Map map) {

		if (map == null) {
			return "{}";
		}

		Set<Map.Entry> enties = map.entrySet();
		if (enties.size() == 0) {
			return "{}";
		}

		StringBuilder sb = new StringBuilder("{");

		for (Map.Entry entry : enties) {

			Object valueObj = entry.getValue();
			String value = getArraysString(valueObj);

			sb.append(",").append(entry.getKey()).append("=").append(value);
		}
		sb.append("}");

		sb.replace(1, 2, "");
		return sb.toString();
	}
}
