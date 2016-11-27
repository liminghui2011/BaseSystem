<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../common/common_tags.jsp" %>
<!DOCTYPE html>
<html lang="en">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>查看角色</title>
</head>
<body>
<div id="accordion" class="accordion">
	<div class="accordion-group">
		<div class="accordion-heading">
			<div class="title">系统管理 > 角色管理 > 角色信息</div>
		</div>
		<div id="addAccordion" class="accordion-body in">
			<div class="accordion-top">
				<div class="control_text">
					角色信息
				</div>
				<div class="control_line"></div>
			</div>
			<div class="accordion-inner">
				<form:form method="post" modelAttribute="role" cssClass="form-horizontal">
					<c:set var="nameError"><form:errors path="name"/></c:set>
					<c:set var="descriptionError"><form:errors path="description"/></c:set>			
					<c:set var="errorAssignedPrivilegeIds"><form:errors path="assignedPrivilegeIds"></form:errors></c:set>		
					
					<div class="control-group${empty nameError?'':' error'}">
						<label class="control-label" for="name">角色名</label>
						<div class="controls">
							<form:input path="name" maxlength="20" readonly="true" cssClass="span3"/>
							<span class="help-inline">${nameError}</span>
						</div>
					</div>
					<div class="control-group${empty descriptionError?'':' error'}">
						<label class="control-label" for="description">描述</label>
						<div class="controls">
							<form:textarea path="description" rows="4" class="span3" readonly="true"/>
							<span class="help-inline">${descriptionError}</span>
						</div>
					</div>
					
					<c:forEach items="${rolejson}" var="tree" varStatus="vs">
					
						<div style="border-bottom: 1px solid #E0E9EE; margin-left: 
							<c:if test="${vs.index==0}">170px</c:if><c:if test="${vs.index!=0}">170px</c:if>; width: 280px;">						
							<div style="border: 0px solid green; width: auto; display: block;">
								<ul id="ztree_${vs.index}" class="ztree"></ul>							
							</div>							
						</div>
						
					</c:forEach>
					
					<div class="form-actions" style="clear: both; height: 35px;border: 0px solid red;" >
						<button id="btn_back" class="btn btn-big btn-gray" type="button">返回</button>
					</div>
				</form:form>
			</div>
		</div>
	</div>
</div>

<script type="text/javascript" src="${path}/resources/js/jquery.ztree.excheck-3.4.min.js"></script>
<script type="text/javascript">
var setting2 = {
	check: {
		enable: true
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
		for(var i=0;i<nodes.length;i++){
			nodes[i].chkDisabled=true;
		}
		treeObj.expandAll(true);
	</c:forEach>
});

//响应返回按钮
$('#btn_back').click(function(){
	window.location.href = '${returnURI}';
})
</script>
</body>
</html>