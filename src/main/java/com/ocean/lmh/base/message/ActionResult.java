package com.ocean.lmh.base.message;


public class ActionResult {

	public static final String	SUCCESS	= "success";
	public static final String	ERROR		= "error";

	private String							type;
	private String							message;

	public ActionResult ( String type , String message ) {
		super ( );
		this.type = type;
		this.message = message;
	}

	public String getType ( ) {
		return type;
	}

	public void setType ( String type ) {
		this.type = type;
	}

	public String getMessage ( ) {
		return message;
	}

	public void setMessage ( String message ) {
		this.message = message;
	}

}
