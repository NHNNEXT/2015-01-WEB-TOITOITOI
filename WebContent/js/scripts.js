var posting_style = document.querySelector('.posting').style;
var posting_textbox_style = document.querySelector('.posting .textbox').style;
var posting_textboxbg_style = document.querySelector('.posting .textbox-bg').style;
var posting_send_style = document.querySelector('.posting .send').style;

var posting = document.querySelector('.posting');
var posting_top = posting.offsetTop;
var topBar = document.querySelector('.top-bar');
var topBar_height = topBar.offsetHeight;

function getPostList() {
	var cidURL = window.location.search;
	var posts;

	$.ajax({
	      url: '/api/postlist'+cidURL,
	      type: 'GET',
	      success: function(data) {
	        renderPosts(data);
	      },
	      error: function(e) {
	        console.log(e)
	      }
	});

};

function getReplyList(pid) {
	var replies;
	$.ajax({
		url: '/api/replylist' + '?pid=' + pid,
		type: 'GET',
		success: function(data) {
			replies = data.replies;
			renderReplies(pid, data);
			return data;
		},
		error: function(e) {
			console.log(e);
			replies = null;
		}
	});
	return replies;
}


function renderPosts(posts) {
	var post_ul = document.querySelector(".posts");

	posts.forEach(function(post){
		post_ul.insertAdjacentHTML('beforeend',
			'<li class="post" data-key='+post.pid+'>' +
				'<img class="quatation-up" src="http://i58.tinypic.com/30holtz.png">' +
				'<div class="contents">' + post.contents + '</div>' +
				'<img class="quatation-down" src="http://i59.tinypic.com/dr46mw.png">' +
				'<div class="info">' +
					'<span class="like-post" data-key=' + post.pid + ' name="likesOnPost" action="/likedOnPost">' + post.liked + '</span>' +
					'<span class="replies-post">' + 0 + '</span>' +
					'<div class="time">' + post.creattime.split(" ")[0] + '</div>' +
				'</div>' +
				'<ul class="replies"></ul>' +
				'<div class="replyBox">' +
					'<form action="/createReply" method="post">' +
						'<input class="textbox" name="content" type="text" placeholder=" re: 댓글 달기...">' +
						'<input name="pid" type="hidden" value="' + post.pid + '">' +
						'<input name="cid" type="hidden" value="' + post.cid + '">' +

						'<button class="send">게시</button>' +
					'</form>' +
				'</div>' +
			'</li>'
		);
		var replies = getReplyList(post.pid);
		//renderReplies(post.pid, replies);
	});
}

function renderReplies(pid, replies){
	var post = document.querySelector('li[data-key="'+pid+'"]');
	var reply_ul = post.querySelector(".replies");
	var replies_post = post.querySelector(".replies-post");

	replies_post.textContent = replies.length;

	replies.forEach(function(reply){
		reply_ul.insertAdjacentHTML('beforeend',
				'<li class="reply">' +
					'<div class="reply-content">re: ' + reply.replyContent + '</div>' +
					'<div class="like-reply" value="' + reply.reId + '" name ="likesOnReply" action="/likedOnReply" method= "post" >' + reply.liked + '</div>' +
				'</li>'
		);
	});

}

getPostList();

var posting_textbox = document.querySelector(".posting .textbox");

$('.posts').on('click', '.like-reply', function(e) {
	// todo
	// if(!on)
	// +1 .addclass(on)
	// ajax(like) (reid, status = plus)
	// else
	// -1. removeclass(on)
	// ajax(unlike) (reid, status = minus)

	var reid = $(this).attr("value");
	var datas = {
		'reid' : reid,
		'status' : 'plus'
	};
	var div = $(this);

	if (!$(this).hasClass('on')) {
		$(this).addClass('on');
		var likes = parseInt($(this).text()) + 1;
		$(this).html(likes);
	}
	else {
		$(this).removeClass('on');
		var likes = parseInt($(this).text()) - 1;
		$(this).html(likes);
		datas = {
				'reid' : reid,
				'status' : 'minus'
		};
	}
	console.log(datas);
	$.ajax({
		type : "POST",
		url : "/likedOnReply",
		data : datas,
		success : function(data) {
			console.log("success");
			// div.html(data);
		},
		error : function(xhr, status, error) {
			console.log(status);
			// console.log(error);
		}
	});
});

  // form submit event

function updateReplies(reply, replyList, replyCount){
	replyList.insertAdjacentHTML('beforeend',
	"<li class='reply'>" +
		"<div class='reply-content'> re: " + reply.replyContent + "</div>" +
		"<div class='like-reply' value='" + reply.reId + "' name ='likesOnReply' action='/likedOnReply' method= 'post' >" + reply.liked + "</div>" +
	"</li>");
	replyCount.textContent = parseInt(replyCount.textContent)+1;
}

$('.posts').on('submit', '.replyBox form', function(e) {
	e.preventDefault(); // prevent default form submit
	var form = $(this);
	var replyList = $(this).parent().siblings('.replies');
	var replyCount = replyList.siblings('.info').find('.replies-post');
	console.log(replyCount);

	$.ajax({
		url: '/createReply', // form action url
		type: 'POST', // form submit method get/post
		dataType: 'json',
		data: $(this).serialize(), // serialize form data
		success: function(reply) {
			form.trigger('reset'); // reset form
			console.log(reply);
			updateReplies(reply, replyList[0], replyCount[0]);
		},
		error: function(e) {
			console.log(e)
		}
	});
});


function updatePosts(post, postList){
	console.log(post);
	postList.insertAdjacentHTML('afterbegin',
		'<li class="post" data-key='+post.pid+'>' +
			'<img class="quatation-up" src="http://i58.tinypic.com/30holtz.png">' +
			'<div class="contents">' + post.contents + '</div>' +
			'<img class="quatation-down" src="http://i59.tinypic.com/dr46mw.png">' +
			'<div class="info">' +
				'<span class="like-post" value=' + post.pid + ' name="likesOnPost" action="/likedOnPost">' + post.liked + '</span>' +
				'<span class="replies-post">' + 0 + '</span>' +
				'<div class="time">' + post.creattime.split(" ")[0] + '</div>' +
			'</div>' +
			'<ul class="replies"></ul>' +
			'<div class="replyBox">' +
				'<form action="/createReply" method="post">' +
					'<input class="textbox" name="content" type="text" placeholder=" re: 댓글 달기...">' +
					'<input name="pid" type="hidden" value="' + post.pid + '">' +
					'<button class="send">게시</button>' +
				'</form>' +
			'</div>' +
		'</li>'
	);
}


$(document).ready(function() {
	  var form = $('.posting-form'); // contact form
	  var submit = $('.posting .send');  // submit button
	  var postList = $('.posts');

	  // form submit event
	  form.on('submit', function(e) {
	    e.preventDefault(); // prevent default form submit

	    $.ajax({
	      url: '/createpost', // form action url
	      type: 'POST', // form submit method get/post
	      dataType: 'json',
	      data: form.serialize(), // serialize form data
	      success: function(data) {
	    	console.log(postList);
	        updatePosts(data, postList[0]);
	        form.trigger('reset'); // reset form
	      },
	      error: function(e) {
	        console.log(e)
	      }
	    });
	  });
	});

$('.posts').on('click', '.like-post', function(e) {
	// todo
	// if(!on)
	// +1 .addclass(on)
	// ajax(like) (reid, status = plus)
	// else
	// -1. removeclass(on)
	// ajax(unlike) (reid, status = minus)

	var pid = $(this).attr("data-key");
	console.log(pid);

	var datas = {
			'pid' : pid,
			'status' : 'plus'
	};
	var div = $(this);

	if (!$(this).hasClass('on')) {
		$(this).addClass('on');
		var likes = parseInt($(this).text()) + 1;
		$(this).html(likes);
	}
	else {
		$(this).removeClass('on');
		var likes = parseInt($(this).text()) - 1;
		$(this).html(likes);
		datas.status = 'minus';
	}
	console.log(datas);
	$.ajax({
		type : "POST",
		url : "/likedOnPost",
		data : datas,
		success : function(data) {
			console.log("success");
			// div.html(data);
		},
		error : function(xhr, status, error) {
			console.log(status);
			// console.log(error);
		}
	});
});

var nudges = [];
var nudgeIndex = 0;

var cid = $('input[name="cid"]').attr('value');
$.get('/nudge/'+cid, function(data) {
	console.log(data);
	for (var i = 0; i < data.length; i++) {
		nudges.push(data[i].contents);
	}
});

function changeNudge () {
	var nextNudge = (nudges.length)? nudges[(nudgeIndex++)%nudges.length] : '이 카페 아메리카노 어때요?';
	$('.posting .textbox').attr('placeholder', nextNudge);
}
function setTimeOutNudge () {
	changeNudge();
	window.setTimeout(setTimeOutNudge, 5000);
}
window.setTimeout(setTimeOutNudge, 10);

$('.posting input').on('focus', function (e) {
	if (!$('.posting').hasClass('glue')) {
		return;
	}
	$('.posting').addClass('focus');
	var prevScrollTop = $(window).scrollTop();
	$(window).scrollTop(prevScrollTop);
	var toBeChangedElement = [$('.top-bar'), $('.posting')];
	var changedArray = toBeChangedElement.map(function (el, index, array) {
		var prevOption = {
			'position' : 'fixed',
			'top' : $(el).position().top
		};
		$(el).css({
			'position' : 'absolute',
			'top' : $(el).offset().top
		});
		return {
			'element':el,
			'prevOption': prevOption
		};
	});

	$(window).on('scroll.topBar', function () {
		toBeChangedElement.forEach(function (el, index, array) {
			$(el).css({
				'top' : $(el).offset().top + $(window).scrollTop() - prevScrollTop
			});
		});
		prevScrollTop = $(window).scrollTop();
	});

	$('.posting input').on('blur', function () {
		$('.posting').removeClass('focus');
		changedArray.forEach(function (item, index) {
			var $element = $(item.element);
			$element.attr('style', '');
		});
		$(window).off('scroll.topBar');
		$('.posting input').off('blur');
	});
});


function dealScroll() {
	if (scrollY > (posting_top - topBar_height)) {
		$(posting).addClass('glue');
	}
	if (scrollY < (posting_top - topBar_height)) {
		$(posting).removeClass('glue');
	}
}
// 스크롤 가운데서 새로고침 또는 뒤로 돌아왔을 때, 캐싱된 스크롤 위치에 있게 됨.
$(document).ready(function () {
	dealScroll();
});
$(window).on("scroll touchmove", dealScroll);
