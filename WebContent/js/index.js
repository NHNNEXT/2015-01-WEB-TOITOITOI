if ('geolocation' in navigator) {
	navigator.geolocation.getCurrentPosition(
    changePosition.bind(this, 'lowAcc'),
    handleError.bind(this, 'lowAcc'),
    {
      timeout : 20000, // ms
      maximumAge : 10800000 // 3hr to ms
    }
  );
} else {
	var error = {
		'message' : 'browser doesn\'t support geolocation'
	};
	handleError.call(this, 'no geo');
}

function changePosition (calledFrom, position) {
  $('#test').text('success :'+calledFrom+', '+position.coords.latitude);
  console.log('success', calledFrom, position);
	showCafelist({
		'lat' : position.coords.latitude,
		'long' : position.coords.longitude
	});
}

function handleError (calledFrom, error) {
  switch (error.code) {
    case error.PERMISSION_DENIED:
			$('#test').text('위치 정보 접근 권한을 허용해주세요. 더 가까운 카페를 보여드립니다.');
			$('#test').show();
      break;
    case error.TIMEOUT:
    case error.POSITION_UNAVAILABLE:
	  case error.UNKNOWN_ERROR:
    default:
			$('#test').text('error :'+calledFrom+', '+error.message);
			debugger;
  }
	showCafelist({'sort':'postNum'});
  console.error('error', calledFrom, error.message);
}

function showCafelist(param){
	console.log('param', param);
	$.ajax({
		type: "get",
		url : "/api/cafelist",
		data : (param) ? param : '',
		success : function(result) {
			console.log(result);
			var cafelist = compileCafeList(result);
			$(".cafe-list").empty();
			console.log(cafelist);
			$(".cafe-list").append(cafelist);
		},
		error : function(xhr, status, error) {
			console.log(status)
		}
	})
}

$('.search').on('submit',function(e){
	e.preventDefault();
	var form = $(".search");

	$.ajax({
		type: "get",
		url : "/searchcafe",
		data : form.serialize(),
		dataType : 'json',
		success : function(result) {
			console.log(result);
			var cafelist = compileCafeList(result);
			$(".cafe-list").empty();
			console.log(cafelist);
			$(".cafe-list").append(cafelist);
			//rendSearchedCafelist(result);
		},
		error : function(xhr, status, error) {
			console.log(status)
		}
	});

});

function compileCafeList(cafes){
	var result = '';

	cafes.forEach(function(cafe){
		result += '<li>'+
				'<img src="http://kiboom.github.io/images'+Math.ceil(Math.random()*7)+'.jpeg">'+
				'<a class = "info" href="/cafe?cid='+cafe.cid+'">'
					+'<span class="name">'+cafe.name+'</span>'
					+'<span class="post-num"><b>POST</b><br>'+cafe.postNum+'개</span>'
					+'<span class="address">'+'성남시 분당구 삼평동'+'</span>';
		result += ('distance' in cafe) ? '<span class="distance">'+parseFloat(cafe.distance).toFixed(3)+'km'+'</span>' : '';
		result +=	'</a>'+'</li>';
	});

	console.log(result);
	return result;
}
