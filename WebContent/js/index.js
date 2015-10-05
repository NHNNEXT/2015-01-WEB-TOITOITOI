if ('geolocation' in navigator) {
	navigator.geolocation.getCurrentPosition(
    changePosition.bind(this, 'lowAcc'),
    logError.bind(this, 'lowAcc'),
    {
      timeout : 20000, // ms
      maximumAge : 10800000 // 3hr to ms
    }
  );
	navigator.geolocation.getCurrentPosition(
    changePosition.bind(this, 'highAcc'),
    logError.bind(this, 'highAcc'),
    {
      enableHighAccuracy : true,
      maximumAge : 1800000 // 30min to ms
    }
  );
  var watchID = navigator.geolocation.watchPosition(
    changePosition.bind(this, 'watch'),
    logError.bind(this, 'watch'),
    {
      maximumAge : 120000 // 2min to ms
    }
  );
} else {
  console.log('browser doesn\'t support geolocation');
}

function changePosition (calledFrom, position) {
  console.log(calledFrom, position);
  // ajax 되고 나면 해야지 \^^/
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
      console.error(calledFrom, error.message);
  }
}

function showCafelist(){
	$.ajax({
		type: "get",
		url : "/api/cafelist",
		dataType : 'json',
		contentType: "application/x-www-form-urlencoded; charset=UTF-8",
		success : function(result) {
			console.log(result);
			rendCafelist(result);
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
		var random = Math.ceil(Math.random()*7);
		cafelist.insertAdjacentHTML('beforeend',
		'<li>'+
				'<img src="' + "http://kiboom.github.io/images" + random + ".jpeg" + '">' +
				'<a class = "info" href="/cafe?cid='+cafe.cid+'">'
					+'<span class="name">'+cafe.name+'</span>'
					+'<span class="post-num"><b>POST</b><br>'+cafe.postNum+'개</span>'
					+'<span class="address">'+'성남시 분당구 삼평동'+'</span>'
					//+'<span class="distance">'+'0.3km'+'</span>'+
				+'</a>'+
		'</li>')
	});
}

$('#search-button').click(function(e){
	e.preventDefault();	
	var form = $(".search");
	
	$.ajax({
		type: "get",
		url : "/searchcafe",
		data : form.serialize(), 
		dataType : 'json',
		success : function(result) {
			console.log(result);
			var cafelist = getCafelist(result);
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

function getCafelist(cafes){
	var result = '';

	cafes.forEach(function(cafe){
		result += '<li>'+
				'<img src="http://placehold.it/80x80">'+
				'<a class = "info" href="/cafe?cid='+cafe.cid+'">'
					+'<span class="name">'+cafe.name+'</span>'
					+'<span class="post-num"><b>POST</b><br>'+cafe.postNum+'개</span>'
					+'<span class="address">'+'성남시 분당구 삼평동'+'</span>'
					//+'<span class="distance">'+'0.3km'+'</span>'+
				+'</a>'+
		'</li>'
	});

	console.log(result);
	return result;
}
