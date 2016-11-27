<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../common/common_tags.jsp" %>
<!DOCTYPE html>
<html lang="en">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>权限列表</title>
</head>
<body>
<div id="accordion" class="accordion">
	<div class="accordion-group">
		<div class="accordion-heading">
			<div class="title">系统管理 > 权限管理 > 权限列表</div>
		</div>
		<div id="addAccordion" class="accordion-body in">
			<div class="accordion-inner">
			<div style="border: 0px solid red; height: 33px;">
				<form:form action="list.do" method="post" modelAttribute="privilege" cssClass="form-inline">
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
					<label for="name">权限名</label>&nbsp;
					<form:input path="name"/>&nbsp;&nbsp;&nbsp;&nbsp;
					<label for="group">权限组名</label>&nbsp;
					<form:input path="group"/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;				
					<a href="#" onclick="$('#privilege').submit(); return false;" class="btn btn-primary">查询</a>
					&nbsp;&nbsp;<a href="add.do" class="btn btn-default">新增</a>
				</form:form>	
			</div>
				<table class="table table-hover table-condensed">
					<thead>
						<tr>
							<th>权限名</th>
							<th>权限组名</th>
							<th>权限描述</th>
							<th>权限路径</th>
							<th width="10%">操作</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach items="${pb.dataList}" var="privilege">
							<tr>
								<td>${privilege.name}</td>
								<td>${privilege.groupName}</td>
								<td>${privilege.description}</td>
								<td>${privilege.uri}</td>
								<td>
									<a href="${path}/operprivilege/list.do?privilegeId=${privilege.id}" class="detail">详细</a>
									<a href="update.do?id=${privilege.id}" class="bianji">修改</a>
									<a href="javascript:remove(${privilege.id})" class="shanchu" >删除</a>
								</td>
							</tr>
						</c:forEach>
						<tr>
							<td colspan="5" form-id="privilege" class="paginationPanel">
								<ltPage:page pageBean="${pb}" />
							</td>
						</tr>
					</tbody>
				</table>			
			</div>
		</div>
	</div>
</div>
<div id="modal" class="modal hide fade">
	<div class="modal-header">
	  <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
	  <div class="modal-title">提示框</div>
 	</div>
	<div class="modal-body">
	  <p>真的要删除该权限吗？</p>
	</div>
    <div class="modal-footer">
      <a href="javascript:remove()" class="btn btn-big btn-primary" >是</a>
      <a href="#" class="btn btn-big btn-gray" data-dismiss="modal">否</a>
    </div>
</div>

<script type="text/javascript">
function remove(id){
	if(id){
		$('#modal').modal();
		$('#modal').data('id', id);
	}else{
		$('#modal').modal('hide');
		location.href = 'remove.do?id='+$('#modal').data('id');
	}
}
</script>
</body>
</html>