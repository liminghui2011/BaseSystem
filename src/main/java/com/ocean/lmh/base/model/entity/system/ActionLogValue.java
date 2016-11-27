package com.ocean.lmh.base.model.entity.system;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import com.ocean.lmh.base.model.entity.BaseValue;

/**
 * 日志实体类
 * @author [liminghui 5119]
 */
public class ActionLogValue extends BaseValue
{

    /**
	 * 
	 */
    private static final long serialVersionUID = -603867518103501502L;

    private Integer id;

    private String type;

    private String actor;

    private String content;

    private String uri;

    private String param;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date addDate;

    private Integer detailId;
    
    private ActionLogDetailValue detail;
    
    private String roleName;
    
    private String loginIp;

    public ActionLogValue()
    {
        super();
    }

    public Integer getId()
    {
        return id;
    }

    public void setId(Integer id)
    {
        this.id = id;
    }

    public String getType()
    {
        return type;
    }

    public void setType(String type)
    {
        this.type = type;
    }

    public String getContent()
    {
        return content;
    }

    public void setContent(String content)
    {
        this.content = content;
    }

    public Date getAddDate()
    {
        return addDate;
    }

    public void setAddDate(Date addDate)
    {
        this.addDate = addDate;
    }

    public Integer getDetailId()
    {
        return detailId;
    }

    public void setDetailId(Integer detailId)
    {
        this.detailId = detailId;
    }

    public String getActor()
    {
        return actor;
    }

    public void setActor(String actor)
    {
        this.actor = actor;
    }

    public String getUri()
    {
        return uri;
    }

    public void setUri(String uri)
    {
        this.uri = uri;
    }

    public String getParam()
    {
        return param;
    }

    public void setParam(String param)
    {
        this.param = param;
    }

	public ActionLogDetailValue getDetail() {
		return detail;
	}

	public void setDetail(ActionLogDetailValue detail) {
		this.detail = detail;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public String getLoginIp() {
		return loginIp;
	}

	public void setLoginIp(String loginIp) {
		this.loginIp = loginIp;
	}

	@Override
	public String toString() {
		return "ActionLogValue [actor=" + actor + ", addDate=" + addDate
				+ ", content=" + content + ", detail=" + detail + ", detailId="
				+ detailId + ", id=" + id + ", loginIp=" + loginIp + ", param="
				+ param + ", roleName=" + roleName + ", type=" + type
				+ ", uri=" + uri + "]";
	}
}
