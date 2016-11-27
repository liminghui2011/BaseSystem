/**
 * 标签云生成JS，在需要引入的页面添加这段JS即可
 * tagCloud.js
 */
/**
 * 小图版本
 * <div id='detailContainer' style='width:531px;height:280px;position:absolute;top:0px;left:0px;z-index:1024;background-image: url("${path}/resources/img/training_notice2.png");'>
				<div id='training_subject' style='color: red;margin-top: 55px;margin-left: 244px;font-family: 黑体;font-size: 18px;font-weight: bold;'>
					Android开发培训
				</div>
				<div id='training_teacher' style='margin-left: 220px;margin-top: 7px;font-family: 黑体;font-size: 16px;'>
					2
				</div>
				<div id='training_time' style='margin-left: 220px;margin-top: 7px;font-family: 黑体;font-size: 16px;'>
					3
				</div>
				<div id='training_address' style='margin-left: 220px;margin-top: 8px;font-family: 黑体;font-size: 16px;'>
					4
				</div>
				<div id='followStatus' style='margin-left: 220px;margin-top: 9px;font-family: 黑体;font-size: 16px;'>
					未关注（请点击标签进行关注）
				</div>
				<div class='circle' style='right: 44px;top:22px;background:#eee;width: 70px;height: 70px;line-height:70px;'>
					<img style='border-radius:50em;width: 70px;height: 70px;' id='teacher_photo' src='http://www.onegreen.net/QQ/UploadFiles/201001/2010011923255763.gif'>
				</div>
			</div>
			
	大图版本
	tagContainerHtml += "<div id='detailContainer' style='width:838px;height:438px;position:absolute;top:0px;left:0px;z-index:1024;'>";
		tagContainerHtml += "<div id='training_subject' style='color: red;margin-top: 90px;margin-left: 380px;font-family: 黑体;font-size: 24px;font-weight: bold;'>";
		tagContainerHtml += "</div>";
		tagContainerHtml += "<div id='training_teacher' style='margin-left: 340px;margin-top: 28px;font-family: 黑体;font-size: 20px;'>";
		tagContainerHtml += "</div>";
		tagContainerHtml += "<div id='training_time' style='margin-left: 340px;margin-top: 23px;font-family: 黑体;font-size: 20px;'>";
		tagContainerHtml += "</div>";
		tagContainerHtml += "<div id='training_address' style='margin-left: 340px;margin-top: 22px;font-family: 黑体;font-size: 20px;'>";
		tagContainerHtml += "</div>";
		tagContainerHtml += "<div id='followStatus' style='margin-left: 340px;margin-top: 21px;font-family: 黑体;font-size: 20px;'>";
		tagContainerHtml += "</div>";
		tagContainerHtml += "<div class='circle' style='right: 73px;top:22px;background:#eee;'>";
		tagContainerHtml += "<img style='border-radius:50em;width: 120px;height: 120px;' id='teacher_photo' src=''>";
		tagContainerHtml += "</div>";
		tagContainerHtml += "</div>";
 */

//创建标签云
function createCloud()
{
	var html = "<div id='tagscloud'>";
	
	///随机从数组中获取一个数字，表示标签显示样式
	var angelarray=["1","2","3","4","5"];
	
	$.ajax({
		type:"POST",
		url:getRootPath() + "/trainingRecord/getShowOnTagCloudRecord.do",
		async:false,
		success:function(data)
		{
			var length = data.length;
			for(var i=0; i<length;i++)
			{
				html += "<a href='javascript:void(-1);' title='点击关注或取消关注' courseId='"+data[i].courseId+"' class='tagLink tagc"+angelarray[Math.floor(Math.random()*angelarray.length+1)-1]+"' >"+data[i].courseSubject+"</a>";
			}
		}
	});
	
	html += "</div>";
	
	return html;
}

$(function()
{
	$("body").append(createCloud());
	
	$('.tagLink').tipsy({gravity: 'n'});
	
	$(".tagLink").live("mouseover",function()
	{
		
		var tagContainerHtml = "";
		
		tagContainerHtml += "<div id='detailContainer' style='width:531px;height:280px;position:absolute;top:0px;left:0px;z-index:1024;'>";
		tagContainerHtml += "<div id='training_subject' style='color: red;margin-top: 55px;margin-left: 244px;font-family: 黑体;font-size: 24px;font-weight: bold;'>";
		tagContainerHtml += "</div>";
		tagContainerHtml += "<div id='training_teacher' style='margin-left: 220px;margin-top: 7px;font-family: 黑体;font-size: 16px;'>";
		tagContainerHtml += "</div>";
		tagContainerHtml += "<div id='training_time' style='margin-left: 220px;margin-top: 7px;font-family: 黑体;font-size: 16px;'>";
		tagContainerHtml += "</div>";
		tagContainerHtml += "<div id='training_address' style='margin-left: 220px;margin-top: 8px;font-family: 黑体;font-size: 16px;'>";
		tagContainerHtml += "</div>";
		tagContainerHtml += "<div id='followStatus' style='margin-left: 220px;margin-top: 9px;font-family: 黑体;font-size: 16px;'>";
		tagContainerHtml += "</div>";
		tagContainerHtml += "<div class='circle' style='right: 44px;top:22px;background:#eee;width: 70px;height: 70px;line-height:70px;'>";
		tagContainerHtml += "<img style='border-radius:50em;width: 70px;height: 70px;' id='teacher_photo' src=''>";
		tagContainerHtml += "</div>";
		tagContainerHtml += "</div>";
		
		$("body").append(tagContainerHtml);
		var tagDetailObj = $("#detailContainer");
		
		var top = $(this).offset().top - parseInt(tagDetailObj.height())+parseInt($(this).css("height"))+8;
		var left = $(this).offset().left - parseInt(tagDetailObj.width());
		var backgroudImg = "url('"+getRootPath()+"/resources/img/training_notice2.png')";
		tagDetailObj.css({
			"top":top,
			"left":left,
			"background-image":backgroudImg
		});
		
		//异步查询判断用户是否已经关注课程
		$.ajax({
			url:getRootPath()+"/trainingRecord/getHasFollow.do",
			data:{"courseId":$(this).attr("courseId")},
			async:false,
			success:function(data){
				if("true" == data.result)
				{
					$("#followStatus").html("已关注");
				}else
				{
					$("#followStatus").empty().html("未关注（请点击标签进行关注）");
				}
			}
		});
		
		//异步查询课程详情
		$.ajax({
			url:getRootPath()+"/trainingRecord/getDetailByCourseId.do",
			data:{"courseId":$(this).attr("courseId")},
			async:false,
			success:function(data){
				if("true" == data.result)
				{
					$("#training_subject").empty().html(data.trainingRecordValue.courseSubject);
					$("#training_teacher").empty().html(data.trainingTeacherValue.teacherName);
					$("#training_time").empty().html(data.startTime);
					$("#training_address").empty().html(data.trainingRecordValue.courseAddress);
					$("#teacher_photo").attr("src","/" + data.trainingTeacherValue.teacherPhoto);
					
				}else
				{
					$("#training_subject").empty();
					$("#training_teacher").empty();
					$("#training_time").empty();
					$("#training_address").empty();
					$("#teacher_photo").attr("src","");
					$("#followStatus").empty().html("未关注（请点击标签进行关注）");
				}
			}
			
		});
		tagDetailObj.css("display","block");
	}).live('mouseout',function()
	{
		$("#detailContainer").remove();
	});
	
	//点击标签进行关注和未关注
	$(".tagLink").live("click",function(){
		$.post(getRootPath()+"/trainingRecord/handleFollow.do",{"courseId":$(this).attr("courseId")},function(data)
		{
			//1--关注成功，  2--取消关注成功
			if("true"===data.result && 1===data.msg)
			{
				$("#followStatus").empty().html("已关注");
			}else if("true"===data.result && 2===data.msg)
			{
				$("#followStatus").empty().html("未关注（请点击标签进行关注）");
			}else
			{
				failModal("关注失败");
			}
		});
	});
});