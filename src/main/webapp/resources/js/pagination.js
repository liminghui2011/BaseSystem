/** 分页 */
$(document).ready(function() {
	$('.paginationPanel .pagination li').click(function() {
		var $this = $(this);
		if ($this.hasClass('disabled') || $this.hasClass('active')) {
			return false;
		} else {
			var pageNumber = $this.attr('page');
			var pageSize = $this.attr('pageSize');
			var formid = $this.parents('.paginationPanel').attr('form-id');
			var $form = $('#' + formid);
			var url = $form[0].action;
			var separator = url.indexOf("?") > -1?"&":"?";
			url = url + separator + "current=" + pageNumber;
			//$form[0].action=url;
			//$form.submit();
			window.location.href = url;
		}
	});

	$('.paginationPanel .gotoPage').click(function() {
		var $pageSize = $(this).siblings('.pageSize');
		var $pageNumber = $(this).siblings('.pageNumber');
		var _pageSize = $pageSize.val();
		var _pageNumber = $pageNumber.val();
		var pattern = /^[0-9]+$/;
		var formid = $(this).parents('.paginationPanel').attr('form-id');
		var $form = $('#' + formid);
		var forward = true;
		if ($.trim(_pageSize) && pattern.test(_pageSize)) {
			_pageSize = parseInt(_pageSize);
			if (_pageSize < 5) {
				$pageSize.focus();
				alert('请输入大于等于5的整数');
				forward = false;
			} 
		}

		if ($.trim(_pageNumber) && pattern.test(_pageNumber)) {
			_pageNumber = parseInt(_pageNumber);
			if (_pageNumber < 1) {
				$pageNumber.focus();
				alert('请输入大于等于1的整数');
				forward = false;
			}
		}
		
		if($.trim(_pageNumber) == ""){
			return false;
		}

		if (forward) {
			
			
			var url = $form[0].action;
			var separator = url.indexOf("?") > -1?"&":"?";
			url = url + separator + "current=" + _pageNumber;
			
			//
			var goUrl = $("#goUrl").val();
			if(goUrl != ""){
				if(goUrl.indexOf("current") != -1){
					var regex=new RegExp("current=\\d+","g"); 
					goUrl = goUrl.replace(regex,"current="+_pageNumber);
				}else{
					var separator2 = goUrl.indexOf("?") > -1?"&":"?";
					goUrl = goUrl + separator2 + "current="+_pageNumber;
				}
				url = goUrl;
			}
			
			
			//$form[0].action=url;
			//$form.submit();
			window.location.href = url;
		} else {
			return false;
		}
	});
});