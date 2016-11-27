package com.ocean.lmh.base.model.entity.system;

import com.ocean.lmh.base.model.entity.BaseValue;

/**
 * 系统状态表对应的实体类
 * @author liminghui
 *
 */
public class SystemStatusValue extends BaseValue{
	
    /**
	 * 
	 */
	private static final long serialVersionUID = 6813333016921982049L;

	private Integer statusId;

    private String statusName;

    private Integer statusLevel;

    public Integer getStatusId() {
        return statusId;
    }

    public void setStatusId(Integer statusId) {
        this.statusId = statusId;
    }

    public String getStatusName() {
        return statusName;
    }

    public void setStatusName(String statusName) {
        this.statusName = statusName;
    }

    public Integer getStatusLevel() {
        return statusLevel;
    }

    public void setStatusLevel(Integer statusLevel) {
        this.statusLevel = statusLevel;
    }
}