package com.ocean.lmh.base.model.entity.system;

import com.ocean.lmh.base.model.entity.BaseValue;

/**
 * 系统状态子表对应的实体类
 * @author liminghui
 *
 */
public class SystemStatusItemValue extends BaseValue{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -7752242553855360401L;

	private Integer statusId;

	private Integer parentStatusid;

	public Integer getStatusId() {
		return statusId;
	}

	public void setStatusId(Integer statusId) {
		this.statusId = statusId;
	}

	public Integer getParentStatusid() {
		return parentStatusid;
	}

	public void setParentStatusid(Integer parentStatusid) {
		this.parentStatusid = parentStatusid;
	}

	@Override
	public String toString() {
		return "SystemStatusItemValue [parentStatusid=" + parentStatusid
				+ ", statusId=" + statusId + "]";
	}

	
}