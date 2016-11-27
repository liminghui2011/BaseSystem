package com.ocean.lmh.base.model.entity.system;

import java.util.ArrayList;
import java.util.List;

import com.ocean.lmh.base.model.entity.BaseValue;

/**
 * 系统菜单实体类
 * @author liminghui
 *	
 */
public class MenuValue extends BaseValue {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1479738306169531185L;

	private Integer id;

	private Integer pid;

	private String name;

	private String uri;

	private Integer position;

	private String description;

	private MenuValue parent;
	
	private List<MenuValue> children = new ArrayList<MenuValue>();
	
	// 标识字段，当用户拥用该菜单的链接地址是值为true;
	private boolean enable;

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

	public MenuValue getParent() {
		return parent;
	}

	public void setParent(MenuValue parent) {
		this.parent = parent;
	}

	public String getUri() {
		return uri;
	}

	public void setUri(String uri) {
		this.uri = uri;
	}

	public List<MenuValue> getChildren() {
		return children;
	}

	public void setChildren(List<MenuValue> children) {
		this.children = children;
	}

	public Integer getPosition() {
		return position;
	}

	public void setPosition(Integer position) {
		this.position = position;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public boolean isEnable() {
		return enable;
	}

	public void setEnable(boolean enable) {
		this.enable = enable;
	}

	public Integer getPid() {
		return pid;
	}

	public void setPid(Integer pid) {
		this.pid = pid;
	}

	@Override
	public String toString() {
		return "Menu [id=" + id + ", children=" + children + ", name=" + name
				+ ", uri=" + uri + ", position="
				+ position + ", description=" + description + ", enable="
				+ enable + "]";
	}

}
