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

function DearList (placeId, listElement, moreElement) {
	this.placeId = placeId;
	this.currentPage = 0;
	this.lastRenderedId = -1;
	this.dears = []; // which is sorted by postNum DESC
	this.listElement = listElement; // this file will be executed after parsing DOMs, becauseof 'defer'.
	this.moreElement = moreElement;
	this.registerEvent();
}
DearList.prototype.toggleDear = function (e) {
	if (!e.target.matches('h3') && !e.target.matches('article')) { // check delegation target
		return;
	}
	var parentElement = e.target.closest('article');
	var header = parentElement.querySelector('h3');
	var list = parentElement.querySelector('ul');

	toggleClass(header, 'on');
	var elementIndex = parentElement.getAttribute('data-index'); // .dataset doesn't work for IE10.

	var dear = this.dears[elementIndex];
	if (dear.posts.length <= 0) {
		dear.getNextPagePosts();
	}
};
DearList.prototype.registerEvent = function () {
	this.moreElement.addEventListener('click', this.getNextPageDears.bind(this));
	this.listElement.addEventListener('click', this.toggleDear.bind(this));
}
DearList.prototype.noMore = function () {
	this.moreElement.style.display = "none"
};
DearList.prototype.render = function () {
	var codes = '';
	for (var dataLen = this.dears.length; this.lastRenderedId+1 < dataLen; (this.lastRenderedId)++) {
		codes += '<article data-index="'+(this.lastRenderedId+1)+'"><h3>'+this.dears[(this.lastRenderedId+1)].name+'</h3><ul></ul></article>';
	}
	this.moreElement.insertAdjacentHTML('beforebegin', codes);
};
DearList.prototype.getNextPageDears = function () {
	var httpRequest = new XMLHttpRequest();
	var requestURL = '/api/place/'+this.placeId+'/dear?page='+ ++(this.currentPage);
	httpRequest.onreadystatechange = function () {
	    if (httpRequest.readyState === XMLHttpRequest.DONE) {
			var received = JSON.parse(httpRequest.response);
			if (!received.success) {
				switch (received.errorMessage) {
					case "No more data." :
						console.log('all dears loaded');
						this.noMore();
						break;
					default :
						console.error('something went wrong @'+requestURL);
						debugger;
				}
				return;
			}
			received.result.forEach(function (item, index, array) {
				this.dears.push(new Dear(item, this.placeId, 'article[data-index="'+this.dears.length+'"] ul'));
			}.bind(this));
			this.render();
	    }
	}.bind(this);
	httpRequest.open('GET', requestURL, true);
	httpRequest.send(null);
};


document.addEventListener("DOMContentLoaded", function() {
	var placeId = document.querySelector('#place-id').value;
	document.querySelector('#new-letter form').addEventListener('submit', function (e) {
		e.preventDefault();
	});
	if (!placeId) {
		console.error('why no placeId?!');
		return;
	}
	var dearList = new DearList (placeId, document.querySelector('#letters'), document.querySelector('#letters .more'));
	dearList.getNextPageDears();

	document.querySelector('#new-letter form').addEventListener('submit', function (e) {
		var form = this;
		var placeId = form.querySelector('input[name="placeId"]').value;
		var dear = form.querySelector('input[name="dear"]').value;
		var content = form.querySelector('textarea[name="content"]').value;

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

		// var data = encodeURIComponent(new FormData(form));
		// var data = new FormData();
		var data = new FormData(form);
		// data.append('dear', encodeURIComponent(dear));
		// data.append('content', encodeURIComponent(content));
		// data.append('placeId', encodeURIComponent(placeId));
		var httpRequest = new XMLHttpRequest();
		httpRequest.onreadystatechange = function(){
		    if (httpRequest.readyState === XMLHttpRequest.DONE) {
		    	form.reset();
		    }
		};
		httpRequest.open('POST', '/api/place/'+placeId+'/post', true);
		// httpRequest.setRequestHeader("Content-Type","multipart/form-data;charset=UTF-8");
		httpRequest.send(data);
	});

	function uploadFile (e) {
		var fileInput = this.files[0];
		if (!fileInput) {
			return;
		}

		console.log(fileInput.name);
		var data = new FormData();
		data.append('imagefile', fileInput);
		var httpRequest = new XMLHttpRequest();
		httpRequest.onreadystatechange = function(){
			if (httpRequest.readyState === XMLHttpRequest.DONE) {
				console.log('good!');
		    }
		};
		httpRequest.open('POST', '/api/post/file', true);
		httpRequest.send(data);
	}
	var fileElement = document.querySelector('#new-letter form input[type="file"]');
	// fileElement.addEventListener('click', uploadFile);
	fileElement.addEventListener('change', uploadFile);

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