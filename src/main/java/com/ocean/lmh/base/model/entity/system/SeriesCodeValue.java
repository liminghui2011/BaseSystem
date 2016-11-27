package com.ocean.lmh.base.model.entity.system;

import com.ocean.lmh.base.model.entity.BaseValue;

/**
 * 序列编号实体类
 * @author liminghui
 *
 */
public class SeriesCodeValue extends BaseValue{
    /**
	 * 
	 */
	private static final long serialVersionUID = -7758960787535984455L;

	private String codeName;

    private String prefix;

    private String middle;

    private String code;

    private Integer step;

    private Integer codeLength;

    public String getCodeName() {
        return codeName;
    }

    public void setCodeName(String codeName) {
        this.codeName = codeName ;
    }

    public String getPrefix() {
        return prefix;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix ;
    }

    public String getMiddle() {
        return middle;
    }

    public void setMiddle(String middle) {
        this.middle = middle ;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code ;
    }

    public Integer getStep() {
        return step;
    }

    public void setStep(Integer step) {
        this.step = step;
    }

    public Integer getCodeLength() {
        return codeLength;
    }

    public void setCodeLength(Integer codeLength) {
        this.codeLength = codeLength;
    }

	@Override
	public String toString() {
		return "SeriesCodeValue [code=" + code + ", codeLength=" + codeLength
				+ ", codeName=" + codeName + ", middle=" + middle + ", prefix="
				+ prefix + ", step=" + step + "]";
	}
}