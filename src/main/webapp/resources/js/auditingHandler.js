/**
 * 欲处理批量审核流程
 * 此js用于判断用户选择的复选框选中的记录的审核状态和信用级别是否一致
 * 如果一致，生成对应的json数据和设置相应的表单数据
 * 否则，弹出相应的信息提示框
 */

//js获取项目根路径，如： http://localhost:8083/uimcardprj
function getRootPath()
{
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

var rightSrc = getRootPath()+"/resources/img/result_right.png";
var errorSrc = getRootPath()+"/resources/img/result_false.png";

//预处理批量审核，判断是否允许批量审核
function preHeadleBatchAuditing(buttonFlag)
{
	var length=0;
	var firstStatus=-1; //第一个选择的状态
	var hasNotSameLeavel = false; //是否存在不相等的等级
	var hasNotSameStatus = false; //是否存在不相等的状态
	var hasValidateStatus = false; //是否存在不满足要求的审核状态(审核通过、审核不通过)
	
	var jsonStr = "[";
	
	//获取全部选中的记录
	$(".ids").each(function(index)
	{
		///包含选中的
		if($(this).attr("checked")=="checked")
		{
			length +=1;
			
			//取得第一条记录的状态和等级
			if(-1 == firstStatus)
			{
				firstStatus = $(this).attr("status");
				jsonStr+="{\"objectId\":\""+$(this).val()+"\",\"stepId\":\""
					      +$(this).attr("stepId")+"\",\"statusId\":\""
					      +firstStatus+"\",\"objectCode\":\""+$(this).attr("objectCode")+"\"},"
			}else 
			{
				//存在2条以上选择记录
				if(firstStatus != $(this).attr("status"))
				{
					hasNotSameStatus = true;
				}
				
				jsonStr+="{\"objectId\":\""+$(this).val()+"\",\"stepId\":\""
						  +$(this).attr("stepId")+"\",\"statusId\":\""+$(this).attr("status")
						  +"\",\"objectCode\":\""+$(this).attr("objectCode")+"\"},"
			}
			
			//当前记录的状态==3（审核通过）
			if(($(this).attr("status") == 3) || ($(this).attr("status") == 4) || ($(this).attr("status") == 1))
			{
				hasValidateStatus = true;
			}
		}
	});
	jsonStr = jsonStr.substring(0,jsonStr.length-1);
	jsonStr += "]";
	if(hasValidateStatus || length==0)
	{
		showAlertMsgModal("请选择需要审核的记录后再执行提交操作");
		return;
	}
	
	if(hasNotSameStatus)
	{
		showAlertMsgModal("请选择相同状态的记录再执行提交操作");
		return;
	}
	
	///给审核表单隐藏域设置值
	$("#buttonFlag").val(buttonFlag);
	$("#jsonStr").val(jsonStr);
	$("#batchAuditingModel").modal("show");
}

/**
 * 显示消息提示框
 * @param {Object} flag 成功或者失败  true-成功  false-失败
 * @param {Object} msg 显示的消息
 */
function showMsgModal(flag,msg)
{
	if(flag)
	{
		//成功之后将审核框中的内容清空
		/*$("#msgModel_img").attr("src",rightSrc);
		$("#msgModel_h").empty().html("成功");*/
		successModal(msg);
	}else
	{
		failModal(msg);
		/*$("#msgModel_img").attr("src",errorSrc);
		$("#msgModel_h").empty().html("失败");*/
	}
	
	//$("#msgModel_p").empty().html(msg);
	//$("#msgModel").modal("show");
}

/**
 * 显示弹出提示框
 * @param {Object} msg 显示的消息
 */
function showAlertMsgModal(msg)
{
//	$("#alertModel_img").attr("src",errorSrc);
//	$("#alertModel_h").empty().html("失败");
//	$("#alertModel_p").empty().html(msg);
//	$("#alertModel").modal("show");
	failModal(msg);
}


$(function()
{
	//注册表单实时验证
	//$('#auditingForm').validate();
	
	//全选
	$("#checkAll").click(function(){
	     $("input[name='ids']").attr("checked",$(this).attr("checked")=="checked"?true:false);
	});
	
	//点击审核通过
	$("#auditpass").on("click",function()
	{
		preHeadleBatchAuditing(1);
	});
	//点击审核通过
	$("#auditnopass").on("click",function()
	{
		preHeadleBatchAuditing(0);
	});
	
	//当审核框隐藏的时候，将审核框中的内容清空
	$("#batchAuditingModel").on("hide",function()
	{
		$("#auditingDes").val("");
		$("label.error").css("display","none");
	});
	
	
	$('#auditingForm').validate({
			submitHandler: function(form){
				form.submit();
			}
		});
});
