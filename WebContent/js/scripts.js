var posting_style = document.querySelector('.posting').style;
var posting_textbox_style = document.querySelector('.posting .textbox').style;
var posting_textboxbg_style = document.querySelector('.posting .textbox-bg').style;
var posting_send_style = document.querySelector('.posting .send').style;

var posting_textbox = document.querySelector(".posting .textbox");

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
var posting = document.querySelector('.posting');
var posting_pos = posting.getBoundingClientRect();

window.addEventListener("scroll", function(event) {
	if (scrollY > 176) {
		posting_style.position = "fixed";
		posting_style.top = "6vh";
		posting_style.zIndex = "2";
		posting_textbox_style.top = '1vh';
		posting_textbox_style.height = '32px';
		posting_textbox_style.lineHeight = '32px';
		posting_textbox_style.fontSize = '10pt';
		posting_textboxbg_style.top = '0';
		posting_textboxbg_style.left = '0';
		posting_textboxbg_style.width = '100%';
		posting_textboxbg_style.height = '46px';
		posting_textboxbg_style.borderRadius = '0px';
		posting_send_style.top = '1vh';
		posting_send_style.right = '5%';
		posting_send_style.height = '32px';
		curPos = scrollY;
	} 
	
	if (scrollY < 176) {
		posting_style.position = "relative";
		posting_style.top = "11px";
		posting_style.zIndex = "0";
		posting_textbox_style.top = '0';
		posting_textbox_style.height = '57px';
		posting_textbox_style.lineHeight = '57px';
		posting_textbox_style.fontSize = '12pt';
		posting_textboxbg_style.top = '0';
		posting_textboxbg_style.left = '5%';
		posting_textboxbg_style.width = '90%';
		posting_textboxbg_style.height = '57px';
		posting_textboxbg_style.borderRadius = '100px';
		posting_send_style.top = '0';
		posting_send_style.right = '5.3%';
		posting_send_style.height = '57px';
		curPos = scrollY;
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
	$('.posting-textbox').attr('placeholder', nextNudge);
}
function setTimeOutNudge () {
	changeNudge();
	window.setTimeout(setTimeOutNudge, 5000);
}
window.setTimeout(setTimeOutNudge, 10);