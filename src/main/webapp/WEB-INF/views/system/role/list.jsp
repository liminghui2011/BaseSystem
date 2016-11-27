<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../common/common_tags.jsp" %>
<!DOCTYPE html>
<html lang="en">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>角色列表</title>
</head>
<body>
<div id="accordion" class="accordion">
	<div class="accordion-group">
		<div class="accordion-heading">
			<div class="title">系统管理 > 角色管理 > 角色列表</div>
		</div>
		<div id="addAccordion" class="accordion-body in">
			<div class="accordion-inner">
				<div style="border: 0px solid red; height: 33px;">
					<form:form action="list.do" modelAttribute="role" method="post" cssClass="form-inline">
						<label>&nbsp;&nbsp;&nbsp;角色名&nbsp;
							<form:input path="name"/>
						</label>&nbsp;
						<button id="rolesearch" type="button" class="btn btn-primary" onclick="$('#role').submit(); return false;" >搜索</button>
						&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
						<op:op url="system/role/add.do" ptype="3">
							<a href="${path}/system/role/add.do"   class="btn btn-default">增加角色</a>
						</op:op>
					</form:form>
				</div>
				<table class="table table-hover table-condensed">
					<thead>
						<tr>
							<th>角色名</th>
							<th width="40%">描述</th>
							<th width="30%">操作</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach items="${pb.dataList}" var="role">
							<tr>
								<td>${role.name}</td>
								<td style="word-wrap:break-word;word-break:break-all;">${role.description}</td>
								<td>
										<a href="view.do?id=${role.id}" >详细</a>&nbsp;
										<op:op url="system/role/update.do" ptype="3">
											<a href="update.do?id=${role.id}"  class="bianji">修改</a>&nbsp;
										</op:op>
										<c:if test="${role.id!=33}">
											<op:op url="system/role/remove.do" ptype="3">
												<a href="${path}/system/role/remove.do?id=${role.id}" class="shanchu" target="deleteOne" >删除</a>
											</op:op>
										</c:if>
									
								</td>
							</tr>
						</c:forEach>
						<tr>
							<td colspan="3" form-id="role" class="paginationPanel"><ltPage:page pageBean="${pb}" /></td>
						</tr>
					</tbody>
				</table>
			</div>
		</div>
	</div>
</div>
</body>
</html>