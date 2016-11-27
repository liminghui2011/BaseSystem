/*
Copyright (c) 2003-2012, CKSource - Frederico Knabben. All rights reserved.
For licensing, see LICENSE.html or http://ckeditor.com/license
 */

CKEDITOR.editorConfig = function(config) {
	// Define changes to default configuration here. For example:
	// config.language = 'fr';
	// config.uiColor = '#AADC6E';
	config.language = 'zh-cn'; //编辑器语言
	config.width = 800; //初始化时的宽度

	config.height = 300; //初始化时的高度
	config.skin = 'kama'; //编辑器样式，有三种：’kama’（默认）、’office2003′、’v2′
	config.tabSpaces = 4;
	config.resize_maxWidth = 600; //如果设置了编辑器可以拖拽这是可以移动的最大宽度
	config.toolbarCanCollapse = false; //工具栏可以隐藏
	config.resize_minWidth = 600; //如果设置了编辑器可以拖拽这是可以移动的最小宽度
	config.resize_enabled = false; //如果设置了编辑器可以拖拽
	
	config.font_names = "宋体/宋体;黑体/黑体;仿宋/仿宋_GB2312;楷体/楷体_GB2312;隶书/隶书;幼圆/幼圆;微软雅黑/微软雅黑;" + config.font_names;
	
	config.toolbar = 'TextEdit'; 
	
	//自定义工具栏，主要用于文字的编辑
	config.toolbar_TextEdit = [
			[ 'Bold', 'Italic', 'Underline', 'Strike', '-', 'Subscript',
					'Superscript' ],
			[ 'JustifyLeft', 'JustifyCenter', 'JustifyRight', 'JustifyBlock' ],
			[ 'Styles', 'Format', 'Font', 'FontSize' ],
			[ 'TextColor', 'BGColor' ] 
				];
	
	

	
	config.toolbar_Full = [ 
       ['Source','-','Save','NewPage','Preview','-','Templates'], 
       ['Cut','Copy','Paste','PasteText','PasteFromWord','-','Print', 'SpellChecker', 'Scayt'], 
       ['Undo','Redo','-','Find','Replace','-','SelectAll','RemoveFormat'], 
       ['Form', 'Checkbox', 'Radio', 'TextField', 'Textarea', 'Select', 'Button', 'ImageButton', 'HiddenField'], 
       '/', 
       ['Bold','Italic','Underline','Strike','-','Subscript','Superscript'], 
        ['NumberedList','BulletedList','-','Outdent','Indent','Blockquote'], 
        ['JustifyLeft','JustifyCenter','JustifyRight','JustifyBlock'], 
        ['Link','Unlink','Anchor'], 
       ['Image','Flash','Table','HorizontalRule','Smiley','SpecialChar','PageBreak'], 
       '/', 
        ['Styles','Format','Font','FontSize'], 
        ['TextColor','BGColor'] 
    ]; 



};
