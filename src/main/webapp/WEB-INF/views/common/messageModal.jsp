<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<%--弹出审批输入框--%>
<div id="batchAuditingModel" class="modal hide fade" style="width: 550px;">
 <form id="auditingForm" action="" method="post" style="margin-bottom: 0px;">
  <div class="modal-header">
  	<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
    <div class="modal-title">审核</div>
  </div>
  <div class="modal-body">
  	<div class="container-fluid">
		<div class="row-fluid">
			<div style="float: left;">
				<p style="color: #A43E1B;float: right;font-size:14px;"><span class="help-inline input_msg_style">*</span>审核说明:</p>
			</div>
			<div style="width: 400px;float: right;">
				<input type="hidden" name="token" value="${token}" />
				<textarea id="auditingDes" name="auditingDes" rows="5" style="width: 100%" class="{maxlength:200,required:true}"></textarea>
			</div>
		</div>
	</div>

  </div>
  <div class="modal-footer">
  	<input id="buttonFlag" name="buttonFlag" type="hidden">
  	<input id="jsonStr" name="jsonStr" type="hidden">
	<button id="auditing_ok" class="lt_sure">&nbsp;</button>
	<button id="auditing_cancle" data-dismiss="modal" class="lt_cancle">&nbsp;</button>
  </div>
</form>
</div>
<!-- 成功消息提示框 -->
<div id="msgModel" class="modal hide fade" style="width:350px ">
  <div class="modal-header">
    <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
    <div class="modal-title">提示框</div>
  </div>
  <div class="modal-body">
  	 <div style="float: left;">
	  	 <div class="right-span" style="width: 50px;"></div>
	  	 <div class="modal-msg" style="width: 200px;">
	  		<h4 id="msgModel_h" style="color: #4D8848">成功</h4>
	  		<p id="msgModel_p" style="color: #9B9B9B">
	  			<c:if test="${not empty actionResult.message}" >
		  			<spring:message code="${actionResult.message}"></spring:message> 
		  		</c:if>
		  		<c:if test="${empty actionResult}" >
		  				操作成功
		  		</c:if>
	  		</p>
	  	 </div>
	 </div>
  </div>
  <div class="modal-footer">
    <button id="msgModel_close"  class="btn btn-big btn-gray" data-dismiss="modal" aria-hidden="true">关闭</button>
  </div>
</div> 

<!-- 失败信息提示框 -->

<div id="msgModel_error" class="modal hide fade" style="width:350px ">
  <div class="modal-header">
  	<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
    <div class="modal-title">提示框</div>
  </div>
  <div class="modal-body">
  	<div style="float: left;">
	  	<div class="false-span" style="width: 50px;"></div>
	  	<div class="modal-msg" style="width: 200px;">
	  			<h4 id="msgModel_error_h" style="color: #A30000">失败</h4>
		  		<p id="msgModel_error_p" style="color: #9B9B9B">
		  		<c:if test="${not empty actionResult.message}" >
			  			<spring:message code="${actionResult.message}"></spring:message> 
			  		</c:if>
			  		<c:if test="${empty actionResult}" >
			  				操作失败
			  		</c:if>
		  		</p>
	  	</div>
	 </div>
  </div>
  <div class="modal-footer">
  	<button id="msgModel_error_close"  class="btn btn-big btn-gray" data-dismiss="modal" aria-hidden="true">关闭</button>
  </div>
</div>

<!-- 弹出提示框 -->
<div id="alertModel" class="modal hide fade">
  <div class="modal-header">
  	<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
    <div class="modal-title">提示框</div>
  </div>
  <div class="modal-body">
   <div class="span2" style="border: 0px solid red;text-align: center;">
  		<img id="alertModel_img" src="" height="48px" width="48px" border="0px">
  	</div>
  	<div class="span10" style="border: 0px solid red;width: 80%;">
  		<h4 id="alertModel_h" style="color: #4D8848"></h4>
  		<p id="alertModel_p" style="color: #333333"></p>
  		 
  	</div>
  </div>
  <div class="modal-footer">
  	<button id="alertModel_close"  class="btn btn-big btn-gray" data-dismiss="modal">关闭</button>	
  </div>
</div>

<%--删除提示框model--%>
<div  class="modal hide fade delModel">
  <div class="modal-header">
    <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
    <div class="modal-title">提示框</div>
  </div>
  <div class="modal-body">
    <p id="del_p">确定要删除这条记录吗？</p>
    <br/><br/><br/>
  </div>
  <div class="modal-footer" >
  	<input id="del_id" type="hidden">
  	
  	<button   class="btn btn-big btn-primary" onclick="deleteOne()">确定</button>
  	
  	<button class="btn btn-big btn-gray" data-dismiss="modal" aria-hidden="true">关闭</button>
  	
  </div>
</div>

<%--批量删除提示框model--%>
<div id="batchdelModel" class="modal hide fade">
  <div class="modal-header">
    <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
    <div class="modal-title">提示框</div>
  </div>
  <div class="modal-body">
    <p id="del_p">确定要删除所选记录吗？</p>
    <br/><br/><br/>
  </div>
  <div class="modal-footer" >
  	<input id="batchdel_id" type="hidden">
  	
  	<button id="batchdelModel_del_a"  class="btn btn-big btn-primary" onclick="batchDelete()" >确定</button>	
  	
  	<button id="batchdelModel_close" class="btn btn-big btn-gray" data-dismiss="modal" aria-hidden="true">关闭</button>	
    
  </div>
</div>


<%--批量删除提示框model--%>
<div id="batchdelOperater" class="modal hide fade">
  <div class="modal-header">
    <button type="button" class="close" data-dismiss="modal" >&times;</button>
    <div class="modal-title">提示框</div>
  </div>
  <div class="modal-body">
    <p id="del_p">请选择要操作的记录!</p>
    <br/><br/><br/>
  </div>
  <div class="modal-footer" >
  	<button  class="btn btn-big btn-primary" data-dismiss="modal" >关闭</button>	
  </div>
</div>

<%--Updatemodel  更新弹出框--%>
<div id="Updatemodal" class="modal hide fade">
  
</div>

<%--batchUpdatemodal  更新弹出框--%>
<div id="dialog" class="modal hide fade" >
  
</div>

<div  id="batchSave" class="modal hide fade">


</div>





