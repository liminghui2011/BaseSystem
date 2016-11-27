package com.ocean.lmh.base.util;

import java.io.ByteArrayOutputStream;
import java.io.PrintWriter;

public class ExceptionUtils {
	
	/**
	 * 获取异常信息
	 * @param e
	 * @return
	 */
	public static String getErrorMsg(Exception e){
		
		ByteArrayOutputStream buf = new ByteArrayOutputStream();
		e.printStackTrace(new PrintWriter(buf, true));
		String expMessage = buf.toString();
		
		return expMessage;
		
	}
	
	/**
	 * 此方法可以获取类之间调用顺序
	 * @return String 
	 */
	public static String MethodInvokeList() {
		StackTraceElement[] stackTraceElements = new Exception().getStackTrace();
		StringBuilder sb = new StringBuilder();
		for (StackTraceElement stackTraceElement : stackTraceElements) {
			sb.append(stackTraceElement.getClassName())
			.append(":")
			.append(stackTraceElement.getMethodName())
			.append("\r\n");
		}
		return sb.toString();
	}
}
