function Post (postDataObject) {
	this.postId = postDataObject.id;
	this.preview = postDataObject.preview;
	this.likes = postDataObject.likes;
}

// Post, Dear, PostList, DearList 로 나눠야겠다.

function Dear (dearDataObject, placeId, listElementSelector, moreElementSelector) {
	this.dearId = dearDataObject.id;
	this.name = dearDataObject.name;
	this.maxPostNum = dearDataObject.totalPostNum; // change property name
	this.currentPage = 0;
	this.lastRenderedId = -1;
	this.posts = [];
	this.placeId = placeId;
	this._listElementSelector = listElementSelector;
	this._moreElementSelector = moreElementSelector;
}
Dear.prototype.initAfterRender = function () {
	// 이게 뭐야 ㅜㅜㅜㅜㅜㅜㅜㅜㅜ Model 이랑 View 랑 얼른 나눠야하는데 ㅜㅜㅜㅜㅜㅜㅜㅜㅜㅜㅜㅜㅜㅜㅜㅜㅜㅜㅜㅜㅜㅜㅜㅜㅜㅜ
	// 코드 중복 때문에라도 클래스 얼른 나눠야 ㅜㅜㅜㅜㅜㅜㅜㅜ
	this.listElement = document.querySelector(this._listElementSelector);
	this.moreElement = this.listElement.querySelector(this._moreElementSelector);

	this.registerEvent();
};
Dear.prototype.noMore = function () {
	this.moreElement.style.display = "none";
};
Dear.prototype.registerEvent = function () {
	this.moreElement.addEventListener('click', this.getNextPagePosts.bind(this));
};
Dear.prototype.render = function () {
	var codes = '';
	for ( var dataLen = this.posts.length, currentId = this.lastRenderedId+1; currentId < dataLen; currentId = ++(this.lastRenderedId)+1 ) {
		var currentPost = this.posts[currentId];
		codes += '<li><a href="/place/'+this.placeId+'/dear/'+this.name+'/post/'+currentPost.postId+'">'+currentPost.preview+'</a><span class="likes"><span class="hidden">좋아요</span>'+currentPost.likes+'</span></li>';
	}
	this.moreElement.insertAdjacentHTML('beforebegin', codes);

	for ( var dataLen = this.posts.length, currentId = this.lastRenderedId+1; currentId < dataLen; currentId = ++(this.lastRenderedId)+1 ) {
		var currentPost = this.posts[currentId];
		codes += '<li><a href="/place/'+this.placeId+'/dear/'+this.dearId+'/post/'+currentPost.postId+'">'+currentPost.preview+'</a></li>';
	}
};
Dear.prototype.getNextPagePosts = function (e) {
	var httpRequest = new XMLHttpRequest(); ///api/place/1/dear/1/post?page=1
	var requestURL = '/api/place/'+this.placeId+'/dear/'+this.dearId+'/post?page='+ (this.currentPage +1);
	httpRequest.onreadystatechange = function () {
	    if (httpRequest.readyState === XMLHttpRequest.DONE) {
			var received = JSON.parse(httpRequest.response);
			if (!received.success) {
				switch (received.errorMessage) {
					case "No more data." :
						console.log('all posts loaded');
						this.noMore();
						break;
					default :
						console.error('something went wrong @'+requestURL);
						debugger;
				}
				return;
			}
			this.currentPage++;
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
};
DearList.prototype.noMore = function () {
	this.moreElement.style.display = "none";
};
DearList.prototype.render = function () {
	var codes = '';
	var lastRenderedId = this.lastRenderedId;
	var dataLen = this.dears.length;

	for ( var currentId = lastRenderedId+1; currentId < dataLen; currentId++ ) {
		codes += '<article data-index="'+currentId+'"><h3>'+this.dears[currentId].name+'</h3><ul><button class="more">더 보기</button></ul></article>';
	}
	this.moreElement.insertAdjacentHTML('beforebegin', codes);
	for ( var currentId = lastRenderedId+1; currentId < dataLen; currentId++ ) {
		this.dears[currentId].initAfterRender();
	}
	this.lastRenderedId = dataLen - 1;
};
DearList.prototype.getNextPageDears = function () {
	var httpRequest = new XMLHttpRequest();
	var requestURL = '/api/place/'+this.placeId+'/dear?page='+ (this.currentPage +1);
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
			this.currentPage++;
			received.result.forEach(function (item, index, array) {
				this.dears.push(new Dear(item, this.placeId, 'article[data-index="'+this.dears.length+'"] ul', 'button.more'));
			}.bind(this));
			this.render();
	    }
	}.bind(this);
	httpRequest.open('GET', requestURL, true);
	httpRequest.send(null);
};


document.addEventListener("DOMContentLoaded", function() {
	var placeId = document.querySelector('#place-id').value;
	if (!placeId) {
		console.error('why no placeId?!');
		return;
	}
	var dearList = new DearList (placeId, document.querySelector('#letters'), document.querySelector('#letters .more'));
	dearList.getNextPageDears();

	function dealMessage (isSuccess, messageHTML, targetElement) {
		var messageBar = formElement.querySelector('.message');
		messageHTML = messageHTML || '무언가 잘못되었어요!';
		((isSuccess)? removeClass : addClass)(messageBar, 'fail');
		messageBar.innerHTML = messageHTML;
		//TODO: after showing message, focus on targetElement. targetElement might be empty.
		//TODO: 2초 뒤에 사라지게(fadeIn/Out)는 나중에.
	}

	var formElement = document.querySelector('#new-letter form');
	formElement.addEventListener('submit', function (e) {
		e.preventDefault();

		var placeId = this.querySelector('input[name="placeId"]').value;
		var dear = this.querySelector('input[name="dear"]').value;
		var content = this.querySelector('textarea[name="content"]').value;
		var inputFiles = this.querySelector('input[type="file"]').files;

		if (!placeId) {
			console.error('no placeId');
			return;
		}
		if (!dear) {
			dealMessage( false, '누구에게 쓰는 편지인가요? 받는 대상을 입력해주세요.', this.querySelector('input[name="dear"]') );
			return;
		}
		if (!content) {
			dealMessage( false, '편지의 내용을 입력해주세요.', this.querySelector('textarea[name="content"]') );
			return;
		}
		if (content.length > 20000) {
			dealMessage( false, '편지의 내용이 너무 길어요! 20000자 이하로 입력해주세요.', this.querySelector('textarea[name="content"]') );
			return;
		}

		var data = new FormData(this);
		var httpRequest = new XMLHttpRequest();
		httpRequest.onreadystatechange = function(){
			if (httpRequest.readyState === XMLHttpRequest.DONE) {
				if (httpRequest.status == 200) {
					var received = JSON.parse(httpRequest.response);
					if (!received.success) {
						dealMessage( false, received.errorMessage );
						return;
					}

					var createdPost = received.result;
					var resultMessage = '글쓰기 성공!'+' <a href="'+('/place/'+createdPost.placeId+'/dear/'+createdPost.dearId+'/post/'+createdPost.id)+'">내가 쓴 글 보러가기 &gt;</a>';
					dealMessage( true, resultMessage );
					formElement.reset();
					return;
				}
				dealMessage( false );
			}
		};
		httpRequest.open('POST', '/api/place/'+placeId+'/post', true);
		httpRequest.send(data);
	});

	formElement.querySelector('input[type="file"]').addEventListener('change', function (e) {
		var inputFiles = e.target.files;
		var fr = new FileReader();
		fr.onload = function (argument) {
			formElement.querySelector('img.preview').src = fr.result;
		};
		fr.readAsDataURL(inputFiles[0]);
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