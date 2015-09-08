var replyButtons = $('.replyButton');
replyButtons.on('click', function () {
	var replyBox = $('.replyBox');
	replyBox.show();
});


var posting_style = document.querySelector('.posting').style;
var posting_textbox_style = document.querySelector('.posting-textbox').style;
var posting_send_style = document.querySelector('.posting-send').style;

var posting_textbox = document.querySelector(".posting-textbox");


posting_textbox.addEventListener ('focus', function (event) { 

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

window.addEventListener ("scroll", function (event) {
    
    if (curPos < scrollY) {
        posting_style.height = '26px';
        posting_style.paddingRight = "10px";
        posting_textbox_style.width = '99%';
        posting_textbox_style.height = '17px';
        posting_textbox_style.fontSize = '10pt';
        posting_send_style.opacity = 0;
        posting_send_style.right = "-100px";
        curPos = scrollY;
    } else if (scrollY == 0){
        posting_style.postion = "fixed";
        posting_style.height = '80px';
        posting_style.paddingRight = "60px";
        posting_textbox_style.width = '100%';
        posting_textbox_style.height = '70px';
        posting_textbox_style.fontSize = '14pt';    
        posting_send_style.opacity = 1;
        posting_send_style.right = "5px";
    }  
    else {
        curPos = scrollY;
    }
     
}, false);

$(".like-reply").click(function() {
  // todo
  // if(!on)
    // +1 .addclass(on)
    // ajax(like) (reid, status = like)
  // else
    // -1. removeclass(on)
    // ajax(unlike) (reid, status = unlike)

	var reid = $(this).attr("value");
	var datas = {
    'reid': reid, 
    'status':'like'
  };
	var div = $(this);

  if (!$(this).hasClass('on')) {
    $(this).addClass('on');
    var likes = $(this).text() + 1;
    $(this).html(likes);
  }

	$.ajax({
           type:"POST",
           url:"/likedOnReply",
           data: datas,
           success : function(data) {
           		console.log("success");
             },
           error : function(xhr, status, error) {
                 console.log(status);
                 //console.log(error);
           }
     });
});
