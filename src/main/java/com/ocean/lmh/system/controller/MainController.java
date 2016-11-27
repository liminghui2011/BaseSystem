package com.ocean.lmh.system.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ocean.lmh.base.constant.PageConstants;


@Controller
public class MainController {
	
	

	@RequestMapping ( "/system/main" )
	public String defaultHandler ( ) {
		
		return PageConstants.Base.MAIN;
	}
}
