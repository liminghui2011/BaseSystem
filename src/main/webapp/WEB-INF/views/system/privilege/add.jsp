<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../common/common_tags.jsp" %>
<!DOCTYPE html>
<html lang="en">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>新增权限</title>
</head>
<body>
<div id="accordion" class="accordion">
	<div class="accordion-group">
		<div class="accordion-heading">
			<div class="title">系统管理 > 权限管理 > 新增权限</div>
		</div>
		<div id="addAccordion" class="accordion-body in">
			<div class="accordion-top">
				<div class="control_text">
					新增权限
				</div>
				<div class="control_line"></div>
			</div>
			<div class="accordion-inner">
				<form:form action="add.do" modelAttribute="privilege" method="post" cssClass="form-horizontal">
					<c:set var="groupError"><form:errors path="groupName"></form:errors></c:set>
					<c:set var="nameError"><form:errors path="name"></form:errors></c:set>
					<c:set var="uriError"><form:errors path="uri"></form:errors></c:set>
					<div class="control-group${not empty groupError?' error':''}">
						<label class="control-label" for="groupName">权限组名</label>
						<div class="controls">
							<form:input path="groupName" maxlength="20" cssClass="span3"/>
							<span class="help-inline">${empty groupError?'用于对权限进行分类':groupError}</span>
						</div>
					</div>
					<div class="control-group${not empty nameError?' error':''}">
						<label class="control-label" for="name">权限名</label>
						<div class="controls">
							<form:input path="name" maxlength="20" cssClass="span3"></form:input>
							<span class="help-inline">${nameError}</span>
						</div>
					</div>
					
					<div class="control-group${not empty uriError?' error':''}">
						<label class="control-label" for="uri">资源URI</label>
						<div class="controls">
							<form:textarea path="uri" cssClass="span3" rows="5"/>
							<span class="help-inline">${empty uriError?'受权限保护的URI,如：privilege/add':uriError}</span>
						</div>
					</div>
					<div class="control-group">
						<label class="control-label" for="description">权限描述</label>
						<div class="controls">
							<form:textarea path="description" cssClass="span3" rows="5"/>
						</div>
					</div>
					<div class="form-actions">
						<button class="btn btn-big btn-primary" type="submit">提交</button>
						<button class="btn btn-big btn-gray" type="reset">重置</button>
						<a href="${returnURI}" class="btn btn-big btn-gray">返回</a>
					</div>
				</form:form>
			</div>
		</div>
	</div>
</div>

<script type="text/javascript">

jQuery.validator.addMethod("protected_uri", function(value, element) { 
  return /^[a-zA-Z0-9\?\\\/\+\.=&\-\_]+$/.test(value); 
});

$('#privilege').validate({
	rules:{
		groupName: 'required',
		name:'required',
		uri: {
			maxlength:256,
			protected_uri:true
		}
	},
	messages:{
		groupName:'<spring:message code="privilege.group.required"/>',
		name:'<spring:message code="privilege.name.required"/>',
		uri: {
			maxlength:'<spring:message code="privilege.uri.max"/>',
			protected_uri:'<spring:message code="privilege.uri.invalid"/>'
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