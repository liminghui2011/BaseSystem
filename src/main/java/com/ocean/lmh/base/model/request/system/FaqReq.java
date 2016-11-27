package com.ocean.lmh.base.model.request.system;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;


public class FaqReq extends CommonReq {

	private String		question;
	private String		answer;
	@DateTimeFormat ( pattern = "yyyy-MM-dd" )
	private Date	from;
	@DateTimeFormat ( pattern = "yyyy-MM-dd" )
	private Date	to;

	public String getQuestion ( ) {
		return question;
	}

	public void setQuestion ( String question ) {
		this.question = question;
	}

	public String getAnswer ( ) {
		return answer;
	}

	public void setAnswer ( String answer ) {
		this.answer = answer;
	}

	public Date getFrom ( ) {
		return from;
	}

	public void setFrom ( Date from ) {
		this.from = from;
	}

	public Date getTo ( ) {
		return to;
	}

	public void setTo ( Date to ) {
		this.to = to;
	}
}
