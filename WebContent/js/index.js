var data = {};
// var example = {
// 	"뷰티토이" : [
// 		{"postId":6,"dear":"뷰티토이","content":"슈테판이 간단한 사용법을 알려주었는데 , 일단은 SAP 언어셋팅을 영어로 바꾸는 것부터 해","createdtime":"2015-12-13 14:07:26.0","placeId":0,"likes":0},
// 		{"postId":7,"dear":"뷰티토이","content":"해외에서 한국인 혹은 한국과 조금이라도 관련된 일을 하게 된다면 반갑지 않은 일을 만나게","createdtime":"2015-12-13 14:07:26.0","placeId":0,"likes":0}
// 	], "깃토이" : [
// 		{"postId":9,"dear":"깃토이","content":"잘 되나요?","createdtime":"2015-12-13 14:48:02.0","placeId":0,"likes":0},
// 		{"postId":4,"dear":"깃토이","content":"깃 마스터 짱짱","createdtime":"2015-12-13 14:07:26.0","placeId":0,"likes":0},
// 		{"postId":5,"dear":"깃토이","content":"프론트 대장님","createdtime":"2015-12-13 14:07:26.0","placeId":0,"likes":0}
// 	], "toitoi" : [
// 		{"postId":1,"dear":"toitoi","content":"깃토이, 뷰티토이, 플토이,소토이","createdtime":"2015-12-13 14:07:26.0","placeId":0,"likes":0},
// 		{"postId":2,"dear":"toitoi","content":"윙가르디움 레비오우사","createdtime":"2015-12-13 14:07:26.0","placeId":0,"likes":0},
// 		{"postId":3,"dear":"toitoi","content":"해리포터","createdtime":"2015-12-13 14:07:26.0","placeId":0,"likes":0}
// 	]
// };
var matches = document.body.matchesSelector || document.body.webkitMatchesSelector || document.body.mozMatchesSelector || document.body.msMatchesSelector || document.body.webkitMatchesSelector || document.body.matchesSelector;


function renderDearList (dataList) {
	var dataLen = dataList.length;
	var codes = '';
	for (var i = 0; i < dataLen; i++) {
		codes += '<article><h3>'+dataList[i]+'</h3></article>';
	}
	document.querySelector('#letters').insertAdjacentHTML('beforeend', codes);
}

function hasClass (el, name, reg) {
	if (!el) { return false; }
	if (!reg) reg = new RegExp('(\\s|^)'+name+'(\\s|$)');
	return el.className.match(reg);
}
function addClass (el, name) {
	if (!el) { return; }
	if (!this.hasClass(el, name)) el.className += " "+name;
}
function removeClass (el, name, reg) {
	if (!el) { return; }
	if (!reg) reg = new RegExp('(\\s|^)'+name+'(\\s|$)');
	el.className = el.className.replace(reg, " ").trim();
}
function toggleClass (el, name) {
	if (!el) { return; }
	var reg = new RegExp('(\\s|^)'+name+'(\\s|$)');
	(hasClass(el, name, reg) ? removeClass : addClass)(el, name, reg);
}

function addPreview (dataList, header) {
	if (!dataList || !header || !dataList.length) {
		return;
	}

	var dataLen = dataList.length;
	var codes = '<ul>';
	for (var i = 0; i < dataLen; i++) {
		var data = dataList[i];
		codes += '<li><a href="'+window.location.pathname+'/dear/'+data.dear+'/post/'+data.postId+'">'+data.preview+'</a></li>';
	}
	codes += '</ul>';
	header.insertAdjacentHTML('afterend', codes);
}

function findPreview (data, dear) {
	var posts;
	if (!data || !(posts = data[dear]) || !posts.length) {
		return null;
	}
	var result = [];
	for (var i = 0; i < posts.length; i++) {
		var post = posts[i];
		result.push({'dear':post.dear, 'preview':post.content, 'postId':post.postId});
	}
	return result;
}

function cloneElement (el) {
	return JSON.parse(JSON.stringify(el));
}

function getDearListDone () {
	var httpRequest = new XMLHttpRequest();
	httpRequest.onreadystatechange = function(){
	    if (httpRequest.readyState === XMLHttpRequest.DONE) {
			data = JSON.parse(httpRequest.response);
			renderDearList(Object.keys(data));
	    }
	};
	httpRequest.open('GET', '/api/place/1/dear?page=1', true);
	httpRequest.send(null);
}

document.addEventListener("DOMContentLoaded", function() {
	getDearListDone();

	document.querySelector('#letters').addEventListener('click', function (e) {
		var header = e.target;
		if (!header.matches('h3')) {
			if (header.matches('article')) {
				header = header.querySelector('h3');
				console.log(header);
			} else {
				return;
			}
		}
		var prevOn = this.querySelector('.on');
		removeClass(prevOn, 'on');
		if (header === prevOn) { return; }

		addClass(header, 'on');
		var list = header.parentNode.querySelector('ul');
		if (!list) {
			var dear = header.textContent;
			var previews = findPreview(data, dear);
			addPreview(previews, header);
		}
	});
});


function updateReplies(reply){
	console.log(reply);
	var code = '<article class="reply">re : '+reply.content+'</article>'
	document.querySelector('#replies').insertAdjacentHTML('afterbegin', code);
}

$(document).ready(function() {
	  var form = $('#write-letter'); // contact form
	  var submit = $('#write-letter button');  // submit button

	  // form submit event
	  form.on('submit', function(e) {
	    e.preventDefault(); // prevent default form submit

	    $.ajax({
	      url : '/api/place/1/post', // form action url
	      type: 'POST', // form submit method get/post
	      dataType: 'x-www-form-urlencoded',
	      data: form.serialize(), // serialize form data
	      success: function(data) {
	        //updateReplies(data);
	        form.trigger('reset'); // reset form
	      },
	      error: function(e) {
	        console.log(e)
	      }
	    });
	  });
	});