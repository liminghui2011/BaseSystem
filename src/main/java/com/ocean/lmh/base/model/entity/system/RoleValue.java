package com.ocean.lmh.base.model.entity.system;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 系统角色实体类
 * @author liminghui
 *
 */
public class RoleValue implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6174510692221873060L;

	private Integer id;

	private String name;

	private String description;

	private List<UserValue> users = new ArrayList<UserValue>();

	private List<PrivilegeValue> privileges = new ArrayList<PrivilegeValue>();

	private List<Integer> assignedPrivilegeIds = new ArrayList<Integer>();
	
	private List<Integer> operPrivilege = new ArrayList<Integer>();

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<UserValue> getUsers() {
		return users;
	}

	public void setUsers(List<UserValue> users) {
		this.users = users;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public List<PrivilegeValue> getPrivileges() {
		return privileges;
	}

	public void setPrivileges(List<PrivilegeValue> privileges) {
		this.privileges = privileges;
	}

	public List<Integer> getAssignedPrivilegeIds() {
		return assignedPrivilegeIds;
	}

	public void setAssignedPrivilegeIds(List<Integer> assignedPrivilegeIds) {
		this.assignedPrivilegeIds = assignedPrivilegeIds;
	}

	public List<Integer> getOperPrivilege() {
		return operPrivilege;
	}

	public void setOperPrivilege(List<Integer> operPrivilege) {
		this.operPrivilege = operPrivilege;
	}

	@Override
	public String toString() {
		return "Role [id=" + id + ", name=" + name + ", description="
				+ description + ", users=" + users + ", privileges="
				+ privileges + ", assignedPrivilegeIds=" + assignedPrivilegeIds
				+ "]";
	}

}
