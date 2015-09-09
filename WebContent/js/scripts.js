var replyButtons = $('.replyButton');
replyButtons.on('click', function() {
	// debugger;
	$(this).parent().children('.replyBox').toggle();
});

var posting_style = document.querySelector('.posting').style;
var posting_textbox_style = document.querySelector('.posting-textbox').style;
var posting_send_style = document.querySelector('.posting-send').style;

var posting_textbox = document.querySelector(".posting-textbox");

posting_textbox.addEventListener('focus', function(event) {

	posting_style.postion = "fixed";
	posting_style.height = '80px';
	posting_style.paddingRight = "60px";
	posting_textbox_style.width = '100%';
	posting_textbox_style.height = '70px';
	posting_textbox_style.fontSize = '14pt';
	posting_send_style.opacity = 1;
	posting_send_style.right = "5px";

}, false);

var curPos = window.scrollY;

window.addEventListener("scroll", function(event) {

	if (curPos < scrollY) {
		posting_style.height = '26px';
		posting_style.paddingRight = "10px";
		posting_textbox_style.width = '99%';
		posting_textbox_style.height = '17px';
		posting_textbox_style.fontSize = '10pt';
		posting_send_style.opacity = 0;
		posting_send_style.right = "-100px";
		curPos = scrollY;
	} else if (scrollY == 0) {
		posting_style.postion = "fixed";
		posting_style.height = '80px';
		posting_style.paddingRight = "60px";
		posting_textbox_style.width = '100%';
		posting_textbox_style.height = '70px';
		posting_textbox_style.fontSize = '14pt';
		posting_send_style.opacity = 1;
		posting_send_style.right = "5px";
	} else {
		curPos = scrollY;
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