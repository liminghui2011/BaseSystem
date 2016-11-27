package com.ocean.lmh.base.model.request.system;


public class CommonReq {

	protected int	current		= 1;
	protected int	pageSize	= 10;

	public CommonReq ( ) {
	}

	public CommonReq ( int pageSize ) {
		this.pageSize = pageSize;
	}

	public CommonReq ( int current , int pageSize ) {
		this.current = current;
		this.pageSize = pageSize;
	}

	public int getCurrent ( ) {
		return current;
	}

	public void setCurrent ( int current ) {
		this.current = current;
	}

	public int getPageSize ( ) {
		return pageSize;
	}

	public void setPageSize ( int pageSize ) {
		this.pageSize = pageSize;
	}
}
