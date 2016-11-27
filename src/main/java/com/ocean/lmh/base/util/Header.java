package com.ocean.lmh.base.util;

/**
 * Header类中主要有两个属性：
 * String text ：excel表中表头对应的列名
 * String fieldName ： excel表中列名对应的实体类中属性的属性名 
 * 用于在导出Excel表格时，动态创建表头
 * @author xuyaling
 */
public class Header {
	
	/** excel表中表头对应的列名 */
	private String text;
	
	/** excel表中列名对应的实体类中属性的属性名   */
	private String fieldName;
	
	public Header(String text, String fieldName) {
		super();
		this.text = text;
		this.fieldName = fieldName;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getFieldName() {
		return fieldName;
	}

	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}
}
