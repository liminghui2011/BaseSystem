package com.ocean.lmh.base.message;

import java.io.Serializable;


public class SuccessActionResult extends ActionResult implements Serializable{

	private static final long serialVersionUID = 2915048886832321323L;

	public SuccessActionResult ( String message ) {
		super ( ActionResult.SUCCESS , message );
	}

	public SuccessActionResult ( ) {
		super ( ActionResult.SUCCESS , "action.result.success" );
	}
}
