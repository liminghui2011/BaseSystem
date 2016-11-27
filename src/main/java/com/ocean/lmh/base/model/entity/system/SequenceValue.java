package com.ocean.lmh.base.model.entity.system;

import java.io.Serializable;

/**
 * 系统序列实体类
 * @author liminghui
 *
 */
public class SequenceValue implements Serializable
{
	
    /**
	 * 
	 */
	private static final long serialVersionUID = 4877634030180032207L;

	private String seqName;

    private Integer currentValue;

    private Integer increment;

    public String getSeqName() {
        return seqName;
    }

    public void setSeqName(String seqName) {
        this.seqName = seqName ;
    }

    public Integer getCurrentValue() {
        return currentValue;
    }

    public void setCurrentValue(Integer currentValue) {
        this.currentValue = currentValue;
    }

    public Integer getIncrement() {
        return increment;
    }

    public void setIncrement(Integer increment) {
        this.increment = increment;
    }
}