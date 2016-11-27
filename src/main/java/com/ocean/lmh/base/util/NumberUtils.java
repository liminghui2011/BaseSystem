package com.ocean.lmh.base.util;

public class NumberUtils {
	
	/**
	 * 将字符串转换为数字，如果转换异常，则返回-1
	 */
	public static int parseInt(String n) {

		try {
			return Integer.parseInt(n);
		} catch (java.lang.NumberFormatException nfe) {
			return -1;
		}
	}
	public static int parseInt(String n, int defaultValue) {
		
		try {
			return Integer.parseInt(n);
		} catch (java.lang.NumberFormatException nfe) {
			return defaultValue;
		}
	}
	
	/**
	 * 将字符串转换为数字，如果转换异常，则返回-1
	 * 
	 */
	public static int parseInt(Object number) {
		
		if(number == null){
			return -1;
		}
		return parseInt(number.toString());
	}
	
	public static int parseInt(Object number, int defaultValue) {
		
		if(number == null){
			return defaultValue;
		}
		return parseInt(number.toString());
	}
	
	/**
	 * 生成随机数字
	 * @param length  随机数长度
	 * @return
	 */
	public static String random(int length){
		
		StringBuilder numberBuilder = new StringBuilder();
		for(int i = 0 ; i < length ; i++){
			int randomNum = (int)(Math.random()*10 - 1);
			numberBuilder.append(randomNum);
		}
		return numberBuilder.toString();
	}
	
}
