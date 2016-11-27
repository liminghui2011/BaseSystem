/**
* File Name : CommonCodeValue.java	
*
* Functions:
*  TODO
*
* History:
*  Date          Modified_By     Reason    Description	
*  2013-9-5       Administrator          TODO      <一句话功能简述>
*
*  Copyright (c) 2010-2015 lutongnet Corporation. All rights reserved.
*
*/

package com.ocean.lmh.base.model.entity.system;

import com.ocean.lmh.base.model.entity.BaseValue;

/**
 * 〈一句话功能简述〉
 * 〈功能详细描述〉
 * @author     [作者]（必须）
 * @see         [相关类/方法]（可选）
 * @since      [产品/模块版本] （必须）
 */

public class CommonCodeValue extends BaseValue
{

    private static final long serialVersionUID = 1L;
    
    private String codeName = null;
    
    private String prefix = null;
    
    private String middle = null;
    
    private String code = null;
    
    private Integer step = 0;
    
    private String codeLength = null;

    public String getCodeName()
    {
        return codeName;
    }

    public void setCodeName(String codeName)
    {
        this.codeName = codeName;
    }

    public String getPrefix()
    {
        return prefix;
    }

    public void setPrefix(String prefix)
    {
        this.prefix = prefix;
    }

    public String getMiddle()
    {
        return middle;
    }

    public void setMiddle(String middle)
    {
        this.middle = middle;
    }

    public String getCode()
    {
        return code;
    }

    public void setCode(String code)
    {
        this.code = code;
    }

    public Integer getStep()
    {
        return step;
    }

    public void setStep(Integer step)
    {
        this.step = step;
    }

    public String getCodeLength()
    {
        return codeLength;
    }

    public void setCodeLength(String codeLength)
    {
        this.codeLength = codeLength;
    }
    
}
