package com.ocean.lmh.system.controller;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.ocean.lmh.base.annotation.Log;
import com.ocean.lmh.base.annotation.MarkRequest;
import com.ocean.lmh.base.constant.AppConstant;
import com.ocean.lmh.base.constant.PageConstants;
import com.ocean.lmh.base.message.SuccessActionResult;
import com.ocean.lmh.base.model.entity.system.MenuValue;
import com.ocean.lmh.base.model.request.system.MenuReq;
import com.ocean.lmh.base.model.vo.PaginationBean;
import com.ocean.lmh.base.util.HttpUtils;
import com.ocean.lmh.base.util.JSONHelper;
import com.ocean.lmh.system.service.MenuService;

/**
 * 系统菜单管理控制类，负责对系统菜单树的各项管理，
 * 保存添加菜单树，添加菜单，查询菜单，修改菜单等
 * @author liminghui
 *
 */
@Controller
@RequestMapping ( value = "/system" )
public class MenuController {

	@Resource ( name = "menuService" )
	private MenuService		menuService;

	@Autowired
	private MessageSource	messageSource;
	
	private String	treeJSON	= "";
	
	private static Logger log = LoggerFactory.getLogger(MenuController.class);

	/**
	 * 查询查单树列表
	 * @param menuReq 条件参数实体
	 * @param model 储存操作结果集
	 * @return 跳转地址
	 */
	@RequestMapping ( value = "/menu/list" )
	@MarkRequest
	@Log ( "查看菜单树列表" )
	public String getMenuList ( @ModelAttribute ( "menu" ) MenuReq menuReq , Model model,
			HttpServletRequest request) 
	{
		PaginationBean<MenuValue> pb = menuService.menuList(menuReq);
		try 
	    {
			pb.setUrl(HttpUtils.getRequestInfo(request, true));
		}
	    catch (Exception e) 
	    {
	    	log.debug("get request url error",e);
		}
		model.addAttribute("pb", pb);
		return PageConstants.Menu.LIST;
	}

	/**
	 * 初始化新增菜单树
	 * @return 跳转地址
	 */
	@RequestMapping ( value = "/menu/addTree" , method = RequestMethod.GET )
	public String addMenuTreeForm ( @ModelAttribute ( "tree" ) MenuValue menu ) 
	{
		return PageConstants.Menu.ADD;
	}

	/**
	 * 添加菜单树
	 * 
	 * @param menu 待添加的菜单树
	 * @param result
	 * @param model 储存操作结果集
	 * @return 请求响应的信息
	 */
	@RequestMapping ( value = "/menu/addTree" , method = RequestMethod.POST )
	@Log ( "添加菜单树" )
	public ResponseEntity<String> addMenuTree ( @ModelAttribute ( "tree" ) MenuValue menu , BindingResult result , 
			Locale locale, Model model ) 
	{		
		Boolean hasError = menuService.addMenuTree(menu);
		if (hasError)
		{
			String info = messageSource.getMessage ( "menu.treename.exists" , null , locale );
			HttpHeaders headers = new HttpHeaders ( );
			headers.set (AppConstant.FILE_HEADER_TYPE,AppConstant.FILE_HEADER_CODE);
			return new ResponseEntity<String> ( info , headers , HttpStatus.BAD_REQUEST );
		}else
		{
			model.addAttribute ( "tree" , menu );
			return new ResponseEntity<String> ( String.valueOf ( menu.getId ( ) ) , HttpStatus.OK );
		}
	}

	/**
	 * 添加菜单项
	 * @param menu 菜单项实体
	 * @param locale
	 * @return 请求响应的信息
	 */
	@RequestMapping ( value = "/menu/addMenu" , method = RequestMethod.POST )
	@Log ( "添加菜单" )
	public ResponseEntity<String> addMenuItem ( MenuValue menu , Locale locale ) 
	{
		
		boolean hasError = menuService.addMenuItem(menu);
		if (hasError )
		{
			String info = messageSource.getMessage ( "menu.nodename.exists" , null , locale );
			HttpHeaders headers = new HttpHeaders ( );
			headers.set (AppConstant.FILE_HEADER_TYPE,AppConstant.FILE_HEADER_CODE);
			return new ResponseEntity<String> ( info , headers , HttpStatus.BAD_REQUEST );
		}else
		{
			return new ResponseEntity<String> ( String.valueOf ( menu.getId ( ) ) , HttpStatus.OK );
		}
	}

	/**
	 * 删除菜单项
	 * @param id 菜单项id
	 * @return 请求响应的信息
	 */
	@RequestMapping ( value = "/menu/remove" , method = RequestMethod.POST )
	@Log ( "删除菜单" )
	public ResponseEntity<String> remove ( Integer id ) 
	{
		menuService.removeMenu ( id );
		return new ResponseEntity<String> ( HttpStatus.OK );
	}

	/**
	 * 移动菜单
	 * @param id
	 * @param pid
	 * @param prevId
	 * @param nextId
	 * @return
	 */
	@RequestMapping ( value = "/menu/move" , method = RequestMethod.POST )
	@Log ( "移动菜单" )
	public ResponseEntity<String> move ( Integer id , Integer pid , Integer prevId , Integer nextId ) 
	{
		menuService.move ( id , pid , prevId , nextId );
		return new ResponseEntity<String> ( HttpStatus.OK );
	}

	/**
	 * 获取菜单信息
	 * @param id 菜单项id
	 * @return 封装有结果的map对象[id:xx,name:xx,pid:xx,uri:xx]
	 */
	@ResponseBody
	@RequestMapping ( value = "/menu/get" , produces = "application/json" )
	public Map<String, Object> getMenu ( Integer id ) 
	{
		return menuService.getMenuHasPidById ( id );
	}

	/**
	 * 更新菜单
	 * @param menu 待更新的菜单实体
	 * @return 请求响应的信息
	 */
	@ResponseBody
	@RequestMapping ( value = "/menu/updateMenu" , produces = "application/json;charset=utf-8" )
	@Log ( "修改菜单" )
	public ResponseEntity<String> updateMenu ( MenuValue menu , Locale locale )  
	{
		boolean hasError = menuService.updateMenuItem(menu);
		
		HttpHeaders headers = new HttpHeaders ( );
		if ( hasError ){
			String info = messageSource.getMessage ( "menu.nodename.exists" , null , locale );
			headers.set (AppConstant.FILE_HEADER_TYPE,AppConstant.FILE_HEADER_CODE);
			return new ResponseEntity<String> ( info , HttpStatus.BAD_REQUEST );
		}

		MenuValue paMenu = menuService.getMenuById(menu.getParent().getId());
		Map<String, Object> rsp = new HashMap<String, Object> ( );
		rsp.put ( "id" , menu.getId ( ) );
		rsp.put ( "pid" , paMenu.getId() );
		rsp.put ( "name" , menu.getName ( ) );
		rsp.put ( "uri" , menu.getUri ( ) );
		String json = JSONHelper.toJson(rsp);
		headers.set (AppConstant.FILE_HEADER_TYPE,AppConstant.FILE_HEADER_CODE);
		return new ResponseEntity<String> ( json , HttpStatus.OK );
	}

	/**
	 * 将菜单树载入到 application 中
	 * @param id 菜单树id
	 * @param session
	 */
	@RequestMapping ( value = "/menu/load/{id}" )
	@Log("加载菜单树到session中")
	public ResponseEntity<String> loadTreeToSession ( @PathVariable Integer id , HttpSession session ) 
	{
		MenuValue tree = menuService.getMenuTree ( id );
		session.getServletContext ( ).setAttribute ( tree.getName ( ) , tree );
		return new ResponseEntity<String> ( HttpStatus.OK );
	}

	/**
	 * 删除菜单树记录
	 * @param id 菜单树id
	 * @param request 请求对象
	 * @param session 
	 * @return 跳转地址
	 */
	@RequestMapping ( value = "/menu/removeTree" )
	@Log ( "删除菜单树" )
	public String removeTree ( Integer id , HttpServletRequest request , HttpSession session , RedirectAttributes ra ) 
	{
		MenuValue tree = menuService.getMenuTree ( id );
		menuService.removeMenu ( tree.getId ( ) );
		session.getServletContext ( ).removeAttribute ( tree.getName ( ) );
		ra.addFlashAttribute ( "actionResult" , new SuccessActionResult ( ) );
		return "redirect:" + HttpUtils.getMarkRequestInfo ( request , "returnURI" );
	}

	/**
	 * 更新菜单树记录信息
	 * @param id 菜单树id
	 * @param model 储存结果集的实体
	 * @return 跳转地址
	 */
	@RequestMapping ( value = "/menu/update" , method = RequestMethod.GET )
	public String updateTree ( Integer id , Model model ) 
	{
		MenuValue tree = menuService.getMenuTree ( id );
		model.addAttribute ( "tree" , tree );
		model.addAttribute ( "treeNodes" , getTreeJSON ( tree ) );
		treeJSON = "";
		return PageConstants.Menu.UPDATE;
	}

	/**
	 * 将菜单树数据转换为json形式的数据格式
	 * @param menu 菜单树实体
	 * @return json形式的菜单树数据
	 */
	public String getTreeJSON ( MenuValue menu ) 
	{
		MenuValue parent = menu.getParent ( );
		int pid = 0;
		if ( parent != null )
		{
			pid = parent.getId ( );
		}
		String uri = menu.getUri ( );
		uri = uri == null ? "" : uri;
		treeJSON += "{id:" + menu.getId ( ) + ", pId:" + pid + ", name:'" + menu.getName ( ) + "', url:'" + uri + "'},";
		if ( menu.getChildren ( ).size ( ) > 0 )
		{
			for ( MenuValue child : menu.getChildren ( ) )
			{
				getTreeJSON ( child );
			}
		}
		return "[" + treeJSON.substring ( 0 , treeJSON.length ( ) - 1 ) + "]";
	}

	/**
	 * 更新菜单树描述
	 * @param id 菜单树id
	 * @param description 新的菜单树描述信息
	 * @return 请求响应的信息
	 */
	@RequestMapping ( value = "/menu/updateDescription" , method = RequestMethod.POST )
	public ResponseEntity<String> updateDescription ( Integer id , String description ) 
	{
		menuService.updateMenuDescription ( id , description );
		return new ResponseEntity<String> ( HttpStatus.OK );
	}
}
