package com.ocean.lmh.system.model.vo;

import java.io.Serializable;

public class MessageEntity implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6770519317287562928L;

	private String userName;
	private String password;
	
	public MessageEntity(String userName,String password) {
		this.userName = userName;
		this.password = password;
	}
	
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}

	@Override
	public String toString() {
		return "MessageEntity [userName=" + userName + ", password=" + password + "]";
	}
}
