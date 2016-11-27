<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="com.ocean.lmh.base.model.entity.system.MenuValue"%>
<%@page import="com.itextpdf.text.log.SysoLogger"%>
<%@page import="com.ocean.lmh.base.constant.AppConstant"%>
<%@ include file="WEB-INF/views/common/common_tags.jsp" %>
<!DOCTYPE html>
<html lang="en">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>Insert title here</title>
<script type="text/javascript" src="resources/js/jquery.min.js"></script>
</head>
<body>
<%!private void list(MenuValue tree){
	System.out.println(tree.getName()+","+(tree.getParent()!=null?tree.getParent().getName():"") + "," + tree.isEnable());
	for(MenuValue child: tree.getChildren()){
		list(child);
	}
}%>
<%
    MenuValue userMenuTree = (MenuValue)session.getAttribute(AppConstant.USER_MENU_TREE);
//list(userMenuTree);
%>
${USER_HOLDER}<p></p>
${SESSIONID_HOLDER }
<p></p>
	省：<select name="province" class="province">
		<option></option>
		<!--  
			<option value="1" selected="selected">1</option>
			<option value="2">2</option>
		-->		
		</select>
	市：<select name="city" class="city">
	<option></option>
		<!-- 
			<option value="1">11</option>
			<option value="22" selected="selected">22</option>
		 -->
		</select>
		<p></p>
			省2：<select name="province" class="province2">
		<option></option>
		<!--  
			<option value="1" selected="selected">1</option>
			<option value="2">2</option>
		-->		
		</select>
	市2：<select name="city" class="city2">
	<option></option>
		<!-- 
			<option value="1">11</option>
			<option value="22" selected="selected">22</option>
		 -->
		</select>
<script type="text/javascript" src="resources/js/pc.js"></script>	
<script type="text/javascript">
jQuery(document).pc({province:'province', city:'city', defaultProvince:'440000', defaultCity:'440100'});
jQuery(document).pc({province:'province2', city:'city2'});
</script>
</body>
</html>