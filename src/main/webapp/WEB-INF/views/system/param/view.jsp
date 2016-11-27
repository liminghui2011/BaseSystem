<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../common/common_tags.jsp" %>
<!DOCTYPE html>
<html lang="en">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>查看参数</title>
</head>
<body>
<div id="accordion" class="accordion">
	<div class="accordion-group">
		<div class="accordion-heading">
			<div class="title">系统管理 > 参数管理 > 参数详情</div>
		</div>
		<div id="addAccordion" class="accordion-body in">
			<div class="accordion-inner">
				<div class="row-fluid">
					<div class="span2">参数名：</div>
					<div class="span10">${PARAM.name}</div>
				</div>
				<div class="row-fluid">
					<div class="span2">参数说明：</div>
					<div class="span10">${PARAM.description}</div>
				</div>
				
				<div class="row-fluid">
					<table class="table table-hover table-condensed">
						<thead>
							<tr>
								<th>键</th>
								<th>值</th>
							</tr>
						</thead>
						<tbody>
							<c:forEach items="${PARAM.items}" var="item" varStatus="vs">
								<tr>
									<td>${item.name}</td>
									<td>${item.value}</td>
								</tr>
							</c:forEach>
						</tbody>
					</table>
					<div class="form-actions" style="clear: both;">	
						<a href="list.do" class="btn btn-default">返回</a>
					</div>
				</div>
			</div>
		</div>
	</div>
</div>
</body>
</html>