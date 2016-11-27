package com.ocean.lmh.base.model.entity;

import java.io.Serializable;
import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

public class BaseValue implements Serializable
{
    private static final long serialVersionUID = 7794512522174826358L;
    
    //修改人
    private Integer modifyOper;
    
    //修改时间
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date modifyTime;

    public Integer getModifyOper()
    {
        return modifyOper;
    }

    public void setModifyOper(Integer modifyOper)
    {
        this.modifyOper = modifyOper;
    }

    public Date getModifyTime()
    {
        return modifyTime;
    }

    public void setModifyTime(Date modifyTime)
    {
        this.modifyTime = modifyTime;
    }

}
