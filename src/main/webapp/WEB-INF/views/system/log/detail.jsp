<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<div class="modal-header">
  <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
 <div class="modal-title">详细日志</div>
	</div>
<div class="modal-body">
  <table class="table table-bordered">
  		<tr>
  			<td width="80" >操作者</td>
  			<td >${log.actor}</td>
  		</tr>
  		<tr>
  			<td width="80">操作时间</td>
  			<td ><fmt:formatDate value="${log.addDate}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
  		</tr>
  		<tr>
  			<td width="80">操作描述</td>
  			<td >${log.content}</td>
  		</tr>
  		<tr>
  			<td width="80">请求地址</td>
  			<td >${log.uri}</td>
  		</tr>
  		<tr>
  			<td width="80">请求参数</td>
  			<td >${log.param}</td>
  		</tr>
  		<tr>
  			<td width="80">异常信息</td>
  			<td >${log.detail.content}</td>
  		</tr>
  	</table>
</div>
   <div class="modal-footer">
     <a href="#" class="btn btn-big btn-gray" data-dismiss="modal">关闭</a>
</div>
