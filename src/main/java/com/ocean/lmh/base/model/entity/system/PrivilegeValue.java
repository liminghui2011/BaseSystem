package com.ocean.lmh.base.model.entity.system;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.ocean.lmh.base.model.entity.BaseValue;

/**
 * 系统菜单权限实体类
 * @author liminghui
 *
 */
public class PrivilegeValue extends BaseValue {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2654804727635545710L;

	private Integer id;

	private String name;

	private String groupName;

	private String uri;
		
	private String description;

	private Set<RoleValue> roles = new HashSet<RoleValue>();
	
	private List<OperPrivilegeValue> operPrivilege = new ArrayList<OperPrivilegeValue>();

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

	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	public Set<RoleValue> getRoles() {
		return roles;
	}

	public void setRoles(Set<RoleValue> roles) {
		this.roles = roles;
	}

	public String getUri() {
		return uri;
	}

	public void setUri(String uri) {
		this.uri = uri;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public List<OperPrivilegeValue> getOperPrivilege() {
		return operPrivilege;
	}

	public void setOperPrivilege(List<OperPrivilegeValue> operPrivilege) {
		this.operPrivilege = operPrivilege;
	}

	@Override
	public String toString() {
		return "Privilege [description=" + description + ", groupName="
				+ groupName + ", id=" + id + ", name="
				+ name + ", roles=" + roles + ", uri=" + uri + "]";
	}
	
}
