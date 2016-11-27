package com.ocean.lmh.base.model.request.system;


public class ParamReq extends CommonReq {

	private String	name;
	private String	description;

	public String getName ( ) {
		return name;
	}

	public void setName ( String name ) {
		this.name = name;
	}

	public String getDescription ( ) {
		return description;
	}

	public void setDescription ( String description ) {
		this.description = description;
	}
}
