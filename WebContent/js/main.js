var data = [];
var matches = document.body.matchesSelector || document.body.webkitMatchesSelector || document.body.mozMatchesSelector || document.body.msMatchesSelector || document.body.webkitMatchesSelector || document.body.matchesSelector;

var replyPath = '/api'+window.location.pathname+'/reply';

function getRepliesContent(data) {
	var repliesContent= [];
	for (var i=0 ; i<data.length ; i++) {
		repliesContent.push(data[i].content);
	}
	console.log(repliesContent);
	return repliesContent;
}

function renderDearList (dataList) {
	var dataLen = dataList.length;
	var codes = '';
	for (var i = 0; i < dataLen; i++) {
		codes += '<article class="reply">re : '+dataList[i]+'</article>';
	}
	document.querySelector('#replies').insertAdjacentHTML('beforeend', codes);
}

function getDearListDone () {
	var httpRequest = new XMLHttpRequest();
	httpRequest.onreadystatechange = function(){
	    if (httpRequest.readyState === XMLHttpRequest.DONE) {
			data = JSON.parse(httpRequest.response);
			renderDearList(getRepliesContent(data));
	    }
	};
	httpRequest.open('GET', replyPath, true);
	httpRequest.send(null);
}

document.addEventListener("DOMContentLoaded", function() {
	getDearListDone();
});


function updateReplies(reply){
	console.log(reply);
	var code = '<article class="reply">re : '+reply.content+'</article>'
	document.querySelector('#replies').insertAdjacentHTML('afterbegin', code);
}

$(document).ready(function() {
	  var form = $('#write-reply'); // contact form
	  var submit = $('#write-reply button');  // submit button

	  // form submit event
	  form.on('submit', function(e) {
	    e.preventDefault(); // prevent default form submit

	    $.ajax({
	      url : replyPath, // form action url
	      type: 'POST', // form submit method get/post
	      dataType: 'json',
	      data: form.serialize(), // serialize form data
	      success: function(data) {
	        updateReplies(data);
	        form.trigger('reset'); // reset form
	      },
	      error: function(e) {
	        console.log(e)
	      }
	    });
	  });
	});
