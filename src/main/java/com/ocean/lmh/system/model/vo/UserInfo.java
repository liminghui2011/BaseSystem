package com.ocean.lmh.system.model.vo;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class UserInfo implements Serializable{

	private static final long serialVersionUID = -7834620904144655517L;
	private Integer id;
	private String userid;
	private String nickname;
	private String status;
	private String province;
	private String city;
	private Date lastLoginDate;
	
	/** 用户具备的角色  */
	private String roleIds;
	private String roleName;

	/** 允许访问的资源URI */
	private List<String> allowUriList;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public List<String> getAllowUriList() {
		return allowUriList;
	}

	public void setAllowUriList(List<String> allowUriList) {
		this.allowUriList = allowUriList;
	}

	public String getRoleIds() {
		return roleIds;
	}

	public void setRoleIds(String roleIds) {
		this.roleIds = roleIds;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public Date getLastLoginDate() {
		return lastLoginDate;
	}

	public void setLastLoginDate(Date lastLoginDate) {
		this.lastLoginDate = lastLoginDate;
	}
}
