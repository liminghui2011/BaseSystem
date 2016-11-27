<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../common/common_tags.jsp" %>
<!DOCTYPE html>
<html lang="en">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>新增用户</title>
</head>
<body>
<div id="accordion" class="accordion">
	<div class="accordion-group">
		<div class="accordion-heading">
			<div class="title">系统管理 > 账号管理 > 新增账号</div>
		</div>
		<div id="addAccordion" class="accordion-body in">
			<div class="accordion-inner" style="border: 0px solid red;">
				<form:form id="user" action="add.do" method="post" modelAttribute="user" cssClass="form-horizontal">
					<c:set var="useridError"><form:errors path="userid"></form:errors></c:set>
					<c:set var="nicknameError"><form:errors path="nickname"></form:errors></c:set>
					<c:set var="passwordError"><form:errors path="password"></form:errors></c:set>
					<c:set var="rolesError"><form:errors path="roles"></form:errors></c:set>
					
					<div class="control-group${empty useridError?'':' error'}" style="border: 0px solid red;">
						<label class="control-label" for="userid"><span class="help-inline input_msg_style">*</span>账号名</label>
						<div class="controls">
							<form:input id="userid" path="userid" autocomplete="off"/>
							<span style="color:red" class="help-inline"></span>
						</div>
					</div>
					
					<div class="control-group${empty nicknameError?'':' error'}">
						<label class="control-label" for="nickname">姓名</label>
						<div class="controls">
							<form:input path="nickname"/>
							<span style="color:red" class="help-inline"></span>
						</div>
					</div>		
												
					<div class="role control-group${empty rolesError?'':' error'}">
						<label class="control-label">角色</label>
						<div class="controls">
							<form:select id="rolevalue" path="roles[0].id">
								<c:forEach items="${roles}" var="role" varStatus="vs">
									<form:option value="${role.id}">${role.name}</form:option>
								</c:forEach>
							</form:select>
							<span class="help-block">${rolesError}</span>							
						</div>
					</div>
					<div class="control-group">
						<label class="control-label" for="remark">备注</label>
						<div class="controls">
							<form:textarea path="remark" rows="3" cols="20" />
						</div>
					</div>
					<div class="form-actions">
						<button class="lt_commit" type="submit">&nbsp;</button>
				    	<button id="btn_back" class="lt_back" type="button">&nbsp;</button>
					</div>
				</form:form>
			</div>
		</div>
	</div>
</div>

<script type="text/javascript" src="<w:path/>resources/js/pc.js"></script>
<script type="text/javascript">
	
	jQuery.validator.addMethod('word', function(value, element){
		return /^[a-zA-Z0-9]+$/.test(value);
	});
	
	$('#user').validate({
		rules:{
			userid:{
				required:true,
				maxlength:20,
				word:true,
				remote:{
					type:'post',
			        url: "${path}/system/user/checkUser.do",
			        data:{
			        	userId: function() 
	                    {
	                        return $("#userid").val();
	                    }
			        }
				}
			},
			nickname: {
                maxlength:5				
			},
			remark:{
				maxlength:200,
			}
		},
		messages:{
			userid:{
				required:"请输入登录账号",
				maxlength:"最长为20个字符",
				word:"只能输入英文、数字",
				remote:"登录名称已存在"
			},
			nickname:{
				maxlength:'最长为5个字符',	
			},
			remark:{
				maxlength:"长度不能超过200个字符",
			}
		},
		onfocusout: function(element) {
	        $(element).valid();
	    },
	    submitHandler: function(form){
			var value = $('#rolevalue').val();
			if(value==null || value==""){
				$('#rolevalue').addClass('error');
				$('#rolevalue').next().css("color","red").text('<spring:message code="user.roles.min"/>');
				return false;
			} 
			form.submit();
		},
	    errorElement: 'span',
	    errorPlacement: function(error, element) {
			error.appendTo(element.next());
		}
	});

	$('#btn_back').click(function(){
		window.location.href = '${returnURI}';
	});
	
</script>
</body>
</html>