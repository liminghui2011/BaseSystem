<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../common/common_tags.jsp" %>
<!DOCTYPE html>
<html lang="en">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>修改参数</title>
</head>
<body>
<div id="accordion" class="accordion">
	<div class="accordion-group">
		<div class="accordion-heading">
			<div class="title">系统管理 > 参数管理 > 修改参数</div>
		</div>
		<div id="addAccordion" class="accordion-body in">
			<div class="accordion-top">
				<div class="control_text">
					修改参数
				</div>
				<div class="control_line"></div>
			</div>
			<div class="accordion-inner">
				<form:form action="update.do" method="post" modelAttribute="PARAM" cssClass="form-horizontal">
					<form:hidden path="id"/>
					<c:set var="nameError"><form:errors path="name"/></c:set>
					<c:set var="descriptionError"><form:errors path="description"/></c:set>
					<div class="control-group${empty nameError?'':' error'}">
						<label class="control-label" for="name">参数名</label>
						<div class="controls">
							<form:input path="name" maxlength="50" cssClass="span3"/>
							<span class="help-inline">${empty nameError?'只能输入字母、数字、下划线':nameError}</span>
						</div>
					</div>
					<div class="control-group${empty descriptionError?'':' error'}">
						<label class="control-label" for="description">参数说明</label>
						<div class="controls">
							<form:textarea path="description" maxlength="100" cssClass="span3"/>
							<span class="help-inline">${descriptionError}</span>
						</div>
					</div>
					<div class="control-group">
						<div class="controls">
							<table class="table table-hover table-condensed">
								<thead>
									<tr>
										<th>键</th>
										<th>值</th>
										<th>操作</th>
									</tr>
								</thead>
								<tbody id="pi">
									<c:forEach items="${PARAM.items}" var="item" varStatus="vs">
										<tr>
											<td>
												<form:input title="${PARAM.items[vs.index].name}" path="items[${vs.index}].name" cssStyle="width:90%"/>
												<span class="help-block"><form:errors path="items[${vs.index}].name"/></span>
											</td>
											<td>
												<form:input title="${PARAM.items[vs.index].value}" path="items[${vs.index}].value" cssStyle="width:90%"/>
												<span class="help-block"><form:errors path="items[${vs.index}].value"/></span>
											</td>
											<td>
												<form:hidden path="items[${vs.index}].id"/>
												<form:hidden path="items[${vs.index}].paramId"/>
												<a href="#" class="add btn btn-link">添加</a>
												<a href="#" class="remove btn btn-link">删除</a>
												
											</td>
										</tr>
									</c:forEach>
								</tbody>
							</table>
						</div>
					</div>
					
					<div class="form-actions">
						<button type="submit" class="btn btn-big btn-primary">提交</button>
						<button type="reset" class="btn btn-big btn-gray">重置</button>
						<a href="${returnURI}" class="btn btn-big btn-gray">返回</a>
					</div>
				</form:form>
			</div>
		</div>
	</div>
</div>
<script type="text/javascript">
	jQuery.validator.addMethod('param', function(value, element){
		return /^[a-zA-Z]+[a-zA-Z0-9_]*$/.test(value);
	});
	$('#PARAM').validate({
		rules:{
			name:{
				required:true,
				param:true
			}
		},
		messages:{
			name:{
				required:'参数名不能为空',
				param:'参数名无效'
			}
		},
		onfocusout: function(element){
			$(element).valid();
		},
		errorElement: 'span',
		errorPlacement: function(error, element) {
	    	element.next().empty();
	    	error.appendTo(element.next());
		},
	    /*errorClass: 'help-inline',
	   	highlight: function(element, errorClass) {
	    	$(element).parents('.control-group').addClass('error');
	  	},
	  	unhighlight: function(element, errorClass, validClass) {
	    	$(element).parents('.control-group').removeClass('error');
	  	}*/
	});
	
	function resetIndex(){
		//alert($('#pi').html())
		$('#pi tr').each(function(i, ele){
			var $name = $(ele).find('input:eq(0)');
			var $value = $(ele).find('input:eq(1)');
			$name.attr('name', 'items['+i+'].name');
			$value.attr('name', 'items['+i+'].value');
			//alert($name.attr('name')+"***"+$value.attr('name'));
		});
	}
	
	$('.add').live('click',function(){
		var size = $('#pi tr').size();
		//alert(size);
		var html = '<tr>';
			html +='<td><input type="text" name="items['+size+'].name" style="width:90%"/></td>';
			html +='<td><input type="text" name="items['+size+'].value" style="width:90%"/></td>';
			html +='<td><a href="#" class="add btn btn-link">添加</a><a href="#" class="remove btn btn-link">删除</a></td>';
			html +='</tr>';
			//alert($(this).parent().parent().html());
			$(this).parent().parent().after(html);
			resetIndex();
		return false;
	});
	
	$('.remove').live('click',function(){
		var pid = $(this).siblings().first().val();
		var size = $('#pi tr').size();
		//alert(size);
		if(size>1){
			//alert($(this).parent().parent().html());
			$(this).parent().parent().remove();
			//alert($(this).parent().parent().html());
			if(pid){
				$.ajax({
				   type: "POST",
				   url: "${path}/system/param/removeParamItem.do",
				   data: {'pid':pid}
				});
			}
			resetIndex();
		}
		return false;
		
	});
</script>
</body>
</html>