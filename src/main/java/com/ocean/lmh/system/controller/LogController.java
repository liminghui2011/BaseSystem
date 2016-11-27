package com.ocean.lmh.system.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ocean.lmh.base.annotation.MarkRequest;
import com.ocean.lmh.base.constant.PageConstants;
import com.ocean.lmh.base.controller.BaseController;
import com.ocean.lmh.base.model.entity.system.ActionLogValue;
import com.ocean.lmh.base.model.request.system.LogReq;
import com.ocean.lmh.base.model.vo.PaginationBean;
import com.ocean.lmh.base.util.HttpUtils;
import com.ocean.lmh.system.service.LogService;

/**
 * 系统日志控制类，主要提供日志查询功能
 * @author liminghui
 *
 */
@Controller
@RequestMapping ( value = "/system" )
public class LogController extends BaseController{

	@Resource ( name = "logService" )
	private LogService	logService;

	private static Logger log = LoggerFactory.getLogger(LogController.class);
	
	/**
	 * 查看日志列表信息
	 * @param logReq 封装查询参数的实体
	 * @param model 储存操作结果
	 * @return 跳转地址
	 */
	@MarkRequest(scope = "")
	@RequestMapping ( value = "/log/list" )
	public String list ( @ModelAttribute ( "log" ) LogReq logReq , Model model, 
			HttpServletRequest request) {
	    PaginationBean<ActionLogValue> pb = logService.list(logReq);
	    try 
	    {
			pb.setUrl(HttpUtils.getRequestInfo(request, true));
		}
	    catch (Exception e) 
	    {
	    	log.debug("get request url error",e);
		}
        model.addAttribute("pb", pb);
        model.addAttribute("logType", getParamsFromMemcached("log_name"));
		return PageConstants.Log.LIST;
	}

	/**
	 * 查看操作日志详情
	 * @param id 日志id
	 * @return 跳转地址
	 */
	@RequestMapping ( value = "/log/{id}" , produces = "application/json" )
	@ResponseBody
	public ActionLogValue detail ( @PathVariable Integer id ) {
		return logService.get ( id );
	}
}
