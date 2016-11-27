package com.ocean.lmh.system.controller;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.ocean.lmh.base.constant.PageConstants;


@Controller
@RequestMapping ( value = "/system" )
public class IndexController {

	@RequestMapping ( value = "/" , method = RequestMethod.GET )
	public String index ( String mid , HttpSession session ) {
		session.setAttribute ( "mid" , mid );
		return PageConstants.Base.INDEX;
	}
}
