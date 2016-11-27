package com.ocean.lmh.base.model.request.system;


public class RoleReq extends CommonReq {

	private String	name;

	public RoleReq ( ) {
		super ( 5 );
	}

	public String getName ( ) {
		return name;
	}

	public void setName ( String name ) {
		this.name = name;
	}
}
