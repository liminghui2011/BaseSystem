package com.ocean.lmh.base.util;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

public class StringUtils {

	private static String UTF_8 = "UTF-8";
	
//	/**
//	 * 判断字符串是否为空，null和""
//	 * @param str
//	 * @return true null ""
//	 */
//	public static boolean isEmpty(String str){
//		
//		if(str == null || "".equals(str.trim())){
//			return true;
//		}
//		return false;
//	}
//	public static boolean notEmpty(String str){
//		
//		return !isEmpty(str);
//	}
	
	
	/**
	 * 字符串相加，高效率
	 * @param strs
	 * @return
	 */
	public static String plus(String... strs){
		StringBuilder sb = new StringBuilder();
		for(String str : strs){
			sb.append(str);
		}
		return sb.toString();
	}
	
	
	/**
	 * 将byte数据转化为字符串，默认UTF-8编码
	 * @param bytes
	 * @return
	 */
	public static String toString(byte[] bytes ){
		return toString(bytes, UTF_8);
	}
	
	/**
	 * 将byte数据转化为字符串
	 * @param bytes
	 * @param charset 指定字符集
	 * @return
	 */
	public static String toString(byte[] bytes, String charset){
		try {
			return new String(bytes, charset);
		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException(e);
		}
	}
	
	/**
	 * 校验字符串的长度，若超长，则返回截取后的字符串
	 * @param inStr
	 * @param length 指定长度
	 * @return
	 */
	public static final String checkLength(String inStr, int length){
		if(inStr == null){
			return null;
		}
		
		if(inStr.length() <= length){
			return inStr;
		}
		
		return inStr.substring(0, length - 1);
	}
	
	/**
	 * 将null转换为""
	 * @param content
	 * @return
	 */
	public static String nullToEmpty(String content) {

		return content == null ? "" : content;
	}

	/**
	 * 将null转换为"0"
	 * @param content
	 * @return
	 */
	public static String nullToZero(String content) {

		return content == null ? "0" : content;
	}


	/**
	 * 将字符串中的HTML标记符转换为指定字符
	 */
	public static String encodeHTML(String inStr) {
		String result = "";
		if (inStr != null && inStr.length() > 0) {
			result = inStr.trim();
			result = result.replace( "&", "&amp;");
			result = result.replace( "\"", "&quot;");
			result = result.replace(  "<", "&lt;");
			result = result.replace( ">", "&gt;");
			result = result.replace( "#", "&#35;");
			result = result.replace( "%", "&#37;");
			result = result.replace( "'", "&#39;");
		}
		return result;
	}
	
	
	public static String getFileSuffix(String fileName){
		int index = fileName.lastIndexOf(".") + 1;
		return fileName.substring(index);
	}
	
	/**
	 * 将字符串转化为byte数组，默认为UTF-8编码
	 * @param inStr
	 * @return
	 */
	public static byte[] getBytes(String inStr){
		try {
			return inStr.getBytes(UTF_8);
		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException("getBytes error:" + e);
		}
	}
	
	/**
	 * 截取字符串。字符串结束标识只在开始标识之后；返回字符串不包含开始和结束标识。
	 * @param targetStr
	 * @param startStr 字符串开始标识
	 * @param endStr 字符串结束标识
	 * @return
	 */
	public static String subString(String targetStr, String startStr, String endStr){
		int startIndex = targetStr.indexOf(startStr);
		int endIndex = targetStr.indexOf(endStr, startIndex);
		
		String responseUrl = targetStr.substring(startIndex + startStr.length(), endIndex);
		
		return responseUrl;
	}
	
	/**
	 * 截取字符串
	 * 返回的字符串不包含开始标识
	 * @param targetStr
	 * @param startStr 开始标识
	 * @return
	 */
	public static String subString(String targetStr, String startStr){
		int startIndex = targetStr.indexOf(startStr);
		String responseUrl = targetStr.substring(startIndex + startStr.length());
		return responseUrl;
	}
	
	
	/**
	 * 字符串补零。左补零。
	 * @param value 原始字符串
	 * @param length 字符串长度
	 * @return
	 */
	public static String fillZero(String value, int length){
			
		StringBuffer sb = new StringBuffer();
		
		int i = value.length();
		while ((length - i) > 0) {
			sb.append("0");
			i++;
		}
		sb.append(value);
		
		return sb.toString();
	}
	
	/**
	 * 分割字符串，基于长度。
	 * @param tarStr
	 * @param baseLength 基本长度
	 * @return String[] 
	 */
	public static String[] split(String tarStr, int baseLength){
		int length = tarStr.length();
		int count = length / baseLength;
		
		if(baseLength * count != length){
			count += 1;
		}
		
		String[] strs = new String[count]; 
		
		for(int i = 0; i < strs.length; i++ ){
			int startIndex = baseLength * i;
			int endIndex = baseLength*(i+1);
			if(endIndex > length){
				endIndex = length;
			}
			strs[i] = tarStr.substring(startIndex, endIndex);
		}
		
		return strs;
	}
	
	/**
	 * 获取指定字符串在目标字符串中的位置。
	 * @param strs 目标字符串
	 * @param chars 查询字符串
	 * @param seqNo 顺序第几个
	 * @return
	 */
	public static int getPosition(String strs, String chars, int seqNo){
		
		int startIndex = 0;
		while( --seqNo >= 0 ){
			startIndex = strs.indexOf(chars, startIndex) + 1;
		}
		
		return startIndex ;
	}
	
	/**
     * 将一个字符串转化为list集合，然后使用list集合的contain方法来判断一个字符串
     * 是否包含在一个长的字符串里面
     * @param sources 长字符串，以逗号隔开
     * @param target 需要判断的字符串，单个
     * @return 包含返回true，不包含返回false
     */
    public static boolean jugeStringContain(String sources, String target)
    {
    	if (AssertHelper.isNotEmptyString(sources)) {
    		String[] temp = sources.split(",");
            Map<String, String> map = new HashMap<String, String>();
            for (String string : temp) {
				map.put(string, null);
			}
            if (map.containsKey(target))
            {
                return true;
            }
		}
        return false;
    }
}
