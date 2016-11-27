package com.ocean.lmh.base.model.entity.system;

import com.ocean.lmh.base.model.entity.BaseValue;

/**
 * 参数详情实体类
 * @author liminghui
 */
public class ParamItemValue extends BaseValue
{

    private static final long serialVersionUID = 7371185049703582391L;

    private Integer id;

    private ParamValue param;

    private String name;

    private String value;
    
    private Integer paramId;
    
    public ParamItemValue()
    {
        super();
    }
    public ParamItemValue(Integer id)
    {
        super();
        this.id = id;
    }

   

    public Integer getId()
    {
        return id;
    }

    public void setId(Integer id)
    {
        this.id = id;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getValue()
    {
        return value;
    }

    public void setValue(String value)
    {
        this.value = value;
    }

    public ParamValue getParam()
    {
        return param;
    }

    public void setParam(ParamValue param)
    {
        this.param = param;
    }

    public Integer getParamId()
    {
        return paramId;
    }

    public void setParamId(Integer paramId)
    {
        this.paramId = paramId;
    }
    
    

}
