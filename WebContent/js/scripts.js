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
	      url: '/api/postlist' + cidURL,
	      type: 'GET',
	      success: function(data) {
	    	data;
	    	posts = data.posts;
	        console.log(posts);
	        renderPosts(posts);
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
			console.log(replies);
			renderReplies(pid, replies);
			return replies;
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
					'<span class="like-post" value=' + post.pid + ' name="likesOnPost" action="/likedOnPost">' + post.liked + '</span>' +
					'<span class="replies-post">' + 0 + '</span>' +
					'<div class="time">' + post.creattime.split(" ")[0] + '</div>' +
				'</div>' +
				'<ul class="replies"></ul>' +
				'<div class="replyBox">' +
					'<form action="/createReply" method="post">' +
						'<input class="textbox" name="reply" type="text" placeholder=" re: 댓글 달기...">' +
						'<input name="pid" type="hidden" value="' + post.pid + '">' +
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


function dealScroll() {
	if (scrollY > (posting_top - topBar_height)) {
		$(posting).addClass('glue');
	}
	if (scrollY < (posting_top - topBar_height)) {
		$(posting).removeClass('glue');
	}
}
// 스크롤 가운데서 새로고침 또는 뒤로 돌아왔을 때, 캐싱된 스크롤 위치에 있게 됨.
dealScroll();
window.addEventListener("scroll", dealScroll, false);

var posting_textbox = document.querySelector(".posting .textbox");

posting_textbox.addEventListener('focus', function(event) {
	/* input박스가 스크롤해서 쪼그라들었을 때만 클릭시 이벤트 적용! */
	if (scrollY > 176){
		posting_style.height = "65px";
		posting_textbox_style.height = '50px';
		posting_textbox_style.lineHeight = '50px';
		posting_textbox_style.fontSize = '4.5vmin';
		posting_textboxbg_style.height = '65px';
		posting_send_style.right = '5.4%';
		posting_send_style.height = '50px';
		posting_textboxbg_style.borderBottom = '1px solid rgba(234, 202, 190, 0.15)';
	}
}, false);

$(".like-reply").click(function() {
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

$(document).ready(function() {
	  var form = $('.posting-form'); // contact form
	  var submit = $('.posting .send');  // submit button

	  // form submit event
	  form.on('submit', function(e) {
	    e.preventDefault(); // prevent default form submit

	    $.ajax({
	      url: '/createpost', // form action url
	      type: 'POST', // form submit method get/post
	      dataType: 'html',
	      data: form.serialize(), // serialize form data
	      success: function(data) {
	        updatePosts();
	        form.trigger('reset'); // reset form
	      },
	      error: function(e) {
	        console.log(e)
	      }
	    });
	  });
	});

function updatePosts(){
	var posts = document.querySelector(".posts")
	var firstPost = posts.firstElementChild;

	var newPost = firstPost.cloneNode(true);
	var postContents = document.querySelector(".posting .textbox");
	debugger;
	newPost.querySelector(".contents").textContent = postContents.value;

	var reply = newPost.querySelector(".reply");
	while(reply){
		reply.parentNode.removeChild(reply);
		reply = newPost.querySelector(".reply");
	}

	var firstPostFloat = firstPost.style.float;
	if (firstPostFloat=="right" || firstPostFloat==""){
		newPost.style.float = "left";
	} else {
		newPost.style.float = "right";
	}

	if(posts.firstChild){
		posts.insertBefore(newPost, posts.firstChild);
	} else {
		posts.appendChild(newPost);
	}
}

$(document).ready(function() {
	  var form = $('.replyBox form'); // contact form
	  var submit = $('.replyBox .send');  // submit button

	  // form submit event
	  form.on('submit', function(e) {
	    e.preventDefault(); // prevent default form submit

	    $.ajax({
	      url: '/createReply', // form action url
	      type: 'POST', // form submit method get/post
	      dataType: 'html',
	      data: form.serialize(), // serialize form data
	      success: function(data) {
	        form.trigger('reset'); // reset form
	        console.log(e);
	        updateReplies(e);
	      },
	      error: function(e) {
	        console.log(e)
	      }
	    });
	  });
	});

function updateReplies(replyButton){
	var replies = document.querySelector(".replies")
	var firstReply = replies.firstElementChild;

	var replyContents = replyButton.parentNode.querySelector(".textbox");
	replies.insertAdjacentHTML('beforeend',
	"<li class='reply'>" +
			"<div class='reply-content'> re: " + replyContents.value + "</div>" +
			"<div class='like-reply' value='${reply.reId}' name ='likesOnReply' action='/likedOnReply' method= 'post' >1</div>" +
	"</li>");
}

$(".like-post").click(function() {
	// todo
	// if(!on)
	// +1 .addclass(on)
	// ajax(like) (reid, status = plus)
	// else
	// -1. removeclass(on)
	// ajax(unlike) (reid, status = minus)

	var pid = $(this).attr("value");
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
$.get('/nudge?cid='+cid, function(data) {
	parsedData = JSON.parse(data);
	for (var i = 0; i < parsedData.length; i++) {
		nudges.push(parsedData[i].contents);
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
