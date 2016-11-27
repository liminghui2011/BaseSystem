<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../common/common_tags.jsp" %>
<!DOCTYPE html>
<html lang="en">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>维护菜单树</title>
</head>
<body>
<link rel="stylesheet" type="text/css" href="<w:path/>resources/css/zTreeStyle/zTreeStyle.css"/>
<script type="text/javascript" src="<w:path/>resources/js/jquery.ztree.core-3.4.min.js"></script>
<script type="text/javascript" src="<w:path/>resources/js/jquery.ztree.exedit-3.4.min.js"></script>
<style type="text/css">
.ztree *{
	font-size: 14px;
}
</style>

<div id="accordion" class="accordion">
	<div class="accordion-group">
		<div class="accordion-heading">
			<div class="title">系统管理 > 菜单管理 > 维护菜单树</div>
		</div>
		<div id="addAccordion" class="accordion-body in">
			<div class="accordion-inner">
				<div class="row-fluid">
					<div class="span5"><ul id="ztree" class="ztree"></ul></div>
					<div class="span7">
						<form action="addMenu.do" id="addMenuForm" class="form-horizontal">
							<div class="control-group">
								<label class="control-label" for="treeName2">菜单树名</label>
								<div class="controls">
									<input type="text" name="treename" id="treeName2" value="${tree.name}" disabled="disabled" maxlength="20"/>
									<span class="help-inline"></span>
								</div>
							</div>
							<div class="control-group">
								<label class="control-label" for="description2">菜单树描述</label>
								<div class="controls">
									<input type="text" name="description" id="description2" value="${tree.description}" maxlength="128"/>
									<span class="help-inline"></span>
									<input type="button" class="btn btn-primary" value="修改" onclick="updateTreeDescription(${tree.id}, this)"/>
								</div>
							</div>
							<div class="control-group">
								<label class="control-label" for="name">菜单名</label>
								<div class="controls">
									<input type="text" name="name" id="name" maxlength="20"/>
									<span class="help-inline"></span>
								</div>
							</div>
							<div class="control-group">
								<label class="control-label" for="uri">链接地址</label>
								<div class="controls">
									<input type="text" name="uri" id="uri" maxlength="256" class="w90"/>
									<span class="help-inline"></span>
								</div>
							</div>
							<div class="form-actions">
								<input type="hidden" id="pid" value="${tree.id}" name="parent.id"/>
								<a href="#" id="btnAddMenu" onclick="$('#addMenuForm').submit()" class="btn btn-big btn-primary">添加</a>
								<a href="${returnURI}" class="btn btn-big btn-gray">返回</a>
							</div>
						</form>
					</div>
				</div>
			</div>
		</div>
	</div>
</div>

<div id="addMenuModal" class="modal hide fade" style="width: 600px;">
	<div class="modal-header">
	  <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
	  <div class="modal-title">添加菜单</div>
 	</div>
	<div class="modal-body">
	  <form action="addMenu.do" id="addMenuForm2" class="form-horizontal">
	  	<div class="control-group">
	  		<label class="control-label">菜单名</label>
	  		<div class="controls">
	  			<input type="text" name="name" maxlength="20"/>
	  			<span class="help-inline"></span>
	  		</div>
	  	</div>
	  	<div class="control-group">
	  		<label class="control-label">链接地址</label>
	  		<div class="controls">
	  			<input type="text" name="uri" class="w90" maxlength="256"/>
	  			<span class="help-inline"></span>
	  			<input type="hidden" name="parent.id" id="parentId"/>
	  		</div>
	  	</div>
	  </form>
	</div>
    <div class="modal-footer">
      <a href="#" class="btn btn-big btn-primary" id="btnAddMenu2" onclick="$('#addMenuForm2').submit()">提交</a>
      <a href="#" class="btn btn-big btn-gray" data-dismiss="modal">取消</a>
    </div>
</div>

<div id="updateMenuModal" class="modal hide fade" style="width:600px;">
	<div class="modal-header">
	  <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
	  <div class="modal-title">修改菜单</div>
 	</div>
	<div class="modal-body">
	  <form id="updateMenuForm" class="form-horizontal">
	  	<div class="control-group">
	  		<label class="control-label">菜单名</label>
	  		<div class="controls">
	  			<input type="text" name="name" maxlength="20"/>
	  			<span class="help-inline"></span>
	  		</div>
	  	</div>
	  	<div class="control-group">
	  		<label class="control-label">链接地址</label>
	  		<div class="controls">
	  			<input type="text" name="uri" style="width:90%" maxlength="256"/>
	  			<span class="help-inline"></span>
	  			<input type="hidden" name="id"/>
	  			<input type="hidden" name="parent.id"/>
	  		</div>
	  	</div>
	  	
	  </form>
	</div>
    <div class="modal-footer">
      <a href="#" class="btn btn-big btn-primary" id="btnUpdateMenu" onclick="$('#updateMenuForm').submit()">提交</a>
      <a href="#" class="btn btn-big btn-gray" data-dismiss="modal">取消</a>
    </div>
</div>

<div id="tips" class="modal hide fade" style="width: 350px;">
	<div class="modal-header">
	  <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
	  <div class="modal-title">提示框</div>
 	</div>
	<div class="modal-body" align="center">
	  <p>存在相同的菜单名称!</p>
	</div>
    <div class="modal-footer">
      <a href="#" class="btn btn-big btn-gray" data-dismiss="modal">确定</a>
    </div>
</div>

<script type="text/javascript">
var setting = {
	view: {
		addHoverDom: addHoverDom,
		removeHoverDom: removeHoverDom,
		selectedMulti: false
	},
	edit: {
			drag: {
				prev: dropPrev,
				next: dropNext,
				inner: dropInner
			}, 
		enable: true,
		editNameSelectAll: false
	},
	data: {
		simpleData: {
			enable: true
		}
	},
	callback: {
		beforeRemove: beforeRemove,
		beforeEditName: beforeEditName,
		onDrop: onDrop
	}
};

var treeNodes = ${treeNodes};
var treeObj;

//添加菜单之前
function addHoverDom(treeId, treeNode) {
	var sObj = $("#" + treeNode.tId + "_span");
	if (treeNode.editNameFlag || $("#addBtn_"+treeNode.id).length>0) return;
	var addStr = "<span class='button add' id='addBtn_" + treeNode.id
		+ "' title='add node' onfocus='this.blur();'></span>";
	sObj.after(addStr);
	sObj.parent().attr('title', treeNode.title);
	sObj.parent().append('<span class="url">'+treeNode.url+'</span>');
	var btn = $("#addBtn_"+treeNode.id);
	if (btn) btn.bind("click", function(){
		$('#addMenuModal').modal();
		$('#addMenuModal form')[0].reset();
		$('#addMenuModal').data('treeObj', treeObj);
		$('#addMenuModal').data('parentNode', treeNode);
		$('#parentId').val(treeNode.id);
		return false;
	});
};

//删除菜单项
function removeHoverDom(treeId, treeNode) {
	$("#addBtn_"+treeNode.id).next('span.url').remove();
	$("#addBtn_"+treeNode.id).unbind().remove();
};

//往上移动
function dropPrev(treeId, treeNodes, targetNode){
	//console.log('dropPrev');
	if(targetNode.pId==0){
		return false;
	}
	return true;
}

//往下移动
function dropNext(treeId, treeNodes, targetNode){
	//console.log('dropNext');
	if(targetNode.pId==0){
		return false;
	}
	return true;
}

function dropInner(treeId, treeNodes, targetNode){
	/*
	console.log('dropInner');
	if(targetNode){
		console.log(targetNode.name);
	}
	*/
	if(!targetNode){
		return false;
	}
	return true;
}

//删除菜单项
function beforeRemove(treeId, treeNode){
	if(treeNode.pId==0 || !treeNode.pId){
		return false;
	}
	var info = $('#ztree').find('a:first').find('span:eq(1)').text();
	$.ajax({
		type:'post',
		url:'remove.do',
		data:'id='+treeNode.id,
		beforeSend: function(){
			$('#ztree').find('a:first').find('span:eq(1)').text('处理中...');
		},
		complete: function(){
			$('#ztree').find('a:first').find('span:eq(1)').text(info);
		},
		error: function(jqXHR, textStatus, errorThrown){
			alert(jqXHR.responseText);
		}
	});
	return true;
} 

//更新菜单项之前
function beforeEditName(treeId, treeNode) {
	if(treeNode.pId==0 || !treeNode.pId){
		return false;
	}
	
	$.ajax({
		type:'post',
		url:'get.do',
		dataType:'json',
		data:'id='+treeNode.id,
		success: function(data, textStatus, jqXHR){
			$('#updateMenuModal').find('input[name=name]').val(data.name);
			$('#updateMenuModal').find('input[name=uri]').val(data.uri);
			$('#updateMenuModal').find('input[name=id]').val(data.id);
			$('#updateMenuModal').find('input[name="parent.id"]').val(data.pid);
			var style = data.style;
			$('#updateMenuModal').find('input[name=style][value="'+style+'"]').attr('checked', 'checked');
			$('#updateMenuModal').data('node', treeNode);
			$('#updateMenuModal').modal();
		},
		error: function(jqXHR){
			alert(jqXHR.responseText);
		}
	});
	return false;
}
//鼠标松开时
function onDrop(event, treeId, treeNodes, targetNode, moveType, isCopy) {
	var node = treeNodes[0];
	var id = node.id;
	var prevId,nextId,parentId;
	var prev = node.getPreNode();
	var next = node.getNextNode();
	
	if(moveType){
		if(moveType=='prev'){
			nextId = targetNode.id;
			parentId = node.getParentNode().id;
			if(prev){
				prevId = prev.id;
			}
		}else if(moveType=='next'){
			prevId = targetNode.id;
			parentId = node.getParentNode().id;
			if(next){
				nextId = next.id;
			}
		}else if(moveType=='inner'){
			parentId = targetNode.id;
		}
		
		//console.log(moveType+','+prevId+','+id+','+nextId+','+parentId);
		var info = $('#ztree').find('a:first').find('span:eq(1)').text();
		$.ajax({
			type:'post',
			url:'move.do',
			data:{'id':id,'pid':parentId,'nextId':nextId,'prevId':prevId},
			beforeSend: function(){
				$('#ztree').find('a:first').find('span:eq(1)').text('处理中...');
			},
			complete: function(){
				$('#ztree').find('a:first').find('span:eq(1)').text(info);
			},
			error: function(jqXHR){
				alert(jqXHR.responseText);
			}
		});
	}
}

jQuery.validator.addMethod("treeName", function(value, element) { 
  return /^[a-zA-Z0-9_]+$/.test(value); 
});

jQuery.validator.addMethod("menuUri", function(value, element) { 
  return /^[a-zA-Z0-9\?\\\/\+\.=&\-\_:]*$/.test(value); 
});

$('#addMenuForm').validate({
	rules:{
		name:'required',
		uri:'menuUri'
	},
	messages:{
		name:'<spring:message code="menu.nodename.required"/>',
		uri:'<spring:message code="menu.uri.invalid"/>'
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
  	},*/
  	submitHandler: function(form){
  		$.ajax({
  			type:'post',
  			url:$(form).attr('action'),
  			data:$(form).serialize(),
  			success:function(data, textStatus, jqXHR){
  				var id = data;
  				var name = $(form).find('input[name=name]').val();
  				var uri = $(form).find('input[name=uri]').val();
  				var pid = $('#pid').val();
  				var node = {id:id, pId:pid, name:name, url:uri};
  				treeObj.addNodes(treeObj.getNodes()[0], node);
  				//console.dir(treeObj.getNodes());
  			},
  			error: function(jqXHR, textStatus, errorThrown){
  				alert(jqXHR.responseText);
  			},
  			beforeSend: function(){
  				$('#btnAddMenu').text('处理中...');
  			},
  			complete: function(){
  				$('#btnAddMenu').text('添加');
  			}
  		});
  	}
});

$('#addMenuForm2').validate({
	rules:{
		name:'required',
		uri:'menuUri'
	},
	messages:{
		name:'<spring:message code="menu.nodename.required"/>',
		uri:'<spring:message code="menu.uri.invalid"/>'
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
  	},*/
  	submitHandler: function(form){
  		$.ajax({
  			type:'post',
  			url:$(form).attr('action'),
  			data:$(form).serialize(),
  			success:function(data, textStatus, jqXHR){
  				var id = data;
  				var name = $(form).find('input[name=name]').val();
  				var uri = $(form).find('input[name=uri]').val();
  				var pid = $('#parentId').val();
  				var node = {id:id, pId:pid, name:name, url:uri};
  				var parentNode = $('#addMenuModal').data('parentNode');
  				treeObj.addNodes(parentNode, node);
  				$('#addMenuModal').modal('hide');
  			},
  			error: function(jqXHR, textStatus, errorThrown){
  				$('#tips').modal();
  				//alert(jqXHR.responseText);
  			},
  			beforeSend: function(){
  				$('#btnAddMenu2').text('处理中...');
  			},
  			complete: function(){
  				$('#btnAddMenu2').text('添加');
  			}
  		});
  	}
});

$('#updateMenuForm').validate({
	rules:{
		name:'required',
		uri:'menuUri'
	},
	messages:{
		name:'<spring:message code="menu.nodename.required"/>',
		uri:'<spring:message code="menu.uri.invalid"/>'
	},
	onfocusout: function(element) {
        $(element).valid();
    },
    errorElement: 'span',
    errorPlacement: function(error, element) {
    	element.next().empty();
    	error.appendTo(element.next());
	},
    /*errorClass: 'help-block',
   	highlight: function(element, errorClass) {
    	$(element).parents('.control-group').addClass('error');
  	},
  	unhighlight: function(element, errorClass, validClass) {
    	$(element).parents('.control-group').removeClass('error');
  	},*/
  	submitHandler: function(form){
  		$.ajax({
  			type:'post',
  			url:'updateMenu.do',
  			data:$(form).serialize(),
  			success:function(data, textStatus, jqXHR){
  				var node = $('#updateMenuModal').data('node');
  				node.name = data.name;
  				node.url = data.uri;
  				treeObj.updateNode(node);
  				//treeObj.expandNode(node, 'open'==data.style);
  				$('#updateMenuModal').modal('hide');
  			},
  			beforeSend: function(){
  				$('#btnUpdateMenu').text('处理中...');
  			},
  			complete: function(){
  				$('#btnUpdateMenu').text('提交');
  			},
  			error:function(jqXHR){
  				$('#tips').modal();
  			}
  		});
  	}
});

function updateTreeDescription(id, ele){
	$.ajax({
		type:'post',
		url:'updateDescription.do',
		data:{id:id, description:$('#description2').val()},
		beforeSend: function(){
			$(ele).val('处理中...');
		},
		error: function(jqXHR){
			alert(jqXHR.responseText);
		},
		complete: function(){
			$(ele).val('修改');
		}
	});
}

jQuery(document).ready(function(){
	$.fn.zTree.init($("#ztree"), setting, treeNodes);
	treeObj = $.fn.zTree.getZTreeObj('ztree');
});
</script>
</body>
</html>