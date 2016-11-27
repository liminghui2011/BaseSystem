package com.ocean.lmh.base.model.entity.system;

/**
 * 操作员角色关联表的实体类
 * @author liminghui
 *
 */
public class UserRoleValue {
	
	private Integer roleId;
	private Integer userId;
	public Integer getRoleId() {
		return roleId;
	}
	public void setRoleId(Integer roleId) {
		this.roleId = roleId;
	}
	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	@Override
	public String toString() {
		return "RolePrivilege [userId=" + userId + ", roleId="
				+ roleId + "]";
	}
}
