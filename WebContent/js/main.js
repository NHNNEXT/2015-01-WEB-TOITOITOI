var data = [];

var replyPath = '/api'+window.location.pathname+'/reply';

function renderDearList (dataList) {
	var dataLen = dataList.length;
	var codes = '';
	for (var i = 0; i < dataLen; i++) {
		codes += '<li>'+dataList[i].content+'<span class="likes"><span class="hidden">좋아요</span> '+dataList[i].likes+'</span></li>';
	}
	document.querySelector('#replies').insertAdjacentHTML('beforeend', codes);
	document.querySelector('.info .replies').textContent = dataLen;

}

function getDearListDone () {
	var httpRequest = new XMLHttpRequest();
	httpRequest.onreadystatechange = function(){
	    if (httpRequest.readyState === XMLHttpRequest.DONE) {
			renderDearList(JSON.parse(httpRequest.response));
	    }
	};
	httpRequest.open('GET', replyPath, true);
	httpRequest.send(null);
}

document.addEventListener("DOMContentLoaded", function() {
	getDearListDone();
});


function updateReplies(reply){
	var code = '<li class="reply">re : '+reply.content+'<span class="likes"><span class="hidden">좋아요</span> '+reply.likes+'</span></li>'
	document.querySelector('#replies').insertAdjacentHTML('afterbegin', code);
	var replieNumElement = document.querySelector('.info .replies');
	var prevReplies = replieNumElement.textContent;
	replieNumElement.textContent = (parseInt(prevReplies)+1);
}

$(document).ready(function() {
	  var form = $('#write-reply'); // contact form
	  var submit = $('#write-reply button');  // submit button

	  // form submit event
	  form.on('submit', function(e) {
	    e.preventDefault(); // prevent default form submit
	    if (!$('#reply-input').val()) {
	    	console.log('입력 좀 해주시죠');
	    	return;
	    }

	    $.ajax({
	      url : replyPath, // form action url
	      type: 'POST', // form submit method get/post
	      dataType: 'json',
	      data: form.serialize(), // serialize form data
	      success: function(data) {
	      	if (!data.success) {
	      		console.error('something went wrong @'+replyPath);
	      		return;
	      	}
	        updateReplies(data.result);
	        form.trigger('reset'); // reset form
	      },
	      error: function(e) {
	        console.log(e)
	      }
	    });
	  });
	});
