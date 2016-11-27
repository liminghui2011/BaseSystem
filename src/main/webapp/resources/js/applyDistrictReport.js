//增值业务和区域报表
//增值业务UV线形图
function AppUVReportLine(reportData,beginDate,endDate){
	  var data = [
		         	{
		         		name : '请求用户数',
		         		value:reportData.requestUv,
		         		color:'#0d8ecf',
		         		line_width:2
		         	},
		         	{
		         		name : '按"0"跳过用户数',
		         		value:reportData.zeroSkipUv,
		         		color:'#ef7707',
		         		line_width:2
		         	},
		         	{
		         		name : '进入用户数',
		         		value:reportData.accessUv,
		         		color:'#89a54e',
		         		line_width:3
		         	}
		         	
		         ];
	        
		var labels = reportData.labels;
		var line = new iChart.LineBasic2D({
			render : 'canvasDiv',
			data: data,
			align:'center',
			title : '增值业务UV统计图',
			width : 800,
			height : 400,
			animation : true,
			tip:{
				enable:true,
				shadow:true
			},
			legend : {
				enable : true,
				row:1,//设置在一行上显示，与column配合使用
				column : 'max',
				valign:'top',
				sign:'bar',
				background_color:null,//设置透明背景
				offsetx:-80,//设置x轴偏移，满足位置需要
				border : true
			},
			crosshair:{
				enable:true,
				line_color:'#62bce9'
			},
			sub_option : {
				point_size:10,
				smooth : true
			},
			coordinate:{
				width:600,
				valid_width:500,
				height:260,
				axis:{
					color:'#9f9f9f',
					width:[0,0,2,2]
				},
				grids:{
					vertical:{
						way:'share_alike',
				 		value:12
					}
				},
				scale:[{
					 position:'left',	
					 start_scale:0,
					 scale_space:10000,
					 scale_size:2,
					 scale_color:'#9f9f9f'
				},{
					 position:'bottom',	
					 labels:labels
				}]
			}
		});
	
	//开始画图
	line.draw();
}

//增值业务PV线形图
function AppPVReportLine(reportData,beginDate,endDate){
	  var data = [
		         	{
		         		name : '请求次数',
		         		value:reportData.requestPv,
		         		color:'#1f7e92',
		         		line_width:2
		         	},
		         	{
		         		name : '按"0"跳过次数',
		         		value:reportData.zeroSkipPv,
		         		color:'#aa4643',
		         		line_width:2
		         	},
		         	{
		         		name : '进入次数',
		         		value:reportData.accessPv,
		         		color:'#89a54e',
		         		line_width:3
		         	}
		         	
		         ];
	        
		var labels = reportData.labels;
		var line = new iChart.LineBasic2D({
			render : 'canvasDiv2',
			data: data,
			align:'center',
			title : '增值业务PV统计图',
			width : 800,
			height : 400,
			animation : true,
			tip:{
				enable:true,
				shadow:true
			},
			legend : {
				enable : true,
				row:1,//设置在一行上显示，与column配合使用
				column : 'max',
				valign:'top',
				sign:'bar',
				background_color:null,//设置透明背景
				offsetx:-80,//设置x轴偏移，满足位置需要
				border : true
			},
			crosshair:{
				enable:true,
				line_color:'#62bce9'
			},
			sub_option : {
				point_size:10,
				smooth : true
			},
			coordinate:{
				width:600,
				valid_width:500,
				height:260,
				axis:{
					color:'#9f9f9f',
					width:[0,0,2,2]
				},
				grids:{
					vertical:{
						way:'share_alike',
				 		value:12
					}
				},
				scale:[{
					 position:'left',	
					 start_scale:0,
					 scale_space:10000,
					 scale_size:2,
					 scale_color:'#9f9f9f'
				},{
					 position:'bottom',	
					 labels:labels
				}]
			}
		});
	
	//开始画图
	line.draw();
}


//区域UV线形图
function DistrictUVReportLine(reportData,beginDate,endDate){
	  var data = [
		         	{
		         		name : '请求用户数',
		         		value:reportData.requestUv,
		         		color:'#0d8ecf',
		         		line_width:2
		         	},
		         	{
		         		name : '按"0"跳过用户数',
		         		value:reportData.zeroSkipUv,
		         		color:'#ef7707',
		         		line_width:2
		         	}//,
//		         	{
//		         		name : '进入用户数',
//		         		value:reportData.accessUv,
//		         		color:'#89a54e',
//		         		line_width:3
//		         	}
		         	
		         ];
	        
		var labels = reportData.labels;
		var line = new iChart.LineBasic2D({
			render : 'canvasDiv',
			data: data,
			align:'center',
			title : '各区域UV统计图',
			width : 800,
			height : 400,
			animation : true,
			tip:{
				enable:true,
				shadow:true
			},
			legend : {
				enable : true,
				row:1,//设置在一行上显示，与column配合使用
				column : 'max',
				valign:'top',
				sign:'bar',
				background_color:null,//设置透明背景
				offsetx:-80,//设置x轴偏移，满足位置需要
				border : true
			},
			crosshair:{
				enable:true,
				line_color:'#62bce9'
			},
			sub_option : {
				point_size:10,
				smooth : true
			},
			coordinate:{
				width:600,
				valid_width:500,
				height:260,
				axis:{
					color:'#9f9f9f',
					width:[0,0,2,2]
				},
				grids:{
					vertical:{
						way:'share_alike',
				 		value:12
					}
				},
				scale:[{
					 position:'left',	
					 start_scale:0,
					 scale_space:10000,
					 scale_size:2,
					 scale_color:'#9f9f9f'
				},{
					 position:'bottom',	
					 labels:labels
				}]
			}
		});
	
	//开始画图
	line.draw();
}


//区域PV线形图
function DistrictPVReportLine(reportData,beginDate,endDate){
	  var data = [
		         	{
		         		name : '请求次数',
		         		value:reportData.requestPv,
		         		color:'#1f7e92',
		         		line_width:2
		         	},
		         	{
		         		name : '按"0"跳过次数',
		         		value:reportData.zeroSkipPv,
		         		color:'#aa4643',
		         		line_width:2
		         	}//,
//		         	{
//		         		name : '进入次数',
//		         		value:reportData.accessPv,
//		         		color:'#89a54e',
//		         		line_width:3
//		         	}
		         	
		         ];
	        
		var labels = reportData.labels;
		var line = new iChart.LineBasic2D({
			render : 'canvasDiv2',
			data: data,
			align:'center',
			title : '各区域PV统计图',
			width : 800,
			height : 400,
			animation : true,
			tip:{
				enable:true,
				shadow:true
			},
			legend : {
				enable : true,
				row:1,//设置在一行上显示，与column配合使用
				column : 'max',
				valign:'top',
				sign:'bar',
				background_color:null,//设置透明背景
				offsetx:-80,//设置x轴偏移，满足位置需要
				border : true
			},
			crosshair:{
				enable:true,
				line_color:'#62bce9'
			},
			sub_option : {
				point_size:10,
				smooth : true
			},
			coordinate:{
				width:600,
				valid_width:500,
				height:260,
				axis:{
					color:'#9f9f9f',
					width:[0,0,2,2]
				},
				grids:{
					vertical:{
						way:'share_alike',
				 		value:12
					}
				},
				scale:[{
					 position:'left',	
					 start_scale:0,
					 scale_space:10000,
					 scale_size:2,
					 scale_color:'#9f9f9f'
				},{
					 position:'bottom',	
					 labels:labels
				}]
			}
		});
	
	//开始画图
	line.draw();
}

//==================================柱形图========================================
//增值业务UV柱形图
function AppUVReportColumn(reportData,beginDate,endDate){
		var data = [
		         	{
		         		name : '请求用户数',
		         		value:reportData.requestUv,
		         		color:'#5287C5',
		         		line_width:2
		         	},
		         	{
		         		name : '按"0"跳过用户数',
		         		value:reportData.zeroSkipUv,
		         		color:'#C85350',
		         		line_width:2
		         	},
		         	{
		         		name : '进入用户数',
		         		value:reportData.accessUv,
		         		color:'#A2C35D',
		         		line_width:2
		         	}
		         	
		         ];
		var labels = reportData.labels;
		var chart = new iChart.ColumnMulti2D({
				render : 'canvasDiv',
				data: data,
				labels:labels,
				title : '增值业务UV统计图',
				width : 800,
				height : 400,
				background_color : '#ffffff',
				animation : true,
				legend : {
					enable : true,
					row:1,//设置在一行上显示，与column配合使用
					column : 'max',
					valign:'top',
					background_color:null,//设置透明背景
					offsetx:-80,//设置x轴偏移，满足位置需要
					border : true
				},
				coordinate:{
					background_color : '#f1f1f1',
					scale:[{
						 position:'left',	
						 start_scale:0,
						 scale_space:10000
					}],
					width:650,
					height:260
				}
		});
		chart.draw();
	}

//增值业务PV柱形图
function AppPVReportColumn(reportData,beginDate,endDate){
	 var data = [
		         	{
		         		name : '请求次数',
		         		value:reportData.requestPv,
		         		color:'#8668A9',
		         		line_width:2
		         	},
		         	{
		         		name : '按"0"跳过次数',
		         		value:reportData.zeroSkipPv,
		         		color:'#4EB3CF',
		         		line_width:2
		         	},
		         	{
		         		name : '进入次数',
		         		value:reportData.accessPv,
		         		color:'#ef7707',
		         		line_width:3
		         	}
		         	
		         ];
	        
		var labels = reportData.labels;
		var chart = new iChart.ColumnMulti2D({
				render : 'canvasDiv2',
				data: data,
				labels:labels,
				title : '增值业务PV统计图',
				width : 800,
				height : 400,
				background_color : '#ffffff',
				animation : true,
				legend : {
					enable : true,
					row:1,//设置在一行上显示，与column配合使用
					column : 'max',
					valign:'top',
					background_color:null,//设置透明背景
					offsetx:-80,//设置x轴偏移，满足位置需要
					border : true
						},
				coordinate:{
					background_color : '#f1f1f1',
					scale:[{
						 position:'left',	
						 start_scale:0,
						 scale_space:10000
					}],
					width:650,
					height:260
				}
		});
		chart.draw();
}


//区域UV柱形图
function DistrictUVReportColumn(reportData,beginDate,endDate){
		 var data = [
		         	{
		         		name : '请求用户数',
		         		value:reportData.requestUv,
		         		color:'#0d8ecf',
		         		line_width:2
		         	},
		         	{
		         		name : '按"0"跳过用户数',
		         		value:reportData.zeroSkipUv,
		         		color:'#ef7707',
		         		line_width:2
		         	}//,
//		         	{
//		         		name : '进入用户数',
//		         		value:reportData.accessUv,
//		         		color:'#89a54e',
//		         		line_width:3
//		         	}
		         	
		         ];
		var labels = reportData.labels;
		var chart = new iChart.ColumnMulti2D({
				render : 'canvasDiv',
				data: data,
				labels:labels,
				title : '各区域UV统计图',
				width : 800,
				height : 400,
				background_color : '#ffffff',
				animation : true,
				legend : {
					enable : true,
					row:1,//设置在一行上显示，与column配合使用
					column : 'max',
					valign:'top',
					background_color:null,//设置透明背景
					offsetx:-80,//设置x轴偏移，满足位置需要
					border : true
				},
				coordinate:{
					background_color : '#f1f1f1',
					scale:[{
						 position:'left',	
						 start_scale:0,
						 scale_space:10000
					}],
					width:650,
					height:260
				}
		});
		chart.draw();
	}

//区域PV柱形图
function DistrictPVReportColumn(reportData,beginDate,endDate){
	var data = [
		         	{
		         		name : '请求次数',
		         		value:reportData.requestPv,
		         		color:'#1f7e92',
		         		line_width:2
		         	},
		         	{
		         		name : '按"0"跳过次数',
		         		value:reportData.zeroSkipPv,
		         		color:'#aa4643',
		         		line_width:2
		         	}//,
//		         	{
//		         		name : '进入次数',
//		         		value:reportData.accessPv,
//		         		color:'#89a54e',
//		         		line_width:3
//		         	}
		         	
		         ];
	        
		var labels = reportData.labels;
		var chart = new iChart.ColumnMulti2D({
				render : 'canvasDiv2',
				data: data,
				labels:labels,
				title : '各区域PV统计图',
				width : 800,
				height : 400,
				background_color : '#ffffff',
				animation : true,
				legend : {
					enable : true,
					row:1,//设置在一行上显示，与column配合使用
					column : 'max',
					valign:'top',
					background_color:null,//设置透明背景
					offsetx:-80,//设置x轴偏移，满足位置需要
					border : true
						},
				coordinate:{
					background_color : '#f1f1f1',
					scale:[{
						 position:'left',	
						 start_scale:0,
						 scale_space:10000
					}],
					width:650,
					height:260
				}
		});
		chart.draw();
	
}






