var ReactDOM = require('react-dom');
var React = require('react');

function Post (postDataObject) {
	this.postId = postDataObject.id;
	this.preview = postDataObject.preview;
	this.likes = postDataObject.likes;
}

// Post, Dear, PostList, DearList 로 나눠야겠다.

function Dear (dearDataObject, placeId, listElementSelector, moreElementSelector, newElementSelector) {
	this.dearId = dearDataObject.id;
	this.name = dearDataObject.name;
	this.maxPostNum = dearDataObject.totalPostNum; // change property name
	this.currentPage = 0;
	this.lastRenderedId = -1;
	this.posts = [];
	this.placeId = placeId;
	this._listElementSelector = listElementSelector;
	this._moreElementSelector = moreElementSelector;
	this._newElementSelector = newElementSelector;
}
Dear.prototype.initAfterRender = function () {
	// 이게 뭐야 ㅜㅜㅜㅜㅜㅜㅜㅜㅜ Model 이랑 View 랑 얼른 나눠야하는데 ㅜㅜㅜㅜㅜㅜㅜㅜㅜㅜㅜㅜㅜㅜㅜㅜㅜㅜㅜㅜㅜㅜㅜㅜㅜㅜ
	// 코드 중복 때문에라도 클래스 얼른 나눠야 ㅜㅜㅜㅜㅜㅜㅜㅜ
	this.listElement = document.querySelector(this._listElementSelector);
	this.moreElement = this.listElement.querySelector(this._moreElementSelector);
	this.newElement = this.listElement.parentElement.querySelector(this._newElementSelector);

	this.registerEvent();
};
Dear.prototype.noMore = function () {
	this.moreElement.style.display = "none";
};
Dear.prototype.writeNewPost = function () {
	// console.log(this.name);
	var inputElement = document.querySelector('#new-letter input#dear-input');
	inputElement.value = this.name;
	inputElement.focus();
}
Dear.prototype.registerEvent = function () {
	this.moreElement.addEventListener('click', this.getNextPagePosts.bind(this));
	this.newElement.addEventListener('click', this.writeNewPost.bind(this));
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
	this.noMore();
}
DearList.prototype.toggleDear = function (e) {
	var parentElement;
	if (e.target.matches('h3')) {
		parentElement = e.target.parentElement;
	} else if (e.target.matches('article')) {
		parentElement = e.target;
	} else {
		return;
	}

	var header = parentElement.querySelector('h3');
	var list = parentElement.querySelector('ul');

	header.classList.toggle('on');
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
		codes += '<article data-index="'+currentId+'"><h3>'+this.dears[currentId].name+'<button class="new"></button></h3><ul><button class="more">더 보기</button></ul></article>';
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
				this.dears.push(new Dear(item, this.placeId, 'article[data-index="'+this.dears.length+'"] ul', 'button.more', 'button.new'));
			}.bind(this));
			this.render();
	    }
	}.bind(this);
	httpRequest.open('GET', requestURL, true);
	httpRequest.send(null);
};

var LetterBox = React.createClass({
	getInitialState: function() {
		return {
			dearName: '',
			content: '',
			remainLength: this.props.maxContentLength,
			message: {
				show: false,
				isFail: false,
				content: ''
			},
			attachment: {
				file: null,
				src: null
			},
			timestamp: new Date()
		}
	},
	handleDearNameChange: function(event) {
		var maxLength = this.props.maxDearLength;
		var content = event.target.value;
		if (content.length > maxLength) {
			alert(maxLength+'자 이하로 써 주세요!');
		}		
		this.setState({dearName: content.substr(0, maxLength)});
	},
	handleContentChange: function(event) {
		this.changeRemainLength(event);
	},
	changeRemainLength: function(event) {
		var content = event.target.value;
		this.setState({
			content: content,
			remainLength : this.props.maxContentLength - content.length
		});
	},
	handleAttachChange: function (event) {
		var inputFiles = event.target.files;
		if (!inputFiles) {
			return;
		}
		var targetFile = inputFiles[0];
		if (targetFile.size > this.props.maxAttachmentSize) {
			alert('파일 용량은 '+(this.props.maxAttachmentSize/1000000)+'메가를 넘길 수 없습니다 ㅜㅜ');
			return;
		}

		var fr = new FileReader();
		fr.onload = function () {
			this.setState({
				attachment: {
					file: targetFile,
					src: fr.result
				}
			});
		}.bind(this);
		fr.readAsDataURL(targetFile);
	},
	handleSubmit: function (event) {
		event.preventDefault();

		var placeId = this.props.placeId;
		var dearName = this.state.dearName;
		var content = this.state.content;
		var attachment = this.state.attachment.file;
		debugger;

		if (!dearName || !/\S+/.test(dearName)) {
			this.dealMessage(false, '누구에게 쓰는 편지인가요? 받는 대상을 입력해주세요.');
			// blink --> this.querySelector('input[name="dear"]')
			return;
		}
		if (!content || !/\S+/.test(content)) {
			this.dealMessage(false, '편지의 내용을 입력해주세요.');
			// blink --> this.querySelector('textarea[name="content"]')
			return;
		}
		if (content.length > 20000) {
			this.dealMessage(false, '편지의 내용이 너무 길어요! 20000자 이하로 입력해주세요.');
			// blink --> this.querySelector('textarea[name="content"]')
			return;
		}

		var data = new FormData();
		data.append('placeId', placeId);
		data.append('content', content);
		data.append('dear', dearName);
		data.append('file', attachment);

		var httpRequest = new XMLHttpRequest();
		httpRequest.onreadystatechange = function() {
			if (httpRequest.readyState === XMLHttpRequest.DONE) {
				switch (httpRequest.status) {
					case 200 :
						var received = JSON.parse(httpRequest.response);
						if (!received.success) {
							this.dealMessage(false, received.errorMessage);
							return;
						}

						var createdPost = received.result;
						var resultMessage = <span>글쓰기 성공!<a href={'/place/'+createdPost.placeId+'/dear/'+createdPost.name+'/post/'+createdPost.id}>내가 쓴 글 보러가기 &gt;</a></span>;
						this.dealMessage(true, resultMessage);
						this.setState({
							timestamp: new Date()
						});
						return;
					case 400 :
						debugger;
						if (httpRequest.response == "Empty input data") {
							this.dealMessage(false, '내용을 입력해 주세요 ㅜㅜ');
						}
						break;
					default :
						this.dealMessage(false);
				}
			}
		}.bind(this);
		var requestURL = '/api/place/'+placeId+'/post';
		httpRequest.open('POST', requestURL, true);
		httpRequest.send(data);
	},
	dealMessage: function (isSuccess, message="무언가 잘못되었어요!") {
		this.setState({
			message: {
				show: true,
				isFail: !isSuccess,
				content: message
			}
		});
	},
	render: function() {
		var message = this.state.message;
		var messageBox = null;
		if (message.show) {
			let className = (message.isFail ? "fail " : "") + "message";
			messageBox = <div className={className}>
				{message.content}
			</div>;
		}

		var previewBox = null;
		var attachment = this.state.attachment.src;
		if (attachment) {
			previewBox = <img className="preview" src={attachment} />;
		}

		return (
			<section id="new-letter" key={this.state.timestamp} className="the-letter">
				<h2 className="hidden">new letter</h2>
				<form onSubmit={this.handleSubmit}>
					<input id="place-id" type="hidden" name="placeId" value={this.props.placeId} />
					<label className="dear-label" htmlFor="dear-input">Dear.</label>
					<input id="dear-input" type="text" name="dear"
						value={this.state.dearName}
						onChange={this.handleDearNameChange}
					/>
					<textarea id="post-content" name="content"
						value={this.state.content}
						onChange={this.handleContentChange}
					/>
					<div className="buttons">
						<button className="file">
							<input type="file" name="imagefile" accept="image/*"
								onChange={this.handleAttachChange}
							/>
						</button>
						<label htmlFor="post-content"
							className={(this.state.remainLength < 0 ? "over " : "") + "remain-length"}> 
							{this.state.remainLength}
						</label>
					</div>
					<button className="send">SEND</button>
					<div className="info">
						{messageBox}
						{previewBox}
					</div>
				</form>
			</section>
		);
	}
});


document.addEventListener("DOMContentLoaded", function() {
	// 임시방편. insert into dear values (91, "만든이");
	var THEID = 91;

	var placeId = window.location.pathname.substr(1);
	if (!placeId) {
		console.error('why no placeId?!');
		return;
	}
	var dearList = new DearList (placeId, document.querySelector('#letters'), document.querySelector('#letters .more'));
	dearList.dears.push(new Dear({id:THEID, name:'만든이'}, placeId, 'article[data-index="'+0+'"] ul', 'button.more', 'button.new'));
	dearList.getNextPageDears();

	ReactDOM.render(
		<LetterBox
			maxDearLength={25}
			maxContentLength={20000}
			maxAttachmentSize={10000000}
			placeId={placeId}
		/>,
		document.getElementById('letter-box')
	);
});