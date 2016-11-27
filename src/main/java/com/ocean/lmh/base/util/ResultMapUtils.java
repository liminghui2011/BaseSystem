package com.ocean.lmh.base.util;

import java.util.HashMap;
import java.util.Map;

public class ResultMapUtils {
	
	private static Map<String,Object> base(String key, int errorCode) {
		Map<String,Object> map = new HashMap<String,Object>();
		map.put(key, errorCode);
		return map;
	}
	
	public static Map<String,Object> result(int errorCode) {
		return base("result", errorCode);
	}
	
	public static Map<String,Object> success() {
		return base("result", 0);
	}
	
	public static Map<String,Object> failture() {
		return base("result", 1);
	}
	
	public static Map<String,Object> sysErr() {
		return base("result", 100);
	}
	
	
	
}
