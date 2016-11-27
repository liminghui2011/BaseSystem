<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../common/common_tags.jsp" %>
<!DOCTYPE html>
<html lang="en">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>新增角色</title>
</head>
<body>
<div id="accordion" class="accordion">
	<div class="accordion-group">
		<div class="accordion-heading">
			<div class="title">系统管理 > 角色管理 > 新增角色</div>
		</div>
		<div id="addAccordion" class="accordion-body in">
			<div class="accordion-top">
				<div class="control_text">
					新增角色
				</div>
				<div class="control_line"></div>
			</div>
			<div class="accordion-inner">
				<form:form action="add.do" method="post" modelAttribute="role" cssClass="form-horizontal">
					<c:set var="nameError"><form:errors path="name"/></c:set>
					<c:set var="descriptionError"><form:errors path="description"/></c:set>			
					<c:set var="errorAssignedPrivilegeIds"><form:errors path="assignedPrivilegeIds"></form:errors></c:set>		
					
					<div class="control-group${empty nameError?'':' error'}">
						<label class="control-label" for="name"><span class="help-inline input_msg_style">*</span>角色名</label>
						<div class="controls">
							<form:input id="nrole" path="name" autocomplete="off" class="span3"/>
							<span style="color:red;" class="help-inline"></span>
						</div>
					</div>
					<div class="control-group${empty descriptionError?'':' error'}">
						<label class="control-label" for="description">描述</label>
						<div class="controls">
							<form:textarea path="description" rows="4" class="span3"/>
							<span style="color:red;" class="help-inline"></span>
						</div>
					</div>
					<input type="hidden" id="privilegeIds" name="privilegeIds" value=""/>
					<c:forEach items="${rolejson}" var="tree" varStatus="vs">
					
						<div style="border-bottom: 1px solid #E0E9EE; margin-left: 
							<c:if test="${vs.index==0}">170px</c:if><c:if test="${vs.index!=0}">170px</c:if>; width: 280px;">						
							<div style="border: 0px solid green; width: auto; display: block;">
								<ul id="ztree_${vs.index}" class="ztree"></ul>							
							</div>							
						</div>
						
					</c:forEach>
					
					<div class="form-actions" style="clear: both;">
						<button id="btn_submit" class="btn btn-big btn-primary" type="button">提交</button>
				    	<button id="btn_back" class="btn btn-big btn-gray" type="button">返回</button>
					</div>
				</form:form>
			</div>
		</div>
	</div>
</div>

<script type="text/javascript" src="${path}/resources/js/jquery.ztree.excheck-3.4.min.js"></script>
<script type="text/javascript">


$('#role').validate({
	rules:{
		name:{
			required:true,
			maxlength:20,
			remote:{
				type:"post",
		        url: "${path}/system/role/checkRole.do",
		        data: {
		        	roleName:function(){
		        		return $("#nrole").val();
		        	}
		        }
			}
		},
		description:{
			maxlength:200
		}
	},
	messages:{
		name:{
			required:'<spring:message code="role.name.required"/>',
			maxlength:"长度不能超过20个字符",
			remote:"角色名已经存在"
		},
		description:{
			maxlength:'<spring:message code="role.description.max"/>'
		}
	},
	onfocusout: function(element) {
        $(element).valid();
    },
    errorElement: 'span',
    errorPlacement: function(error, element) {
		error.appendTo(element.next());
	},
  	submitHandler: function(form){
		form.submit();
	},
});

//校验是否分配了权限
$('#btn_submit').click(function(){

	var classifyIds = "";
	<c:forEach items="${rolejson}" var="tree" varStatus="vs">
		treeObj = $.fn.zTree.getZTreeObj('ztree_${vs.index}');
		nodes=treeObj.getCheckedNodes(true);
		if(nodes.length>0){
			for(var i=0;i<nodes.length;i++){
				var opId = nodes[i].pId;
				if(opId==0){
					classifyIds += (opId+"-"+nodes[i].id + ",");
				}else{
					classifyIds += (nodes[i].id+"-"+opId + ",");
				}
			}
		}
	</c:forEach>
	if(classifyIds==""){
		alertModal("角色的权限不能为空！");
	}else{
		classifyIds = classifyIds.substring(0, (classifyIds.length-1));
		$("#privilegeIds").val(classifyIds);
		$('#role').submit();
	}
})

//响应返回按钮
$('#btn_back').click(function(){
	window.location.href = '${returnURI}';
});


var setting2 = {
	check: {
		enable: true,
		chkboxType: { "Y" : "ps", "N" : "s" }
	},
	data: {
		simpleData: {
			enable: true
		}
	},
	callback: {
		beforeCollapse: zTreeBeforeCollapse,
	},
};

//禁止所有展开的父节点折叠
function zTreeBeforeCollapse(treeId, treeNode){
	return true;
}

var treeObj;
var nodes;

jQuery(document).ready(function(){
	<c:forEach items="${rolejson}" var="tree" varStatus="vs">
		$.fn.zTree.init($("#ztree_${vs.index}"), setting2, ${tree});
		treeObj = $.fn.zTree.getZTreeObj('ztree_${vs.index}');
		nodes=treeObj.transformToArray(treeObj.getNodes());
		treeObj.expandAll(false);
	</c:forEach>
});

</script>
</body>
</html>