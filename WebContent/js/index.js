function Post (postDataObject) {
	this.postId = postDataObject.id;
	this.preview = postDataObject.preview;
	this.likes = postDataObject.likes;
}

// Post, Dear, PostList, DearList 로 나눠야겠다.

function Dear (dearDataObject, placeId, listElementSelector) {
	this.dearId = dearDataObject.id;
	this.name = dearDataObject.name;
	this.maxPostNum = dearDataObject.totalPostNum; // change property name
	this.currentPage = 0;
	this.posts = [];
	this.placeId = placeId;
	this.listElementSelector = listElementSelector; // .querySelector occurs error for ''.
}
Dear.prototype.render = function (listElement) {
	var listElement = document.querySelector(this.listElementSelector);
	if (!listElement) {
		console.error('render to what?');
		return;
	}

	var dataLen = this.posts.length;
	var codes = '';
	for (var i = 0; i < dataLen; i++) {
		var currentPost = this.posts[i];
		codes += '<li><a href="/place/'+this.placeId+'/dear/'+this.dearId+'/post/'+currentPost.postId+'">'+currentPost.preview+'</a></li>';
	}
	listElement.insertAdjacentHTML('beforeend', codes);
};
Dear.prototype.getNextPagePosts = function () {
	if (this.currentPage > 0 && this.posts.length >= this.maxPostNum) {
		console.log('all posts loaded');
		return;
	}

	var httpRequest = new XMLHttpRequest(); ///api/place/1/dear/1/post?page=1
	var requestURL = '/api/place/'+this.placeId+'/dear/'+this.dearId+'/post?page='+ ++(this.currentPage);
	httpRequest.onreadystatechange = function () {
	    if (httpRequest.readyState === XMLHttpRequest.DONE) {
			var received = JSON.parse(httpRequest.response);
			if (!received.success) {
				console.error('something went wrong @'+requestURL);
				return;
			}
			received.result.forEach(function (item, index, array) {
				this.posts.push(new Post(item));
			}.bind(this));
			this.render();
	    }
	}.bind(this);
	httpRequest.open('GET', requestURL, true);
	httpRequest.send(null);
};

function DearList (placeId, maxDearNum, listElement) {
	this.placeId = placeId;
	this.maxDearNum = maxDearNum;
	this.currentPage = 0;
	this.dears = []; // which is sorted by postNum DESC
	this.listElement = listElement; // this file will be executed after parsing DOMs, becauseof 'defer'.
}
DearList.prototype.render = function () {
	var dataLen = this.dears.length;
	var codes = '';
	for (var i = 0; i < dataLen; i++) {
		// codes += '<article><h3 data-index="'+i+'">'+this.dears[i].name+'</h3><ul></ul></article>';
		codes += '<article data-index="'+i+'"><h3>'+this.dears[i].name+'</h3><ul></ul></article>';
		// codes += '<article><h3>'+this.dears[i].name+'</h3><ul></ul></article>';
	}
	this.listElement.insertAdjacentHTML('beforeend', codes);
};
DearList.prototype.getNextPageDears = function () {
	if (this.currentPage > 0 && this.dears.length >= this.maxDearNum) {
		console.log('all dears loaded');
		return;
	}

	var httpRequest = new XMLHttpRequest();
	var requestURL = '/api/place/'+this.placeId+'/dear?page='+ ++(this.currentPage);
	httpRequest.onreadystatechange = function () {
	    if (httpRequest.readyState === XMLHttpRequest.DONE) {
			var received = JSON.parse(httpRequest.response);
			if (!received.success) {
				console.error('something went wrong @'+requestURL);
				debugger;
				return;
			}
			received.result.forEach(function (item, index, array) {
				this.dears.push(new Dear(item, this.placeId, 'article[data-index="'+index+'"] ul'));
			}.bind(this));
			this.render();
	    }
	}.bind(this);
	httpRequest.open('GET', requestURL, true);
	httpRequest.send(null);
};


document.addEventListener("DOMContentLoaded", function() {
	var dearList = new DearList (1, 10, document.querySelector('#letters'));
	dearList.getNextPageDears();

	document.querySelector('#new-letter form').addEventListener('submit', function (e) {
		e.preventDefault();
		var placeId = this.querySelector('input[name="placeId"]').value;
		var dear = this.querySelector('input[name="dear"]').value;
		var content = this.querySelector('textarea[name="content"]').value;
		var form = this;
		if (!placeId) {
			console.error('no placeId');
			return;
		}

		if (!dear) {
			alert("input dear"); // after alert, focus on input.
			return;
		}
		if (!content) {
			alert("input content"); // after alert, focus on input.
			return;
		}
		if (content.length > 20000) {
			alert("it\'s too long"); // after alert, focus on input.
			return;
		}
		var httpRequest = new XMLHttpRequest();
		httpRequest.onreadystatechange = function(){
		    if (httpRequest.readyState === XMLHttpRequest.DONE) {
		    	form.reset();
		    }
		};
		httpRequest.open('POST', '/api/place/'+placeId+'/post', true);
		httpRequest.setRequestHeader("Content-Type","application/x-www-form-urlencoded;charset=UTF-8");
		httpRequest.send(
			'dear='+encodeURIComponent(dear)
			+'&content='+encodeURIComponent(content)
			+'&placeId='+encodeURIComponent(placeId)
		);
	});

	document.querySelector('#letters').addEventListener('click', function (e) {
		if (!e.target.matches('h3') && !e.target.matches('article')) { // check delegation target
			return;
		}
		var parentElement = e.target.closest('article');
		var header = parentElement.querySelector('h3');
		var list = parentElement.querySelector('ul');

		var prevOn = this.querySelector('.on');
		removeClass(prevOn, 'on');
		if (header === prevOn) { return; }

		addClass(header, 'on');
		var elementIndex = parentElement.getAttribute('data-index'); // .dataset doesn't work for IE10.

		var dear = dearList.dears[elementIndex];
		dear.getNextPagePosts();
		// debugger;
		// var list = header.parentNode.querySelector('ul');
		// if (!list) {
		// 	var dear = header.textContent;
		// 	var previews = findPreview();
		// 	addPreview(previews, header);
		// }
	});

	// make component
	var textarea = document.querySelector('#new-letter textarea');
	var remainNotifier = document.querySelector('#new-letter .remain-length');
	function changeRemainLength () {
		var remainLength = 20000 - textarea.value.length;
		remainNotifier.textContent = remainLength;
		((remainLength < 0) ? addClass : removeClass)(remainNotifier, 'over');
	}
	if (!textarea.readOnly) {
		textarea.addEventListener('change', changeRemainLength);
		textarea.addEventListener('keydown', function (e) { window.setTimeout(changeRemainLength, 0); });
		textarea.addEventListener('paste', function (e) { window.setTimeout(changeRemainLength, 0); });
		textarea.addEventListener('cut', function (e) { window.setTimeout(changeRemainLength, 0); });
		textarea.addEventListener('drop', function (e) { window.setTimeout(changeRemainLength, 0); });
	}
});