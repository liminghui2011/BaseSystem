package com.ocean.lmh.base.model.entity.system;

import com.ocean.lmh.base.model.entity.BaseValue;

/**
 * 角色权限关联表的实体类
 * @author liminghui
 * 
 */
public class RolePrivilegeValue extends BaseValue {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7276359532339414299L;
	private Integer roleId;
	private Integer privilegeId;
	private Integer operId;

	public Integer getRoleId() {
		return roleId;
	}

	public void setRoleId(Integer roleId) {
		this.roleId = roleId;
	}

	public Integer getPrivilegeId() {
		return privilegeId;
	}

	public void setPrivilegeId(Integer privilegeId) {
		this.privilegeId = privilegeId;
	}

	public Integer getOperId() {
		return operId;
	}

	public void setOperId(Integer operId) {
		this.operId = operId;
	}

	@Override
	public String toString() {
		return "RolePrivilege [operId=" + operId + ", privilegeId="
				+ privilegeId + ", roleId=" + roleId + "]";
	}

}
