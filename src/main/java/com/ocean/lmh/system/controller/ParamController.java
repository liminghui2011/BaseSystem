package com.ocean.lmh.system.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.ocean.lmh.base.annotation.Log;
import com.ocean.lmh.base.annotation.MarkRequest;
import com.ocean.lmh.base.constant.PageConstants;
import com.ocean.lmh.base.message.SuccessActionResult;
import com.ocean.lmh.base.model.entity.system.ParamItemValue;
import com.ocean.lmh.base.model.entity.system.ParamValue;
import com.ocean.lmh.base.model.request.system.ParamReq;
import com.ocean.lmh.base.model.vo.PaginationBean;
import com.ocean.lmh.base.util.HttpUtils;
import com.ocean.lmh.system.service.ParamService;

/**
 * 系统参数管理控制类，负责对页面中所有与参数相关的操作进行响应
 * 并调用相关的业务方法，然后根据操作的结果分发不同的结果页面。
 * @author liminghui
 */
@Controller
@RequestMapping ( value = "/system" )
public class ParamController {

	@Resource(name = "paramService")
	private ParamService paramService;
	
	private static Logger log = LoggerFactory.getLogger(ParamController.class);

	/**
	 * 根据条件分页获取参数列表
	 * @param paramReq 封装查询属性的实体
	 * @param model 储存结果集的实体
	 * @return 跳转地址
	 */
	@RequestMapping(value = "/param/list")
	@MarkRequest
	@Log("查询参数")
	public String list(@ModelAttribute("PARAM") ParamReq paramReq, Model model,
			HttpServletRequest request) {
		PaginationBean<ParamValue> pb = paramService.getParamList(paramReq);
		try 
	    {
			pb.setUrl(HttpUtils.getRequestInfo(request, true));
		}
	    catch (Exception e) 
	    {
	    	log.debug("get request url error",e);
		}
		model.addAttribute("pb", pb);
		return PageConstants.Param.LIST;
	}

	/**
	 * 查看系统参数详情
	 * @param id 参数id
	 * @param model 储存结果的实体
	 * @return 跳转语句
	 */
	@RequestMapping(value = "/param/view")
	@MarkRequest
	@Log("查看参数详情")
	public String view(@RequestParam Integer id, Model model) {
		ParamValue param = paramService.getParamById(id);
		model.addAttribute("PARAM", param);
		return PageConstants.Param.VIEW;
	}

	/**
	 * 初始化新增表单
	 * @param param 参数实体对象
	 * @return 跳转语句
	 */
	@RequestMapping(value = "/param/add", method = RequestMethod.GET)
	public String addForm(Model model) {
		ParamValue param = new ParamValue();
		ParamItemValue item = new ParamItemValue();
		param.addItem(item);
		model.addAttribute("PARAM", param);
		return PageConstants.Param.ADD;
	}

	/**
	 * 新增系统参数记录
	 * @param param 待添加的参数实体
	 * @param result 储存错误信息的实体
	 * @param model 储存结果的实体
	 * @param request 请求对象
	 * @return 跳转地址
	 */
	@RequestMapping(value = "/param/add", method = RequestMethod.POST)
	@Log("新增参数")
	public String add(@Valid @ModelAttribute("PARAM") ParamValue param,
			BindingResult result, RedirectAttributes ra, Model model,
			HttpServletRequest request) {

		if (result.hasErrors()) {
			return PageConstants.Param.ADD;
		}

		paramService.add(param, result, request);
		if (result.hasErrors()) {
			return PageConstants.Param.ADD;
		}

		ra.addFlashAttribute("actionResult", new SuccessActionResult());

		return "redirect:"+ HttpUtils.getMarkRequestInfo(request, "returnURI",	PageConstants.Param.LIST);
	}

	/**
	 * 初始化修改表单
	 * @param param 参数实体
	 * @return 跳转地址
	 */
	@RequestMapping(value = "/param/update", method = RequestMethod.GET)
	public String update(@RequestParam Integer id, Model model) {
		ParamValue param = paramService.getParamById(id);
		model.addAttribute("PARAM", param);
		return PageConstants.Param.UPDATE;
	}

	/**
	 * 修改系统参数信息
	 * @param param 待修改的参数实体
	 * @param result 储存错误信息的实体
	 * @param model 储存结果的实体
	 * @param request 请求对象
	 * @return 跳转地址
	 */
	@RequestMapping(value = "/param/update", method = RequestMethod.POST)
	@Log("修改参数")
	public String update(@Valid @ModelAttribute("PARAM") ParamValue param,
			BindingResult result, RedirectAttributes ra, Model model,
			HttpServletRequest request) {

		if (result.hasErrors()) {
			return PageConstants.Param.UPDATE;
		}
		
		paramService.update(param, result, request);
		
		if (result.hasErrors()) {
			return PageConstants.Param.UPDATE;
		}
		ra.addFlashAttribute("actionResult", new SuccessActionResult());

		return "redirect:" + HttpUtils.getMarkRequestInfo(request, "returnURI", PageConstants.Param.LIST);
	}

	/**
	 * 删除系统参数
	 * @param id 参数id
	 * @return 跳转地址
	 */
	@RequestMapping(value = "/param/remove", method = RequestMethod.GET)
	@Log("删除参数")
	public String remove(@RequestParam Integer id, RedirectAttributes ra,
			HttpServletRequest request) {
		paramService.remove(id, request);
		ra.addFlashAttribute("actionResult", new SuccessActionResult());
		return "redirect:" + HttpUtils.getMarkRequestInfo(request, "returnURI", PageConstants.Param.LIST);
	}
	
	/**
	 * 删除系统参数的子参数记录信息
	 * @param pid 父参数的id
	 * @param id 子参数的id
	 * @return 跳转地址
	 */
    @RequestMapping(value = "/param/removeParamItem")
    public String removeParamItem(@RequestParam Integer pid,Integer id) {
        this.paramService.removeParamItem(pid);
        return null;
    }
	
	
}
