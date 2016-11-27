package com.ocean.lmh.base.model.entity.system;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;

/**
 * 系统操作员实体类
 * @author liminghui
 *
 */
public class UserValue implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4210915695863772093L;

	private Integer id;

	private String userid;

	private String password;

	private String nickname;
	
	private String city;

	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date lastLoginDate;

	private String status;

	private List<RoleValue> roles = new ArrayList<RoleValue>();
	
	/** 判断是否进行重置密码 */
	private String isreset;
	
	private String remark;
	
	public String getIsreset() {
		return isreset;
	}

	public void setIsreset(String isreset) {
		this.isreset = isreset;
	}

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

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public Date getLastLoginDate() {
		return lastLoginDate;
	}

	public void setLastLoginDate(Date lastLoginDate) {
		this.lastLoginDate = lastLoginDate;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public List<RoleValue> getRoles() {
		return roles;
	}

	public void setRoles(List<RoleValue> roles) {
		this.roles = roles;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", userid=" + userid + ", password="
				+ password + ", nickname=" + nickname + ", city=" + city + ", lastLoginDate=" + lastLoginDate + ", status=" + status
				+ ", roles=" + roles + "]";
	}

}
