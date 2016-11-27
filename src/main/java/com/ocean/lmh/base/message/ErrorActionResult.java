package com.ocean.lmh.base.message;

public class ErrorActionResult extends ActionResult {

	public ErrorActionResult ( String message ) {
		super ( ActionResult.ERROR , message );
	}

	public ErrorActionResult ( ) {
		super ( ActionResult.ERROR , "action.result.error" );
	}
}
