package com.ocean.lmh.system.model.vo;

import java.io.Serializable;


public class LogConfigParamValue implements Serializable{

	private static final long serialVersionUID = 1276811740822885547L;
	private String name;
	private String column;
	private Integer maxLength;
	
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Integer getMaxLength() {
		return maxLength;
	}
	public void setMaxLength(Integer maxLength) {
		this.maxLength = maxLength;
	}
	public String getColumn() {
		return column;
	}
	public void setColumn(String column) {
		this.column = column;
	}
	
	@Override
	public String toString() {
		return "LogConfigParamValue [name=" + name + ", column=" + column
				+ ", maxLength=" + maxLength + "]";
	}
	
}
