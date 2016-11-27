(function($){
	$.fn.pc = function(config){
		var pathname = location.pathname;
		var secondSlashIndex = pathname.substring(1).indexOf('/');
		var contextPath = pathname.substring(0, secondSlashIndex+2);
		
		//var $province = $('.'+config.province);
		var $city = $('.'+config.city);
		var defaultProvince = config.defaultProvince;
		var defaultCity = config.defaultCity;
		//alert(defaultProvince+"***"+defaultCity);
		
		/**$.getJSON(contextPath+'resources/js/json-array-of-province.json', function(data){
			$.each(data.province, function(i, entry){
				var s = defaultProvince==entry.code?'selected="selected"':'';
				$province.append('<option value="'+entry.code+'" '+s+'>'+entry.name+'</option>')
			});
		});*/
		
		function loadCity(province){
			var pcode = province.substring(0,2);
			$.getJSON(contextPath+'resources/js/json-array-of-city.json', function(data){
				//$city.find('option').not(':first').remove();
				//$city.append('<option value="" >'+请选择+'</option>')
				$.each(data.city, function(i, entry){
					var city = entry.code;
					var ccode = city.substring(0,2);
					if(pcode===ccode){
						var s = defaultCity==entry.code?'selected="selected"':'';
						$city.append('<option value="'+entry.code+'" '+s+'>'+entry.name+'</option>')
					}
				});
			});
		}
		
		if(defaultProvince){
			loadCity(defaultProvince);
		}
		
/**  	$province.change(function(){
			defaultCity = '';
			loadCity($(this).val());
		});*/
		
	}
})(jQuery);