var data = [];

var replyPath = '/api'+window.location.pathname+'/reply';
var postId = document.querySelector('#write-reply input[type="hidden"]').value;
var postLikePath = '/api/post/'+postId+'/like';

function renderDearList (dataList) {
	var dataLen = dataList.length;
	var codes = '';
	for (var i = 0; i < dataLen; i++) {
		var currentReply = dataList[i];
		codes += '<li data-id="'+currentReply.id+'">'+currentReply.content+'<span class="likes"><span class="hidden">좋아요</span>'+currentReply.likes+'</span></li>';
	}
	document.querySelector('#replies').insertAdjacentHTML('beforeend', codes);
	increase(document.querySelector('.info .replies'), dataLen, true);
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
	var code = '<li data-id="'+reply.id+'">'+reply.content+'<span class="likes"><span class="hidden">좋아요</span> '+reply.likes+'</span></li>'
	document.querySelector('#replies').insertAdjacentHTML('afterbegin', code);
	increase(document.querySelector('.info .replies'), 1, false);
}

function increase (element, value, isOverwrite) {
	element.textContent = (isOverwrite)? value : (parseInt(element.textContent) + value);
}

$(document).ready(function() {
	var attachment = new Image();
	attachment.onload = function (e) {
		var parentElement = document.querySelector('section.the-letter');
		parentElement.insertBefore(this, parentElement.lastChild.nextSibling);
	};
	attachment.src = '/api/post/'+postId+'/file';

	$('.info .likes').on('click.like', function (e) {
		var target = this;
		$.ajax({
			url : postLikePath, // form action url
			success: function(data) {
				if (!data.success) {
					console.error('something went wrong @'+postLikePath);
					return;
				}
				addClass(target, 'on');
				$(target).off('click.like');
				increase(target, data.result.likes, true);
			},
			error: function(e) {
				console.log(e);
				debugger;
			}
		});
	});
	$('#replies').on('click.like', '.likes:not(".on")', function (e) {
		var target = this;
		var id = target.closest('li').dataset.id;
		var requestPath = '/api/reply/'+id+'/like';
		$.ajax({
			url : requestPath,
			success: function(data) {
				if (!data.success) {
					console.error('something went wrong @'+requestPath);
					return;
				}
				addClass(target, 'on');
				increase(target, data.result.likes, true);
			},
			error: function(e) {
				console.log(e)
			}
		});
	});

	function dealMessage (isSuccess, messageHTML, targetElement) {
		var messageBar = document.querySelector('#write-reply .message');
		messageHTML = messageHTML || '무언가 잘못되었어요!';
		((isSuccess)? removeClass : addClass)(messageBar, 'fail');
		messageBar.innerHTML = messageHTML;
		//TODO: after showing message, focus on targetElement. targetElement might be empty.
		//TODO: 2초 뒤에 사라지게(fadeIn/Out)는 나중에.
	}

	  var form = $('#write-reply'); // contact form
	  var submit = $('#write-reply button');  // submit button

	  // form submit event
	  form.on('submit', function(e) {
	    e.preventDefault();
	    if (!$('#reply-input').val()) {
	    	dealMessage( false, '댓글 내용을 입력해주세요.', this.querySelector('#reply-input') );
	    	return;
	    }

	    $.ajax({
	      url : replyPath, // form action url
	      type: 'POST', // form submit method get/post
	      dataType: 'json',
	      data: form.serialize(), // serialize form data
	      success: function(received) {
	      	if (!received.success) {
	      		dealMessage( false, received.errorMessage );
	      		return;
	      	}

	        dealMessage( true, '댓글이 등록되었습니다.' );
	        updateReplies(received.result);
	        form.trigger('reset'); // reset form
	      },
	      error: function(e) {
	      	debugger;
	        dealMessage( false, e );
	      }
	    });
	  });
	});
