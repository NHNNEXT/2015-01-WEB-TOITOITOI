if ('geolocation' in navigator) {
	navigator.geolocation.getCurrentPosition(
    changePosition.bind(this, 'lowAcc'),
    logError.bind(this, 'lowAcc'),
    {
      timeout : 20000, // ms
      maximumAge : 10800000 // 3hr to ms
    }
  );
} else {
	var error = {
		'message' : 'browser doesn\'t support geolocation'
	};
	logError.call(this, 'no geo');
}

function changePosition (calledFrom, position) {
  console.log(calledFrom, position);
	showCafelist({
		'lat' : position.coords.latitude,
		'long' : position.coords.longitude
	});
}

function logError (calledFrom, error) {
  switch (error.code) {
    case error.PERMISSION_DENIED:
    // 한 번 block하고 나면 다시 안 물어본다는 게 함정 ㅜㅜ
      break;
    case error.TIMEOUT:
      break;
    case error.POSITION_UNAVAILABLE:
     case error.UNKNOWN_ERROR:
      debugger;
      break;
    default:
			showCafelist();
      console.error(calledFrom, error.message);
  }
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

showCafelist();

function rendCafelist(cafes){
	var cafelist = document.querySelector(".cafe-list");

	cafes.forEach(function(cafe){
		cafelist.insertAdjacentHTML('beforeend',
		'<li>'+
				'<img src="http://placehold.it/80x80">'+
				'<a class = "info" href="/cafe?cid='+cafe.cid+'">'
					+'<span class="name">'+cafe.name+'</span>'
					+'<span class="post-num"><b>POST</b><br>'+cafe.postNum+'개</span>'
					+'<span class="address">'+'성남시 분당구 삼평동'+'</span>'
					+'<span class="distance">'+'0.3km'+'</span>'+
				'</a>'+
		'</li>')
	});
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
