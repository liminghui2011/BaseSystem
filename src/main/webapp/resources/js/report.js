/* 3D柱状图 */
function MyColumn3D(data,id,label,xtitle,w,h,endScale,scaleSpace,ytitle){
	
	var chart = new iChart.ColumnMulti3D({
			render : id,
			data: data,
			labels:label,
			title : {
				text : xtitle,    //说明图表的文字信息
				color : '#3e576f'
			},
			width : w,
			height : h,
			background_color : '#ffffff',
			legend:{
				enable:true,
				background_color : '#EFEFEF',
				align : 'center',
				valign : 'bottom',
				row:1,
				column:'max',
				border : {
					enable : false
				}
			},
			column_width : 8,//柱形宽度
			zScale:5,//z轴深度倍数
			xAngle : 50,
			bottom_scale:1.1,
			label:{
				color:'#4c4f48'
			},			
			tip:{
				enable :true
			},
			text_space : 16,//坐标系下方的label距离坐标系的距离。
			coordinate:{
				background_color : '#EFEFEF',
				grid_color : '#a4a4a2',    
				color_factor : 0.24,
				board_deep:10,
				offsety:-10,
				offsetx:10,
				pedestal_height:10,
				left_board:false,//取消左侧面板
				width:w-80,
				height:h-160,
				scale:[{
					 position:'left',	
					 start_scale:0,
					 end_scale:endScale,
					 scale_space:scaleSpace,
					 scale_enable : false,
					 label:{
						color:'#4c4f48'
					 }
				}]
			}
	});

	//利用自定义组件构造左侧说明文本
	chart.plugin(new iChart.Custom({
			drawFn:function(){
				//计算位置
				var coo = chart.getCoordinate(),
					x = coo.get('originx'),
					y = coo.get('originy');
				//在左上侧的位置，渲染一个单位的文字
				chart.target.textAlign('start')
				.textBaseline('bottom')
				.textFont('600 11px Verdana')
				.fillText(ytitle,x-40,y-28,false,'#6d869f');
				
			}
	}));
	
	chart.draw();
}


/* 曲线图   
	data，xlabel, id, title, h, w, endScale, scaleSpace
*/
function myLineBase2D()
{
	var flow=[];
			for(var i=0;i<25;i++){
				flow.push(Math.floor(Math.random()*(10+((i%16)*5)))+10);
			}
			
			var data = [
			         	{
			         		name : 'PV',
			         		value:flow,
			         		color:'#ec4646',
			         		line_width:2
			         	}
			         ];
	        
			var labels = ["00","01","02","03","04","05","06","07","08","09","10","11","12","13","14","15","16","17","18","19","20","21","22","23","24"];
			
			var chart = new iChart.LineBasic2D({
				render : 'canvasDiv3',
				data: data,
				align:'center',
				title : {
					text:'ichartjs官方网站上一日PV流量',
					font : '微软雅黑',
					fontsize:24,
					color:'#b4b4b4'
				},
				width : 800,
				height : 400,
				background_color:'#ffffff',
				tip:{
					enable:true,
					shadow:true,
					listeners:{
						 //tip:提示框对象、name:数据名称、value:数据值、text:当前文本、i:数据点的索引
						parseText:function(tip,name,value,text,i){
							return "<span style='color:#005268;font-size:14px;'>"+labels[i]+"点:"+
							"</span><span style='color:#005268;font-size:16px;'>"+value+"万</span>";
						}
					}
				},
				crosshair:{
					enable:true,
					line_color:'#EA2525'
				},

				coordinate:{
					width:640,
					height:260,
					striped_factor : 0.18,
					grid_color:'#BCBCBC',
					axis:{
						color:'#C7C5C5',
						width:[0,0,2,2]
					},
					scale:[{
						 position:'left',	
						 start_scale:0,
						 end_scale:100,
						 scale_space:10,						
						 scale_enable : false,
						 label : {color:'#9d987a',font : '微软雅黑',fontsize:12,fontweight:600},
						 scale_color:'#9f9f9f'
					},{
						 position:'bottom',	
						 label : {color:'#9d987a',font : '微软雅黑',fontsize:12,fontweight:600},
						 scale_enable : false,
						 labels:labels
					}]
				}
			});
			//利用自定义组件构造左侧说明文本
			chart.plugin(new iChart.Custom({
					drawFn:function(){
						//计算位置
						var coo = chart.getCoordinate(),
							x = coo.get('originx'),
							y = coo.get('originy'),
							w = coo.width,
							h = coo.height;
						//在左上侧的位置，渲染一个单位的文字
						chart.target.textAlign('start')
						.textBaseline('bottom')
						.textFont('600 11px 微软雅黑')
						.fillText('访问量(万)',x-40,y-12,false,'#9d987a')
						.textBaseline('top')
						.fillText('(时间)',x+w+12,y+h+10,false,'#9d987a');
						
					}
			}));
		//开始画图
		chart.draw();
}

//2014-05-21
//每天各时段报表
function everydayRequestReport(reportData,requestDate){
	  var data = [
		         	{
		         		name : '请求次数',
		         		value:reportData.requestPv,
		         		color:'#0d8ecf',
		         		line_width:2
		         	},
		         	{
		         		name : '请求用户数',
		         		value:reportData.requestUv,
		         		color:'#ef7707',
		         		line_width:2
		         	}
		         ];
	        
		var labels = reportData.labels;
		var line = new iChart.LineBasic2D({
			render : 'canvasDiv',
			data: data,
			align:'center',
			title : requestDate+'日各时段开机请求数统计图',
			width : 1000,
			height : 600,
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
				label:false,
				hollow_inside:false,
				point_size:12,
				smooth : true
			},
			coordinate:{
				width:800,
				height:400,
				axis:{
					color:'#9f9f9f',
					width:[0,0,2,2]
				},
				grids:{
					vertical:{
						way:'share_alike',
				 		value:5
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



//平台各业务分组请求用户数报表
function platformGroupReportUV(reportData,requestDate){
	var chart = new iChart.Donut2D({
		render : 'canvasDiv',
		data: reportData.uvList,
		title : {
			text : requestDate+'日各业务分组开机请求用户数计图',
			color : '#3e576f'
		},
		sub_option : {
			label : {
				background_color:null,
				sign:false,//设置禁用label的小图标
				padding:'0 4',
				border:{
					enable:false,
					color:'#666666'
				},
				fontsize:11,
				fontweight:600,
				color : '#4572a7'
			},
			border : {
				width : 2,
				color : '#ffffff'
			},
			listeners:{
				parseText:function(d,t){
					return t + ","+(d.get('value')*100/d.root.total).toFixed(2)+"%";
				}
			}
		},
		shadow : true,
		shadow_blur : 6,
		shadow_color : '#aaaaaa',
		shadow_offsetx : 0,
		shadow_offsety : 0,
		background_color:'#fefefe',
		offset_angle:-120,//逆时针偏移120度
		showpercent:false,
		decimalsnum:2,
		width : 800,
		height : 400,
		radius:120,
		animation:true
	});
	
	chart.draw();
}

//平台各业务分组请求数报表
function platformGroupReportPV(reportData,requestDate){
	var chart = new iChart.Donut2D({
		render : 'canvasDiv2',
		data: reportData.pvList,
		title : {
			text : requestDate+'日各业务分组开机请求次数统计图',
			color : '#3e576f'
		},
		sub_option : {
			label : {
				background_color:null,
				sign:false,//设置禁用label的小图标
				padding:'0 4',
				border:{
					enable:false,
					color:'#666666'
				},
				fontsize:11,
				fontweight:600,
				color : '#4572a7'
			},
			border : {
				width : 2,
				color : '#ffffff'
			},
			listeners:{
				parseText:function(d,t){
					return t + ","+(d.get('value')*100/d.root.total).toFixed(2)+"%";
				}
			}
		},
		shadow : true,
		shadow_blur : 6,
		shadow_color : '#aaaaaa',
		shadow_offsetx : 0,
		shadow_offsety : 0,
		background_color:'#fefefe',
		offset_angle:-120,//逆时针偏移120度
		showpercent:false,
		decimalsnum:2,
		width : 800,
		height : 400,
		radius:120,
		animation:true
	});
	
	chart.draw();
}


//平台请求用户数报表
function platformReportUV(reportData,requestDate){
	var chart = new iChart.Donut2D({
		render : 'canvasDiv',
		data: reportData.uvList,
		title : {
			text : requestDate+'日各平台开机请求用户数计图',
			color : '#3e576f'
		},
		sub_option : {
			label : {
				background_color:null,
				sign:false,//设置禁用label的小图标
				padding:'0 4',
				border:{
					enable:false,
					color:'#666666'
				},
				fontsize:11,
				fontweight:600,
				color : '#4572a7'
			},
			border : {
				width : 2,
				color : '#ffffff'
			},
			listeners:{
				parseText:function(d,t){
					return d.get('terraceName') + " " + d.get('value') + ","+(d.get('value')*100/d.root.total).toFixed(2)+"%";
				}
			}
		},
		shadow : true,
		shadow_blur : 6,
		shadow_color : '#aaaaaa',
		shadow_offsetx : 0,
		shadow_offsety : 0,
		background_color:'#fefefe',
		offset_angle:-120,//逆时针偏移120度
		showpercent:false,
		decimalsnum:2,
		width : 800,
		height : 400,
		radius:120,
		animation:true
	});
	
	chart.draw();
}

//平台各业务分组请求数报表
function platformReportPV(reportData,requestDate){
	var chart = new iChart.Donut2D({
		render : 'canvasDiv2',
		data: reportData.pvList,
		title : {
			text : requestDate+'日各平台开机请求次数统计图',
			color : '#3e576f'
		},
		sub_option : {
			label : {
				background_color:null,
				sign:false,//设置禁用label的小图标
				padding:'0 4',
				border:{
					enable:false,
					color:'#666666'
				},
				fontsize:11,
				fontweight:600,
				color : '#4572a7'
			},
			border : {
				width : 2,
				color : '#ffffff'
			},
			listeners:{
				parseText:function(d,t){
					return d.get('terraceName') + " " + d.get('value') + ","+(d.get('value')*100/d.root.total).toFixed(2)+"%";
				}
			}
		},
		shadow : true,
		shadow_blur : 6,
		shadow_color : '#aaaaaa',
		shadow_offsetx : 0,
		shadow_offsety : 0,
		background_color:'#fefefe',
		offset_angle:-120,//逆时针偏移120度
		showpercent:false,
		decimalsnum:2,
		width : 800,
		height : 400,
		radius:120,
		animation:true
	});
	
	chart.draw();
}


//各地市请求用户数统计图
function districtRequestReportUV(data,requestDate)
{
		var chart = new iChart.Pie2D({
		render : 'canvasDiv',
		data: data.uvList,
		title : {
			text : requestDate+'日各地市开机请求用户数统计图',
			color : '#3e576f'
		},
	
		sub_option : {
			label : {
				background_color:null,
				sign:false,//设置禁用label的小图标
				padding:'0 4',
				border:{
					enable:false,
					color:'#666666'
				},
				fontsize:11,
				fontweight:600,
				color : '#4572a7'
			},
			border : {
				width : 2,
				color : '#ffffff'
			},
			listeners:{
				parseText:function(d,t){
					return t + "," + (d.get('value')*100/d.root.total).toFixed(2)+"%";
				}
			}
		},
		shadow : true,
		shadow_blur : 6,
		shadow_color : '#aaaaaa',
		shadow_offsetx : 0,
		shadow_offsety : 0,
		background_color:'#fefefe',
		offsetx:-60,//设置向x轴负方向偏移位置60px
		offset_angle:-120,//逆时针偏移120度
		showpercent:true,
		decimalsnum:2,
		width : 800,
		height : 400,
		radius:120,
		animation:true,
		showpercent:false
						
	});
	chart.draw();
}



//各地市请求次数统计图
function districtRequestReportPV(data,requestDate)
{
		var chart = new iChart.Pie2D({
		render : 'canvasDiv2',
		data: data.pvList,
		title : {
			text : requestDate+'日各地市开机请求次数统计图',
			color : '#3e576f'
		},
	
		sub_option : {
			label : {
				background_color:null,
				sign:false,//设置禁用label的小图标
				padding:'0 4',
				border:{
					enable:false,
					color:'#666666'
				},
				fontsize:11,
				fontweight:600,
				color : '#4572a7'
			},
			border : {
				width : 2,
				color : '#ffffff'
			},
			listeners:{
				parseText:function(d,t){
					return t + "," + (d.get('value')*100/d.root.total).toFixed(2)+"%";
				}
			}
		},
		shadow : true,
		shadow_blur : 6,
		shadow_color : '#aaaaaa',
		shadow_offsetx : 0,
		shadow_offsety : 0,
		background_color:'#fefefe',
		offsetx:-60,//设置向x轴负方向偏移位置60px
		offset_angle:-120,//逆时针偏移120度
		showpercent:true,
		decimalsnum:2,
		width : 800,
		height : 400,
		radius:120,
		animation:true,
		showpercent:false
						
	});
	chart.draw();
}


