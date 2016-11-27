package com.ocean.lmh.base.util;


public class HttpClientResponse {
	private int status;
	private String body;
	
	public HttpClientResponse(int status, String body) {
		this.status = status;
		this.body = body;
	}
	
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public String getBody() {
		return body;
	}
	public void setBody(String body) {
		this.body = body;
	}
	
	@Override
	public String toString() {
		return "HttpResponse [status=" + status + ", body=" + body + "]";
	}
	
	
}

