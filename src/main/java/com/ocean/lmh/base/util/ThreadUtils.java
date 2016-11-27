package com.ocean.lmh.base.util;

public class ThreadUtils {

	@SuppressWarnings("static-access")
	public static void sleep(long time){
		try {
			Thread.currentThread().sleep(time);
		} catch (InterruptedException e) {
			throw new RuntimeException(e);
		}
	}
	
	public static void wait(Object obj, long time){
		try {
			obj.wait(time);
		} catch (InterruptedException e) {
			throw new RuntimeException(e);
		}
	}
	
	public static void wait(Object obj){
		try {
			obj.wait();
		} catch (InterruptedException e) {
			throw new RuntimeException(e);
		}
	}
}
