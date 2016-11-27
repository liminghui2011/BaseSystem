<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="com.ocean.lmh.base.constant.AppConstant"%>
<%@page import="com.ocean.lmh.base.model.entity.system.MenuValue"%>
<%@page import="java.util.List"%>
<%@page isELIgnored="false"%>
<%@page import="com.ocean.lmh.system.model.vo.UserInfo"%>
<%@page import="com.ocean.lmh.base.util.StringUtils"%>
<%@page import="com.ocean.lmh.base.util.DateUtils"%>
<%@page import="com.itextpdf.text.log.SysoLogger"%>
<%@ include file="../common/common_tags.jsp" %>

<!DOCTYPE html>
<html lang="en">
<head id="myHead">
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	
	<title>
		路通内部管理平台
	</title>
	<link rel="stylesheet" type="text/css" href="<w:path/>resources/css/bootstrap.css">
	<link rel="stylesheet" type="text/css" href="<w:path/>resources/css/zzsc.css">
	<link rel="stylesheet" type="text/css" href="<w:path/>resources/css/163css.css">
	<script type="text/javascript" src="<w:path/>resources/js/jquery-1.8.3.js"></script>
	<link rel="stylesheet" type="text/css" href="<w:path/>resources/css/bootstrap-responsive.min.css">
	<link rel="stylesheet" type="text/css" href="<w:path/>resources/css/bootstrap-tagsinput.css">
	<link id="skinUri" rel="stylesheet" type="text/css" href="<w:path/>resources/css/${ cookie['skin'] != null ? cookie['skin'].value:'main'}.css">
	<%-- <link rel="stylesheet" type="text/css" href="<w:path/>resources/css/tip-skyblue/tip-skyblue.css"> --%>
    
    <link rel="stylesheet" type="text/css" href="<w:path/>resources/css/buttonstyle.css" />
    <link rel="stylesheet" href="<w:path/>resources/css/zTreeStyle/zTreeStyle.css" type="text/css"></link>
    <link rel="stylesheet" href="<w:path/>resources/css/fanycbox/jquery.fancybox.css" type="text/css"></link>
	<script type="text/javascript" src="<w:path/>resources/js/bootstrap.min.js"></script>
	<script type="text/javascript" src="<w:path/>resources/js/bootstrap-tagsinput.js"></script>
    <link rel="stylesheet"	href="<w:path/>resources/css/jquery-ui.css">
    <link rel="stylesheet"	href="<w:path/>resources/css/jquery.ui.accordion.css">
    <script type="text/javascript" src="<w:path/>resources/js/jquery-ui.js"></script>
    
    
    <script type="text/javascript" src="<w:path/>resources/js/jquery.ztree.core-3.4.min.js"></script>
    <script type="text/javascript" src="${path}/resources/js/jquery.ztree.exedit-3.4.min.js"></script>
	<script type="text/javascript" src="<w:path/>resources/My97DatePicker/WdatePicker.js"></script>
	<script type="text/javascript" src="<w:path/>resources/js/pagination.js"></script>
	<script type="text/javascript" src="<w:path/>resources/js/jquery.validate.min.js"></script>
	<script type="text/javascript" src="<w:path/>resources/js/messages_cn.js"></script>
	<script type="text/javascript" src="<w:path/>resources/js/jquery.metadata.js"></script>
	<script type="text/javascript" src="<w:path/>resources/js/jquery.cookie.js"></script>
	<script type="text/javascript" src="<w:path/>resources/js/comman/base.js"></script>
	<script type="text/javascript" src="<w:path/>resources/js/jquery.fancybox.js"></script>
	<script type="text/javascript" src="<w:path/>resources/js/zzsc.js"></script>
	<script type="text/javascript" src="<w:path/>resources/js/jquery.tipsy.js"></script>
	
	
    <%--<script type="text/javascript" src="<w:path/>uploadify/jquery.uploadify.min.js"></script> --%>
	<!--[if lt IE 9]>
    	<script type="text/javascript" src="<w:path/>resources/js/html5shiv.js"></script>
    <![endif]-->
	<style type="text/css">
		
	    #mymenus ul {
		    list-style: none;
		    padding: 0 0 0 0;
		    margin-top: -9px;
		    margin-bottom: -5px;
		}
		
		#mymenus ul li {
		    font-weight: normal;
		    cursor: auto;
		    background-color: #F1F9FC;
		    /*padding: 10px 0 0 0;*/
		    line-height: 42px;
		}
		
		#mymenus a {
		    text-decoration: none;
		}
		
		#mymenus a:hover {
		    text-decoration: none;
		    color: #0131fe;
		}
		#addAccordion{margin-left: 10px;margin-right: 10px;}
		.table tr{border: 1px solid #D5D5D5;}
		.accordion-group{border-bottom: none;
			border-left: none;
		}
		.inner_add_icon{
			height:16px;
			width:16px;
			float:left;
			margin-top:3px;
			display:inline-block;
			background: url("${path}/resources/img/add.png") no-repeat;
		}
	</style>
	
<style type="text/css">
#preview{width:100px;height:100px;border:0px solid #000;overflow:hidden;}
#imghead {filter:progid:DXImageTransform.Microsoft.AlphaImageLoader(sizingMethod=image);}
</style>
<link rel="stylesheet" type="text/css" href="<w:path/>resources/css/flat-button.css" />
<script type="text/javascript">
$(function(){
	 if ('${not empty actionResult}' ){
		if('${actionResult.type}'=='success'){
			$("#msgModel").modal();
		}
		else if('${actionResult.type}'=='error'){
			$("#msgModel_error").modal();	
		}
	} 
	
});
</script>
</head>

<body style="margin: 0px;padding: 0px; ">
<%@ include file="../common/messageModal.jsp" %>
	<!-- 确定弹出框 -->
<div id="system_out_sure" style="position:absolute;top:0px;left:0px;z-index: 10000">
	<div class="system_out_top">
		<div>提示框</div>
		<div class="delete" onclick="cancle('#system_out_sure')"></div>
	</div>
	<div class="system_out_center">
		<div id="sure_font" style="position:absolute;top:60px;left:80px;color:#9A9A9A;letter-spacing:1px;">
			
		</div>
	</div>
	<div class="system_out_bottom">
		<input type="hidden" id="outtype" name="outtype">
		<div class="sure_btn" onclick="logout()"></div>
		<div class="cancle" onclick="cancle('#system_out_sure')" ></div>
	</div>
</div>
	<script type="text/javascript">
    	var main_h = $(document).height()/2;
		var main_w = $(document).width()/3;
		$('#system_out_sure').css('left',main_w+'px');
		$('#system_out_sure').css('top',main_h-100+'px');
    </script>
<div class="wrapper">	
	<%-- <!-- 头部信息 -->
	<div id="main_head" style="height: 90px;width: 100%;position: relative;min-width: 1000px;" >
		<!-- 头部背景图片 -->
		<img alt="" src="<w:path/>resources/img/top_bg2.png" style="position:absolute;width: 100%;height: 100%;z-index: -1;"> 
		<img alt="" src="<w:path/>resources/img/logo.png" style="position:absolute;margin-left: 28px;margin-top: 20px;">
		
		<%
			UserInfo userInfo = (UserInfo)request.getSession().getAttribute(AppConstant.USER_INFO);
			String lastLoginStr = "首次登录";
			if(userInfo.getLastLoginDate()!=null)
			{
				lastLoginStr = DateUtils.format("yyyy年MM月dd日",userInfo.getLastLoginDate());
			}
		%>
		
		<!-- 头部登陆者信息 -->
		<div style="margin-top: 62px;height:22px;position: absolute;border: 0px solid red;width: 100%;" align="right">
			<font color="black" style="padding-right: 10px;">欢迎您：<%=userInfo.getUserid()%> 【<%=userInfo.getRoleName() %>】 上次登陆时间：<%=lastLoginStr %></font>
			<a href="<w:path/>system/user/change_self_password.do" style="text-decoration: none;padding-right: 10px;">修改密码</a>
			<a href="#" id="cps_logout" onclick="showSure('1');" style="text-decoration: none;padding-right: 10px;">注销</a>
			<a href="#" id="cps_eixt" onclick="showSure('0');" style="text-decoration: none;padding-right: 20px;">退出</a>
		</div>
		
	</div>
	 --%>
	
	<%
					UserInfo userInfo = (UserInfo)request.getSession().getAttribute(AppConstant.USER_INFO);
					String lastLoginStr = "首次登录";
					if(userInfo.getLastLoginDate()!=null)
					{
						lastLoginStr = DateUtils.format("yyyy年MM月dd日",userInfo.getLastLoginDate());
					}
%>	
	
<img alt="" src="<w:path/>resources/img/top_logo.png" style="position:absolute;left: 28px;top: 20px;">

<table width="100%" height="100%" border="0" style="word-wrap:break-word;border-collapse: collapse; border-spacing: inherit;"  >
	 <tr >
		<td colspan="3"  class="top_bg2_new">
		<!-- 头部登陆者信息 -->
		  <div style="margin-top:55px;float:right;padding-bottom: 8px;">
			
			<ul class="nav" id="top_banner_text">
			  <li><a class="text_white" style="padding-right: 20px;">欢迎您：<%=userInfo.getNickname()%> 【<%=userInfo.getRoleName() %>】 上次登陆时间：<%=lastLoginStr %></a></li>
			  <li class="dropdown">
			    <a class="dropdown-toggle text_white"
			       data-toggle="dropdown"
			       href="#">
			      		  换肤
			        <b class="caret"></b>
			      </a>
			    <ul class="dropdown-menu">
			      <li style="background-color: #394B61;"><a href="#" class="btnSkin" rel="main">深蓝色</a></li>
                  <li style="background-color: #266DAF;"><a href="#" class="btnSkin" rel="blue_new">蓝色</a></li>
                  <li style="background-color: #379934;"><a href="#" class="btnSkin" rel="green">青色</a></li>
			    </ul>
			  </li>
			  <li><a class="text_white" href="<w:path/>system/user/updateMyInfoUI.do" style="text-decoration: none;padding-right: 5px;">我的信息</a></li>
			  <li><a class="text_white" href="<w:path/>system/user/change_self_password.do" style="text-decoration: none;padding-right: 5px;">修改密码</a></li>
			  <li><a class="text_white" href="#" id="cps_logout" onclick="showSure('1');" style="text-decoration: none;padding-right: 5px;">注销</a></li>
			  <li><a class="text_white" href="#" id="cps_eixt" onclick="showSure('0');" style="text-decoration: none;">退出</a></li>
			</ul>
			
		  </div>
		</td>
	</tr>
	
	<!-- 左边菜单栏 -->
	<tr>
	<td id="lef" class="left_bg" valign="top" width="200px;" >
		
	<div id="outside" style="width: 200px;border: 0px solid red;" class="left_bg">
		<div class="container-fluid" style="padding-left: 0;">
			<div class="row-fluid" style="padding-left: 0;">
		<div id="menu-right" class="" style="padding-left: 0;">
			<div id="mymenus" class="" style="width: 200px; border: 0px solid red;padding-left: 0;font-size: 18px;">
			
				<%
					String mid1 = (String)session.getAttribute("mid1");
					String mid2 = (String)session.getAttribute("mid2");
					String mid3 = (String)session.getAttribute("mid3");
					
					MenuValue menuValue = (MenuValue)session.getAttribute(AppConstant.USER_MENU_TREE);
				if(null == menuValue)
				{
				%>
					请重新登录！
				<%
				    return;
				}
				
				List<MenuValue> menu1Listq = menuValue.getChildren();
				int actid = 0;
				String iconStr = "icon-folder-close";
				for(MenuValue menu1: menu1Listq){
					if(!menu1.isEnable())
					{
						continue;
					}
				%>
				<h3 >
					<%=menu1.getName() %>
				</h3>
				<div style="border: 0px solid black;background: #F1F9FC;">
				<ul class="topnav nav nav-list nav<%=menu1.getId() %> " >
				<%	
					for(MenuValue menu2: menu1.getChildren()){
					if(menu2.isEnable()){
						
						String uri2 = menu2.getUri();
						uri2 = uri2==null?"":uri2;
						String target2 = "_self";
						
						if(uri2.startsWith("http://") || uri2.startsWith("https://")){
							target2 = "_blank";
						}else{
							if(uri2.indexOf("?")>0){
								uri2 = application.getContextPath() + "/" + uri2 + "&mid1="+menu1.getId()+"&mid2="+menu2.getId()+"&act_menu="+actid;
							}else{
								uri2 = application.getContextPath() + "/" + uri2 + "?mid1="+menu1.getId()+"&mid2="+menu2.getId()+"&act_menu="+actid;
							}
						}
						
						if(menu2.getChildren().size()==0){
				%>
					<li>
						<a data-title="<%=menu2.getName() %>" rel="shortmenu" href="<%=uri2%>" target="<%=target2%>" 
	
	<%=menu2.getId().toString().equals(mid2)?"class=\"current\"":"" %>>
							<%--
							<i class="icon-th-large<%=menu2.getId().toString().equals(mid2)?" icon-white":"" 
				
								%>"></i>
							 --%>
							 <span style="background:url(${path}/resources/img/menu_dot.png) no-repeat;width:5px;height:5px;display:inline-block;padding-top:4px;"></span>
							<span style="font-size: 16px;"><%=menu2.getName() %></span>
						</a>
					</li>
				<%			
						}else{
				%>
					<li class="myaccordion-menu">
						<a href="#myaccordion<%=menu2.getId() %>"  data-toggle="collapse">
							<i class="icon-th-large"></i>
							<span><%=menu2.getName() %></span>
						</a>
						<div id="myaccordion<%=menu2.getId() %>" >
							<div class="myaccordion-inner">
								<ul class="nav nav-list">
				<%
									int i = 0;
									for(MenuValue menu3: menu2.getChildren()){
										String uri3 = menu3.getUri();
										uri3 = uri3==null?"":uri3;
										String target3 = "_self";
										if(uri3.startsWith("http://") || uri3.startsWith
	
	("https://")){
											target3 = "_blank";
										}else{
											if(uri3.indexOf("?")>0){
												uri3 = application.getContextPath() + "/" + 
	
	uri3 + "&mid1q="+menu1.getId()+"&mid2q="+menu2.getId()+"&mid3q="+menu3.getId();
											}else{
												uri3 = application.getContextPath() + "/" + 
	
	uri3 + "?mid1q="+menu1.getId()+"&mid2q="+menu2.getId()+"&mid3q="+menu3.getId();
											}
										}
				%>
										<li>
											<a href="<%=uri3 %>" target="<%=target3 %>" 
												class="<%=menu3.getId().toString().equals
	
	(mid3)?"current":"" %> <%=(i++)==0?"first":"" %>">
												<%=menu3.getName() %>
											</a>
										</li>
				<%									
									}					
				%>
								</ul>
							</div>
						</div>
					</li>
				<%			
						}
					}
				}
				%>
				</ul></div>
				<%
					actid++;
					}
				%>		
				</div>
				<div style="height: 20px;"></div>
				
		</div>		
			</div>
		</div>
		
		<script type="text/javascript">
			$(document).ready(function(){
				var h = $(window).height();
				$('#lef').height(h-101);
			
				$('.menu1').hover(function(){
					var $this = $(this);
					$('.menu1').removeClass('active');;
					$this.addClass('active');
					$('.topnav').addClass('hide');
					$('.nav'+$this.attr('rel')).removeClass('hide');
				});
				
				///显示菜单选中
				/**/$(".nav>li>a").each(function()
				{
					if(window.location.href.indexOf($(this).attr("href")) > 0)
					{
						$(this).parent("li").attr("class","menuAactive");
						$(this).css("color","#FFF");
						$(this).find("span:first").css({
							"background":"url("+getRootPath()+"/resources/img/menu_dot_white.png) no-repeat"
						});
						return;
					}
				});
				
				
				//换肤
				$('.btnSkin').click(function(){
					var skin = $(this).attr('rel');
					
					$.cookie('skin', skin, {path: '<w:path/>'});
					$('#skinUri').attr('href', '<w:path/>resources/css/'+skin+'.css');
				});
				
				//设置表单斑马线
				$(".table").addClass("table-striped");
			});
			
			$("#mymenus").myaccordion( {
// 				icons: { "header": "ui-icon-folder-collapsed", "activeHeader": "ui-icon-folder-open" },
				icons: { "header": "ui-icon-circle-triangle-e", "activeHeader": "ui-icon-circle-triangle-s" },
				active : ${act_menu},
				collapsible : true,
				beforeActivate: function( event, ui ) {
					//alert(event+"***"+ui.newPanel);
				}
			});
			
		</script>
		
	</div>
		
	</td>
	
	<%-- 是否显示关闭左侧菜单
	<td style="width: 9px; background-color: #DEDCDD">
		<img id="gb_id" src="<w:path/>resources/img/guanbi.png" onclick="hideShow();">
	</td>
	--%>
	<td valign="top" >
		
		<div id="content" class="container" style="width: 100%;">
					<%-- <c:if test="${not empty actionResult}">
						<div class="alert alert-${actionResult.type}">
							<button class="close" type="button" data-dismiss="alert">X</button>
							<spring:message code="${actionResult.message}"></spring:message>
						</div>
					</c:if> --%>
					<decorator:body></decorator:body>
				</div>
		
	</td>
	</tr>
</table>

</div>


	<script type="text/javascript">
	
	//弹出退出确认框
	function showSure(type)
	{
		$('#outtype').val(type);
		if(type == 1){
			$('#sure_font').html("确定要注销当前账号？");
		}else{
			$('#sure_font').html("确定要退出系统？");
		}
		$("#system_out_sure").show();
	}
	
	//隐藏退出确认框
	function cancle(id)
	{
		$(id).hide();
	}
	
	//注销账号或退出系统
	function logout()
	{
		var type = $('#outtype').val();
		window.location.href = "${path}/system/logout.do?outType="+type;
	}
	
	//显示或隐藏菜单栏
	function hideShow()
	{
		var i = $.cookie('toggleMenuStatus') || 0;
		$.cookie('toggleMenuStatus', ++i, { path: '<w:path/>' });
			
		$('#mymenus').toggleClass('span1');
		$('#mymenus').toggleClass('span2');
		
		if($('#mymenus').hasClass('span1')){
			$('#mymenus').show();
			$('#lef').width(205);
			$('#outside').width(205);
			$('#gb_id').attr('src','${path}/resources/img/guanbi.png');
			
		}else{
			$('#mymenus').hide();
			$('#lef').width(0);
			$('#outside').width(0);
			$('#gb_id').attr('src','${path}/resources/img/gb_before_r.png');
		}
		
		//当鼠标划上的时候显示小提示框
		$(".span1 a[rel=shortmenu]").tooltip({
			placement:'right'
		});
	}
	
	//给路径加上“当前位置”
	function addPlaceNow()
	{
		var titleText = "当前位置：" + $(".title").html();
		$(".title").empty().html(titleText);
	}
	
	$(document).ready(function(){
		$("#gb_id").mouseover(function()
		{
			var lef_width = $('#lef').width();
			if(lef_width == 0){
	    		$(this).attr('src','${path}/resources/img/gb_focus_r.png');
	    	}else{
	    		$(this).attr('src','${path}/resources/img/guanbi_focus.png');
	    	}
		}).mouseout(function()
		{
	    	var lef_width = $('#lef').width();
			if(lef_width == 0){
	    		$(this).attr('src','${path}/resources/img/gb_before_r.png');
	    	}else{
	    		$(this).attr('src','${path}/resources/img/guanbi.png');
	    	}
		});
		
		///设置当前页面顶部导航图片的宽度为当前显示页面的宽度
		$("#main_head").css("width",$(document).width());
		
		//屏蔽modal点击空白位置出现隐藏的问题
	    $('.modal').attr("data-backdrop","static");
		
		//屏蔽键盘上的enter按键引起的操作
		document.onkeydown = function(e){ 
		    var ev = document.all ? window.event : e;
		    if(ev.keyCode==13) {
		    	return false;
		    }
		}

		
		///显示标签云
		//$("#tagClound").css();
		
		//添加“当前位置”
		addPlaceNow();
		
		//模态窗口添加“关闭”title
		$("button[class='close']").attr("title","关闭");
	});
	
	</script>
	
</body>
</html>
