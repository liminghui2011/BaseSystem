package com.ocean.lmh.base.model.entity.system;

import com.ocean.lmh.base.model.entity.BaseValue;

/**
 * 日志详情实体类
 * @author liminghui
 */
public class ActionLogDetailValue extends BaseValue
{

    /**
	 * 
	 */
    private static final long serialVersionUID = -2355356038018521523L;

    private Integer id;

    private String content;

    public Integer getId()
    {
        return id;
    }

    public void setId(Integer id)
    {
        this.id = id;
    }

    public String getContent()
    {
        return content;
    }

    public void setContent(String content)
    {
        this.content = content;
    }
}
