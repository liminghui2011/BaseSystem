package com.ocean.lmh.base.model.entity.system;

import java.io.Serializable;
import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

/**
 * 操作员日志实体类
 * @author liminghui
 *
 */
public class UserActionLogValue implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1876740053612022164L;

	private String id;

	private String userId;

	private String actionCode;

	private String operationDesc;

	private String stringColumn1;

	private String stringColumn2;

	private String stringColumn3;

	private String stringColumn4;

	private Integer intColumn1;

	private Integer intColumn2;

	private Integer intColumn3;

	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date dateColumn1;

	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date dateColumn2;

	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date dateColumn3;

	private String inputParams;

	private String httpRespCode;

	private String httpErrorMsg;

	private String thirdRespData;

	private String respData;

	private Integer businessResultCode;

	private String exceptionInfo;

	private Long usingTime;

	private String comment;

	private Date txnDate;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getActionCode() {
		return actionCode;
	}

	public void setActionCode(String actionCode) {
		this.actionCode = actionCode;
	}

	public String getOperationDesc() {
		return operationDesc;
	}

	public void setOperationDesc(String operationDesc) {
		this.operationDesc = operationDesc;
	}

	public String getStringColumn1() {
		return stringColumn1;
	}

	public void setStringColumn1(String stringColumn1) {
		this.stringColumn1 = stringColumn1;
	}

	public String getStringColumn2() {
		return stringColumn2;
	}

	public void setStringColumn2(String stringColumn2) {
		this.stringColumn2 = stringColumn2;
	}

	public String getStringColumn3() {
		return stringColumn3;
	}

	public void setStringColumn3(String stringColumn3) {
		this.stringColumn3 = stringColumn3;
	}

	public String getStringColumn4() {
		return stringColumn4;
	}

	public void setStringColumn4(String stringColumn4) {
		this.stringColumn4 = stringColumn4;
	}

	public Integer getIntColumn1() {
		return intColumn1;
	}

	public void setIntColumn1(Integer intColumn1) {
		this.intColumn1 = intColumn1;
	}

	public Integer getIntColumn2() {
		return intColumn2;
	}

	public void setIntColumn2(Integer intColumn2) {
		this.intColumn2 = intColumn2;
	}

	public Integer getIntColumn3() {
		return intColumn3;
	}

	public void setIntColumn3(Integer intColumn3) {
		this.intColumn3 = intColumn3;
	}

	public Date getDateColumn1() {
		return dateColumn1;
	}

	public void setDateColumn1(Date dateColumn1) {
		this.dateColumn1 = dateColumn1;
	}

	public Date getDateColumn2() {
		return dateColumn2;
	}

	public void setDateColumn2(Date dateColumn2) {
		this.dateColumn2 = dateColumn2;
	}

	public Date getDateColumn3() {
		return dateColumn3;
	}

	public void setDateColumn3(Date dateColumn3) {
		this.dateColumn3 = dateColumn3;
	}

	public String getInputParams() {
		return inputParams;
	}

	public void setInputParams(String inputParams) {
		this.inputParams = inputParams;
	}

	public String getHttpRespCode() {
		return httpRespCode;
	}

	public void setHttpRespCode(String httpRespCode) {
		this.httpRespCode = httpRespCode;
	}

	public String getHttpErrorMsg() {
		return httpErrorMsg;
	}

	public void setHttpErrorMsg(String httpErrorMsg) {
		this.httpErrorMsg = httpErrorMsg;
	}

	public String getThirdRespData() {
		return thirdRespData;
	}

	public void setThirdRespData(String thirdRespData) {
		this.thirdRespData = thirdRespData;
	}

	public String getRespData() {
		return respData;
	}

	public void setRespData(String respData) {
		this.respData = respData;
	}

	public Integer getBusinessResultCode() {
		return businessResultCode;
	}

	public void setBusinessResultCode(Integer businessResultCode) {
		this.businessResultCode = businessResultCode;
	}

	public String getExceptionInfo() {
		return exceptionInfo;
	}

	public void setExceptionInfo(String exceptionInfo) {
		this.exceptionInfo = exceptionInfo;
	}

	public Long getUsingTime() {
		return usingTime;
	}

	public void setUsingTime(Long usingTime) {
		this.usingTime = usingTime;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public Date getTxnDate() {
		return txnDate;
	}

	public void setTxnDate(Date txnDate) {
		this.txnDate = txnDate;
	}

	@Override
	public String toString() {
		return "UserActionLog [id=" + id + ", userId=" + userId
				+ ", actionCode=" + actionCode + ", operationDesc="
				+ operationDesc + ", stringColumn1=" + stringColumn1
				+ ", stringColumn2=" + stringColumn2 + ", stringColumn3="
				+ stringColumn3 + ", stringColumn4=" + stringColumn4
				+ ", intColumn1=" + intColumn1 + ", intColumn2=" + intColumn2
				+ ", intColumn3=" + intColumn3 + ", dateColumn1=" + dateColumn1
				+ ", dateColumn2=" + dateColumn2 + ", dateColumn3="
				+ dateColumn3 + ", inputParams=" + inputParams
				+ ", httpRespCode=" + httpRespCode + ", httpErrorMsg="
				+ httpErrorMsg + ", thirdRespData=" + thirdRespData
				+ ", respData=" + respData + ", businessResultCode="
				+ businessResultCode + ", exceptionInfo=" + exceptionInfo
				+ ", usingTime=" + usingTime + ", comment=" + comment
				+ ", txnDate=" + txnDate + "]";
	}
}
