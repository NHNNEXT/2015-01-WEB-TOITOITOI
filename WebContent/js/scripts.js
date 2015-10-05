var posting_style = document.querySelector('.posting').style;
var posting_textbox_style = document.querySelector('.posting .textbox').style;
var posting_textboxbg_style = document.querySelector('.posting .textbox-bg').style;
var posting_send_style = document.querySelector('.posting .send').style;

var curPos = window.scrollY;
var posting = document.querySelector('.posting');
var posting_top = posting.offsetTop;
var topBar = document.querySelector('.top-bar');
var topBar_height = topBar.offsetHeight;

window.addEventListener("scroll", function(event) {
	if (scrollY > (posting_top - topBar_height)) {
		posting_style.position = "fixed";
		posting_style.top = "5.9vmax";
		posting_style.zIndex = "2";
		posting_textbox_style.top = '0.7vmax';
		posting_textbox_style.height = '8vmin';
		posting_textbox_style.lineHeight = '8vmin';
		posting_textbox_style.fontSize = '4min';
		posting_textbox_style.borderColor = "rgba(237,222,204,0.8)";
		posting_textboxbg_style.top = '0';
		posting_textboxbg_style.left = '0';
		posting_textboxbg_style.width = '100%';
		posting_textboxbg_style.height = '11vmin';
		posting_textboxbg_style.borderRadius = '0px';
		posting_textboxbg_style.borderBottom = '1px solid rgba(234, 202, 190, 0.15)';		
		posting_send_style.top = '0.7vmax';
		posting_send_style.right = '5%';
		posting_send_style.height = '8vmin';
		curPos = scrollY;
	} 
	
	if (scrollY < (posting_top - topBar_height)) {
		posting_style.position = "relative";
		posting_style.top = "0";
		posting_style.zIndex = "0";
		posting_textbox_style.top = '0';
		posting_textbox_style.height = '7.7vmax';
		posting_textbox_style.lineHeight = '7.7vmax';
		posting_textbox_style.fontSize = '4.5vmin';
		posting_textboxbg_style.left = '5%';
		posting_textboxbg_style.width = '90%';
		posting_textboxbg_style.height = '7.7vmax';
		posting_textboxbg_style.borderRadius = '100px';
		posting_textboxbg_style.borderBottom = 'none';		
		posting_send_style.top = '0';
		posting_send_style.right = '5.2%';
		posting_send_style.height = '7.7vmax';
		curPos = scrollY;
	} else {
		curPos = scrollY;
	}

}, false);

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
	        form.trigger('reset'); // reset form
	        updatePosts();
	      },
	      error: function(e) {
	        console.log(e)
	      }
	    });
	  });
	});
/*
function updatePosts(){
	var posts = document.querySelector(".posts")
	var firstPost = posts.firstElementChild;
	
	var newPost = firstPost.cloneNode(true);
	var postContents = document.querySelector(".posting .textbox");
	newPost.querySelector(".contents").textContents = postContents.value;

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
*/
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

