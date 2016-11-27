package com.ocean.lmh.base.model.entity.system;

import java.util.ArrayList;
import java.util.List;

import com.ocean.lmh.base.model.entity.BaseValue;


/**
 * 系统参数实体类
 * @author liminghui
 */
public class ParamValue extends BaseValue
{

    private static final long serialVersionUID = -7229534992425517423L;

    private Integer id;

    private String name;

    private int isInit;

    private List<ParamItemValue> items = new ArrayList<ParamItemValue>();

    private String description;

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

    public String getDescription()
    {
        return description;
    }

    public void setDescription(String description)
    {
        this.description = description;
    }

    public List<ParamItemValue> getItems()
    {
        return items;
    }

    public void setItems(List<ParamItemValue> items)
    {
        this.items = items;
    }

    public void addItem(ParamItemValue item)
    {
        item.setParam(this);
        items.add(item);
    }

    public int getIsInit()
    {
        return isInit;
    }

    public void setIsInit(int isInit)
    {
        this.isInit = isInit;
    }

	@Override
	public String toString() {
		return "Param [description=" + description + ", id=" + id + ", isInit="
				+ isInit + ", items=" + items.toString() + ", name=" + name + "]";
	}
    
}
