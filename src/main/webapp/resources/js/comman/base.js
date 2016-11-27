var url = '';
var checkIds = '';
$(function(){
	
	/**
	 * 全选功能
	 * heqiuying 2014-3-25 新建
	 */
	$("#checkAll").click(function(){
		$("[name='checkItem']:checkbox").attr("checked",this.checked);
	});
	var checkboxlen = $(":checkbox[name='checkItem']").length;
	$(":checkbox[name='checkItem']").click(function(){
		$("#checkAll").attr("checked",checkboxlen==$("[name='checkItem']:checked").length);
	});
	//全选功能结束
	
	/**
	 * 批量删除
	 */
	$("a[target='delete']").click(function(event){
		event.preventDefault();
		url = $(this).attr("href");
		if(getKeyList()){
		$("#batchdelModel").modal();
		}
	});
	
	
	/**
	 * 批量提交
	 */
	$("a[target='save']").click(function(event){
		event.preventDefault();
		url = $(this).attr("href");
		if(getKeyList()){
			url = url.replace("{checkIds}", checkIds);
			document.location.href = url;
		}
	});
	
	
	/**
	 *弹出编辑框
	 */
	$("a[target='dialog']").click(function(event){
		event.preventDefault();
		url = $(this).attr("href");
		if(getKeyList()){
			url = url.replace("{checkIds}", checkIds);
			$.get(url,function(data){
				$("#dialog").html(data);
				$("#dialog").modal();
			});
		}
	});
	
	/**
	 * 单个编辑弹出框
	 */
	$("a[target='dialogOne']").click(function(event){
		event.preventDefault();
		url = $(this).attr("href");
		var width =  $(this).attr("width");
		var height = $(this).attr("height");
		if(width!=undefined&&width!=''){
			$("#dialog").css("width",width);
		  }
		if(height!=undefined&&height!=''){
			$("#dialog").css("height",height);
		  }
		
			$.get(url,function(data){
				$("#dialog").html(data);
				$("#dialog").modal();
			});
	});
	
	
	
	
	$("a[target='deleteOne']").click(function(event){
		event.preventDefault();
		url = $(this).attr("href");
		$(".delModel").modal();
	});
	
	
	/**
	 * 批量提交审核
	 */
	$("a[target='submitAudit']").click(function(event){
		event.preventDefault();
		var submitFlag = true;
		url = $(this).attr("href");
		checkIds = '';
		$("[name='checkItem']:checked").each(function(){
			if($(this).prev(".statusId").text()!='1'){
				submitFlag =false;
				return false;
			}
			if(checkIds!=''){
				checkIds=checkIds+","+$(this).val();
			}
			else{
				checkIds = $(this).val();
			}
		});
		
		if(!submitFlag){
			alertModal("请选择待提交状态的记录");
			return false;
		}
		
		if(checkIds==''){
			alertModal("请选择需要提交审核的记录");
			return false;
		}
		
		url = url.replace("{checkIds}", checkIds);
		document.location.href = url;
	});
	
	/**
	 * 批量提交审核
	 */
	$("a[target='audit']").click(function(event){
		event.preventDefault();
		var submitFlag = true;
		url = $(this).attr("href");
		checkIds = '';
		$("[name='checkItem']:checked").each(function(){
			if($(this).prev(".statusId").text()!='2'&&$(this).prev(".statusId").text()!='9'){
				submitFlag =false;
				return false;
			}
			if(checkIds!=''){
				checkIds=checkIds+","+$(this).val();
			}
			else{
				checkIds = $(this).val();
			}
		});
		
		if(!submitFlag){
			alertModal("请选择待待审核或者待复审状态的记录");
			return false;
		}
		
		if(checkIds==''){
			alertModal("请选择需要审核的记录");
			return false;
		}
		
		url = url.replace("{checkIds}", checkIds);
		$.get(url,function(data){
			$("#dialog").html(data);
			$("#dialog").modal();
		});
	});
	
	/**
	 * 批量审核经验
	 */
	$("a[target='auditExperience']").click(function(event){
		event.preventDefault();
		var submitFlag = true;
		url = $(this).attr("href");
		checkIds = '';
		$("[name='checkItem']:checked").each(function(){
			if(checkIds!=''){
				checkIds=checkIds+","+$(this).val();
			}
			else{
				checkIds = $(this).val();
			}
		});
		
		if(checkIds==''){
			alertModal("请选择需要审核的记录");
			return false;
		}
		
		url = url.replace("{checkIds}", checkIds);
		if(getKeyList()){
			document.location.href = url;
		}
	});
	
});


function deleteOne(){
	document.location.href =  url;
}

function batchDelete(){
	$("#batchDeleteModal").modal('hide');
	url = url.replace("{checkIds}", checkIds);
	document.location.href = url;
	
}

function getKeyList(){
	checkIds = '';
	$("[name='checkItem']:checked").each(function(){
		if(checkIds!=''){
			checkIds=checkIds+","+$(this).val();
		}
		else{
			checkIds = $(this).val();
		}
	});
	if(checkIds.length==''){
		$("#batchdelOperater").modal();
		return false;
	}
	return true;
}

function alertModal(msg){
	 $("#alertModel_p").empty().append(msg);
	 $("#alertModel").modal('show');
}

function successModal(msg){
	$("#msgModel_p").empty().append(msg);
	$("#msgModel").modal('show');
	
}

function failModal(msg){
	$("#msgModel_error_p").empty().append(msg);
	$("#msgModel_error").modal('show');
	
}

//js获取项目根路径，如： http://localhost:8083/uimcardprj
function getRootPath(){
   
	//获取当前网址，如： http://localhost:8083/uimcardprj/share/meun.jsp
    var curWwwPath=window.document.location.href;
    
    //获取主机地址之后的目录，如： uimcardprj/share/meun.jsp
    var pathName=window.document.location.pathname;
    var pos=curWwwPath.indexOf(pathName);
   
    //获取主机地址，如： http://localhost:8083
    var localhostPath=curWwwPath.substring(0,pos);
    
    //获取带"/"的项目名，如：/uimcardprj
    var projectName=pathName.substring(0,pathName.substr(1).indexOf('/')+1);
    return(localhostPath+projectName);
}

//js获取项目路径，如： http://localhost:8083
function getLocalPath()
{
	//获取当前网址，如： http://localhost:8083/uimcardprj/share/meun.jsp
    var curWwwPath=window.document.location.href;
    
    //获取主机地址之后的目录，如： uimcardprj/share/meun.jsp
    var pathName=window.document.location.pathname;
    var pos=curWwwPath.indexOf(pathName);
   
    //获取主机地址，如： http://localhost:8083
    var localhostPath=curWwwPath.substring(0,pos);
    return localhostPath;
}

//去掉css尺寸后面的px
function subCssPX(obj)
{
	return obj.substring(0,obj.length-2);
}


