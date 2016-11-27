package com.ocean.lmh.base.util;

import java.util.ArrayList;
import java.util.List;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.type.TypeFactory;

/**
 * json 帮助类 对象和JSON互相转换
 */
public class JSONHelper {
	private static final ObjectMapper OBJECT_MAPPER	= new ObjectMapper();
	
	/**
	 * 将Object对象转为JSON字符串
	 * @param object
	 * @return
	 */
	public static String toJson(Object object) {
		String json = null;
		try {
			json = OBJECT_MAPPER.writeValueAsString(object);
		} catch (Exception e) {
			throw new RuntimeException("To json error, object is "+object+";exception:"+e);
		}
		return json;
	}

	/**
	 * 将一个JSON字符串转换为Object对象
	 * @param <T>
	 * @param json
	 * @param clazz
	 * @return
	 */
	public static <T> T toObject(String json, Class<T> clazz) {
		T o = null;
		if (json != null) {
			try {
				o = OBJECT_MAPPER.readValue(json, clazz);
			} catch (Exception e) {
				throw new RuntimeException("Json string To object error, json is "+json+";exception:"+e);
			}
		}
		return o;
	}

	/**
	 * 将一个JSON字符串转换为List<T>对象
	 * @param <T>
	 * @param json
	 * @param clazz
	 * @return
	 */
	@SuppressWarnings("deprecation")
	public static <T> List<T> toList(String json, Class<T> clazz) {
		List<T> o = null;
		if (json != null) {
			try {
				o = OBJECT_MAPPER.readValue(json, TypeFactory.collectionType(ArrayList.class, clazz));
			} catch (Exception e) {
				throw new RuntimeException("Json string To List<object> error, json is "+json+";exception:"+e);
			}
		}
		return o;
	}

}
