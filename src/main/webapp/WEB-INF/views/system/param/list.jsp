<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../common/common_tags.jsp" %>
<!DOCTYPE html>
<html lang="en">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>参数列表</title>
</head>
<body>
<div id="accordion" class="accordion">
	<div class="accordion-group">
		<div class="accordion-heading">
			<div class="title">系统管理 > 参数管理 > 参数列表</div>
		</div>
		<div id="addAccordion" class="accordion-body in">
			<div class="accordion-inner">
				<div style="border: 0px solid red; height: 33px;">
				<form:form action="list.do" method="post" modelAttribute="PARAM" cssClass="form-inline">
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
					<label class="">
					参数名&nbsp;<form:input path="name"/>
					</label>&nbsp;&nbsp;&nbsp;
					<label>
					参数说明&nbsp;<form:input path="description"/>
					</label>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;					
					<a href="#" onclick="$('#PARAM').submit(); return false;" class="btn btn-primary">查询</a>&nbsp;&nbsp;
					<a href="add.do" class="btn btn-default">新增</a>
				
				</form:form>
			</div>
				<table class="table table-hover table-condensed">
					<thead>
						<tr >
							<th>参数名</th>
							<th>参数说明</th>
							<th width="10%">操作</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach items="${pb.dataList}" var="Param">
							<tr>
								<td>${Param.name}</td>
								<td>${Param.description}</td>
								<td>
										<a href="view.do?id=${Param.id}" class="detail">详细</a>
										<a href="update.do?id=${Param.id}" class="bianji">修改</a>
										<a href="javascript:remove(${Param.id});" class="shanchu">删除</a>
								</td>
							</tr>
						</c:forEach>
						<tr>
							<td colspan="3" form-id="PARAM" class="paginationPanel"><ltPage:page pageBean="${pb}" /></td>
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
	  <h3>删除参数</h3>
 	</div>
	<div class="modal-body">
	  <p>真的要删除该参数吗？</p>
	</div>
    <div class="modal-footer">
      <a href="javascript:remove()" class="btn btn-primary">是</a>
      <a href="#" class="btn btn-default" data-dismiss="modal">否</a>
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