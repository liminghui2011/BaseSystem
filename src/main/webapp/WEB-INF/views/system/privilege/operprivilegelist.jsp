<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../common/common_tags.jsp" %>
<!DOCTYPE html>
<html lang="en">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>操作权限列表</title>
</head>
<body>
<div id="accordion" class="accordion">
	<div class="accordion-group">
		<div class="accordion-heading">
			<div class="title">系统管理 > 权限管理 > 操作权限列表</div>
		</div>
		<div id="addAccordion" class="accordion-body in">
			<div class="accordion-inner">
				<div class="accordion-top">
					<div class="control_text">
						操作权限列表
					</div>
					<div class="control_line"></div>
				</div>
				<form:form action="${path}/operprivilege/add.do" modelAttribute="operprivilege" method="post" cssClass="form-horizontal">
					<form:hidden path="opId"/>
					<input type="hidden" name="privilegeId" value="${privilege.id}"/>
					<c:set var="nameError"><form:errors path="operName"></form:errors></c:set>
					<c:set var="levelError"><form:errors path="operLevel"></form:errors></c:set>
					<c:set var="uriError"><form:errors path="uri"></form:errors></c:set>
					<div class="control-group">
						<label class="control-label" for="operName">所属菜单</label>
						<div class="controls">
							<input type="text" value="${privilege.name}" disabled="disabled" class="span3"/>
						</div>
					</div>
					<div class="control-group${not empty nameError?' error':''}">
						<label class="control-label" for="operName">操作权限名</label>
						<div class="controls">
							<form:input path="operName" maxlength="20" class="span3"></form:input>
							<span class="help-inline">${nameError}</span>
						</div>
					</div>
					<div class="control-group${not empty levelError?' error':''}">
						<label class="control-label" for="operName">权限级别</label>
						<div class="controls">
							<form:select path="operLevel" class="span3">
								<form:option value="0">基本操作权限</form:option>
								<form:option value="1">一级审核权限</form:option>
								<form:option value="2">二级审核权限</form:option>
							</form:select>
							<span class="help-inline">${levelError}</span>
						</div>
					</div>
					<div class="control-group${not empty uriError?' error':''}">
						<label class="control-label" for="uri">资源URI</label>
						<div class="controls">
							<form:textarea path="uri" cssClass="span3" rows="3"/>
							<span class="help-inline">${empty uriError?'受权限保护的URI,如：privilege/add':uriError}</span>
						</div>
					</div>
					<div class="control-group">
						<label class="control-label" for="operDescription">操作权限描述</label>
						<div class="controls">
							<form:textarea path="operDescription" cssClass="span3" rows="3"/>
						</div>
					</div>
					<div class="form-actions">
						<button class="btn btn-big btn-primary" type="submit">提交</button>
						<a href="${path}/system/privilege/list.do" class="btn btn-big btn-gray">返回</a>
					</div>
				</form:form>
				
				<table class="table table-hover table-condensed">
					<thead>
						<tr >
							<th>操作权限名</th>
							<th>所属菜单</th>
							<th>权限描述</th>
							<th>权限路径</th>
							<th width="10%">操作</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach items="${oplist}" var="op">
							<tr>
								<td>${op.operName}</td>
								<td>${privilege.name}</td>
								<td>${op.operDescription}</td>
								<td>${op.uri}</td>
								<td>
									<div class="btn-group">
										<a href="${path}/operprivilege/update.do?opId=${op.opId}" class="btn btn-primary">修改</a>
										<a href="javascript:remove(${op.opId})" class="btn btn-danger">删除</a>
									</div>
								</td>
							</tr>
						</c:forEach>
					</tbody>
				</table>			
			</div>
		</div>
	</div>
</div>
<div id="modal" class="modal hide fade">
	<div class="modal-header">
	  <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
	  <h3>删除权限</h3>
 	</div>
	<div class="modal-body">
	  <p>真的要删除该权限吗？</p>
	</div>
    <div class="modal-footer">
      <a href="#" class="btn btn-primary" data-dismiss="modal">否</a>
      <a href="javascript:remove()" class="btn" >是</a>
    </div>
</div>

<script type="text/javascript">
function remove(id){
	if(id){
		$('#modal').modal();
		$('#modal').data('id', id);
	}else{
		$('#modal').modal('hide');
		location.href = 'remove.do?opId='+$('#modal').data('id');
	}
}

jQuery.validator.addMethod("protected_uri", function(value, element) { 
  return /^[a-zA-Z0-9\?\\\/\+\.=&\-\_]+$/.test(value); 
});

$('#operprivilege').validate({
	rules:{
		operName: 'required',
		operLevel: 'required',
		uri: {
			maxlength:256,
			protected_uri:true
		}
	},
	messages:{
		operName:'操作权限名不能为空！',
		operLevel:'权限级别不能为空！',
		uri: {
			maxlength:'路径的最大长度为256字符',
			protected_uri:'无效的资源uri'
		}
	},
	onfocusout: function(element) {
        $(element).valid();
    },
    errorElement: 'span',
    errorPlacement: function(error, element) {
    	element.next().empty();
    	error.appendTo(element.next());
	},
   	/*highlight: function(element, errorClass) {
    	$(element).parents('.control-group').addClass('error');
  	},
  	unhighlight: function(element, errorClass, validClass) {
    	$(element).parents('.control-group').removeClass('error');
  	}*/
});
</script>
</body>
</html>