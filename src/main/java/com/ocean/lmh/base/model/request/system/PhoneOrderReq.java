package com.ocean.lmh.base.model.request.system;

import java.sql.Timestamp;

public class PhoneOrderReq extends CommonReq {

	private Integer		id;
	private String		userId;
	private String		phoneNo;
	private Timestamp	orderTime;
	private String		orderStatus;

	public PhoneOrderReq ( ) {
		super ( 5 );
		orderStatus = "0";
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getPhoneNo() {
		return phoneNo;
	}

	public void setPhoneNo(String phoneNo) {
		this.phoneNo = phoneNo;
	}

	public Timestamp getOrderTime() {
		return orderTime;
	}

	public void setOrderTime(Timestamp orderTime) {
		this.orderTime = orderTime;
	}

	public String getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(String orderStatus) {
		this.orderStatus = orderStatus;
	}

	@Override
	public String toString() {
		return "PhoneOrderReq [id=" + id + ", userId=" + userId + ", phoneNo=" + phoneNo + ", orderTime=" + orderTime
				+ ", orderStatus=" + orderStatus + "]";
	}

}
