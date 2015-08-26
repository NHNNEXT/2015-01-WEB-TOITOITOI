var replyButton = document.getElementById('replyButton');
replyButton.addEventListener("click",showReplyBox, false);

function showReplyBox () {
	var replyBox = document.getElementById('replyBox');
 	replyBox.style.display = 'inline';
	
}

//button이 inline element 이기 때문에

/*replyButton.addEventListener("click",
	function () {
	}, false);*/
