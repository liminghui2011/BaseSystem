package com.ocean.lmh.system.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ocean.lmh.base.constant.PageConstants;


@Controller
@RequestMapping ( value = "/system" )
public class InfoController {

	@RequestMapping ( value = "/info/denied" )
	public String denied (HttpServletRequest request, Model model ) {
		HttpSession session = request.getSession();
		model.addAttribute ( "info" , session.getAttribute("info"));
		return PageConstants.Base.INFO;
	}
}
