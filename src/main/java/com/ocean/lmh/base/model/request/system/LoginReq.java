package com.ocean.lmh.base.model.request.system;



public class LoginReq {

	private String	userid;

	private String	password;

	private String	verifyCode;

	// 强行登录,挤掉已经登录的相同账号
	private boolean	forceLogin;

	public String getUserid ( ) {
		return userid;
	}

	public void setUserid ( String userid ) {
		this.userid = userid;
	}

	public String getPassword ( ) {
		return password;
	}

	public void setPassword ( String password ) {
		this.password = password;
	}

	public String getVerifyCode ( ) {
		return verifyCode;
	}

	public void setVerifyCode ( String verifyCode ) {
		this.verifyCode = verifyCode;
	}

	public boolean isForceLogin ( ) {
		return forceLogin;
	}

	public void setForceLogin ( boolean forceLogin ) {
		this.forceLogin = forceLogin;
	}
}
