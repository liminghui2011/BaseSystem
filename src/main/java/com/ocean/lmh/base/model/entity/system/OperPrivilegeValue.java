package com.ocean.lmh.base.model.entity.system;

import com.ocean.lmh.base.model.entity.BaseValue;

/**
 * 系统操作权限实体类
 * @author liminghui
 *
 */
public class OperPrivilegeValue extends BaseValue{
    /**
	 * 
	 */
	private static final long serialVersionUID = 7976433646626207794L;

	private Integer opId;

    private Integer privilegeId;

    private String operName;

    private String operDescription;
    
    private String uri;
    
    private Integer operLevel;

    public Integer getOpId() {
        return opId;
    }

    public void setOpId(Integer opId) {
        this.opId = opId;
    }

    public Integer getPrivilegeId() {
        return privilegeId;
    }

    public void setPrivilegeId(Integer privilegeId) {
        this.privilegeId = privilegeId;
    }

    public String getOperName() {
        return operName;
    }

    public void setOperName(String operName) {
        this.operName = operName;
    }

    public String getOperDescription() {
        return operDescription;
    }

    public void setOperDescription(String operDescription) {
        this.operDescription = operDescription;
    }

	public String getUri() {
		return uri;
	}

	public void setUri(String uri) {
		this.uri = uri;
	}

	public Integer getOperLevel() {
		return operLevel;
	}

	public void setOperLevel(Integer operLevel) {
		this.operLevel = operLevel;
	}

	@Override
	public String toString() {
		return "OperPrivilegeValue [opId=" + opId + ", operDescription="
				+ operDescription + ", operLevel=" + operLevel + ", operName="
				+ operName + ", privilegeId=" + privilegeId + ", uri=" + uri
				+ "]";
	}
	
    
}