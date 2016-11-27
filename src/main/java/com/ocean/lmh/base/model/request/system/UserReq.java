package com.ocean.lmh.base.model.request.system;


public class UserReq extends CommonReq {

	private String	name;
	private String	status;
	private String	oldPassword;
	private String	newPassword;
	private String	newPassword2;
	private String	userid;
	private Integer	id;
	

	public UserReq ( ) {
		super ( 5 );
	}

	public String getName ( ) {
		return name;
	}

	public void setName ( String name ) {
		this.name = name;
	}

	public String getStatus ( ) {
		return status;
	}

	public void setStatus ( String status ) {
		this.status = status;
	}

	public String getOldPassword ( ) {
		return oldPassword;
	}

	public void setOldPassword ( String oldPassword ) {
		this.oldPassword = oldPassword;
	}

	public String getNewPassword ( ) {
		return newPassword;
	}

	public void setNewPassword ( String newPassword ) {
		this.newPassword = newPassword;
	}

	public String getNewPassword2 ( ) {
		return newPassword2;
	}

	public void setNewPassword2 ( String newPassword2 ) {
		this.newPassword2 = newPassword2;
	}

	public String getUserid ( ) {
		return userid;
	}

	public void setUserid ( String userid ) {
		this.userid = userid;
	}

	public Integer getId ( ) {
		return id;
	}

	public void setId ( Integer id ) {
		this.id = id;
	}


}
