<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../common/common_tags.jsp" %>
<!DOCTYPE html>
<html lang="en">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>查询日志</title>
</head>
<body>
<div id="accordion" class="accordion">
	<div class="accordion-group">
		<div class="accordion-heading">
			<div class="title">系统管理 > 日志管理 > 日志列表</div>
		</div>
		<div id="logAccordion" class="accordion-body in">
			<div id="addAccordion" class="accordion-inner">
				<div style="border: 0px solid red; height: 33px;padding-right: 30px;">
				<form:form action="list.do" method="post" modelAttribute="log" cssClass="form-inline">
					&nbsp;&nbsp;&nbsp;&nbsp;
					<label for="from">查询日期&nbsp;
						<form:input id="from" path="from" cssClass="input-medium Wdate" onclick="WdatePicker({maxDate:'#F{$dp.$D(\\'to\\')}'})" readonly="true"/>
					</label>&nbsp;
					<label for="to">至
						<form:input id="to" path="to" cssClass="input-medium Wdate" onclick="WdatePicker({minDate:'#F{$dp.$D(\\'from\\')}'});" readonly="true"/>
					</label>&nbsp;
					
					<div class="btn-group">
						<form:select path="type" cssStyle="width:100px;">
							<c:forEach items="${logType}" var="item">
								<form:option value="${item.key}">${item.value}</form:option>
							</c:forEach>
						</form:select>		                
	                </div>&nbsp;
					<label for="content">
						<form:input path="content"/>
					</label>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
	                <input type="button" id="search" class="btn btn-primary" value="搜索" style="width: 54px;" onclick="checkSubmit();">
				</form:form>
				</div>
				<table class="table table-hover table-condensed">
					<thead>
						<tr >
							<th>账号名</th>
							<th>账号角色</th>
							<th>登录IP</th>
							<th>操作时间</th>
							<th>操作事件</th>
							<th width="10%">操作</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach items="${pb.dataList}" var="log">
							<tr>
								<td>${log.actor}</td>
								<td>${log.roleName}</td>
								<td>${log.loginIp}</td>
								<td><fmt:formatDate value="${log.addDate}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
								<td>${log.content}</td>
								<td>
									<a href="<w:path/>system/log/detail.do?id=${log.id}" class="detail" target="dialogOne" width="800px">详细</a>
								</td>
							</tr>
						</c:forEach>
						<tr>
							<td colspan="6" form-id="log" class="paginationPanel"><ltPage:page pageBean="${pb}" /></td>
						</tr>
					</tbody>
				</table>
			</div>
		</div>
	</div>
</div>


<script type="text/javascript">

function doSearch(en,cn){
	$('#type').val(en);
	$('#typeCN').val(cn);
	$('#log').submit();
}

//判断日期选择框是否输入
function checkSubmit()
{
	var fromDate = $("#from").val();
	var toDate = $("#to").val();
	if((fromDate != "" && toDate != "") || (fromDate == "" && toDate == ""))
	{
		$('#log').submit();
	}
	else
	{
		alertModal("请选择完整的查询日期范围！！！");
	}
}
</script>
</body>
</html>