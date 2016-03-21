var cardCarousel;
var headerCarousel;

var DearList = React.createClass({
	loadDataFromServer: function() {
		var httpRequest = new XMLHttpRequest();
		var requestURL = '/api/place/'+this.props.placeId+'/dear?page='+ this.props.page; // page will disappear later, because we don't paginate dearlist.
		httpRequest.onreadystatechange = function () {
		    if (httpRequest.readyState === XMLHttpRequest.DONE) {
				var received = JSON.parse(httpRequest.response);
				if (!received.success) {
					switch (received.errorMessage) {
						default :
							console.error('something went wrong @'+requestURL);
							debugger;
					}
					return;
				}
				this.setState({dears: received.result});
		    }
		}.bind(this);
		httpRequest.open('GET', requestURL, true);
		httpRequest.send(null);
	},
	getInitialData: function() {
		return {
			dears: []	// { createdtime, id, name. totalPostNum }
		};
	},
	componentDidMount: function() {
		this.loadDataFromServer();
	},
	render: function() {
		var placeId = this.props.placeId;
		var dears = this.state.dears.map(dear => (
			<Dear placeId={placeId} createdtime={dear.createdtime} dearId={dear.id} maxPostNum={dear.maxPostNum} pageSize={20}>
				{dear.name}
			</Dear>
		));
		return (
			<section id="letters">
				<h2 className="hidden">letters</h2>
				<div className="carousel-card-container">
					{dears}
				</div>
			</section>
		);
	}
});

var Dear = React.createClass({
	loadMorePostList: function() {
		// do noMore, with maxPostNum and pageSize.
	},
	writeNewPost: function() {
		headerCarousel.trigger('to.owl.carousel', [1, 250]);
		var inputElement = document.querySelector('#new-letter input#dear-input');
		inputElement.value = this.name;
		// inputElement.focus();
	},
	render: function() {
		var ancestor = {
			placeId: this.props.placeId,
			dearId: this.props.dearId,
			dearName: this.props.children
		};

		return (
			<article className="dear owl-item">
				<h3>{this.props.children}<button className="new" onClick={this.writeNewPost}></button></h3>
				<PostList ancestor={ancestor} pageSize={this.props.pageSize} />
				<button className="more" onClick={this.loadMorePostList}>더 보기</button>
			</article>
		);
	}
});

var PostList = React.createClass({
	loadDataFromServer: function() {
		var httpRequest = new XMLHttpRequest(); ///api/place/1/dear/1/post?page=1
		var requestURL = '/api/place/'+this.props.ancestor.placeId+'/dear/'+this.props.ancestor.dearId+'/post?page='+ (this.state.page +1);
		httpRequest.onreadystatechange = function () {
		    if (httpRequest.readyState === XMLHttpRequest.DONE) {
				var received = JSON.parse(httpRequest.response);
				if (!received.success) {
					switch (received.errorMessage) {
						default :
							console.error('something went wrong @'+requestURL);
							debugger;
					}
					return;
				}
				this.setState({posts: received.result});
		    }
		}.bind(this);
		this.setState({page: this.state.page + 1});
		httpRequest.open('GET', requestURL, true);
		httpRequest.send(null);
	},
	getInitialData: function() {
		return {
			posts: []	// { id, preview, likes, createdtime }
		};
	},
	componentDidMount: function() {
		this.loadDataFromServer();
	},
	render: function() {
		var ancestor = this.props.ancestor;

		var posts = this.state.posts.map(post => (
			<Post ancestor={ancestor} postId={post.id} createdtime={post.createdtime} likes={post.likes}>
				{post.preview}
			</Post>
		));

		return (
			<ul>
				{posts}
			</ul>
		);
	}
});

var Post = React.createClass({
	render: function() {
		<li>
			<a href="/place/{this.props.ancestor.placeId}/dear/{this.props.ancestor.dearName}/post/{this.props.postId+'">
				{this.props.children}
			</a>
			<span class="likes"><span class="hidden">좋아요</span>
				{this.props.likes}
			</span>
		</li>
	}
});

document.addEventListener("DOMContentLoaded", function() {
	// 임시방편. insert into dear values (91, "만든이");, 임시적으로 삭제.
	// var THEID = 91;

	var placeId = document.querySelector('#place-id').value;
	if (!placeId) {
		console.error('why no placeId?!');
		return;
	}

	ReactDOM.render(
		<DearList placeId={placeId} page={0} />,
		document.getElementById('letters')
	);

	function dealMessage (isSuccess, messageHTML, targetElement) {
		var messageBar = formElement.querySelector('.message');
		messageHTML = messageHTML || '무언가 잘못되었어요!';
		messageBar.classList[((isSuccess)? 'remove' : 'add')]('fail');
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
		if (!dear || !/\S+/.test(dear)) {
			dealMessage( false, '누구에게 쓰는 편지인가요? 받는 대상을 입력해주세요.', this.querySelector('input[name="dear"]') );
			return;
		}
		if (!content || !/\S+/.test(content)) {
			dealMessage( false, '편지의 내용을 입력해주세요.', this.querySelector('textarea[name="content"]') );
			return;
		}
		if (content.length > 20000) {
			dealMessage( false, '편지의 내용이 너무 길어요! 20000자 이하로 입력해주세요.', this.querySelector('textarea[name="content"]') );
			return;
		}

		var data = new FormData(this);
		var httpRequest = new XMLHttpRequest();
		httpRequest.onreadystatechange = function() {
			if (httpRequest.readyState === XMLHttpRequest.DONE) {
				switch (httpRequest.status) {
					case 200 :
						var received = JSON.parse(httpRequest.response);
						if (!received.success) {
							dealMessage( false, received.errorMessage );
							return;
						}

						var createdPost = received.result;
						var resultMessage = '글쓰기 성공!'+' <a href="'+('/place/'+createdPost.placeId+'/dear/'+createdPost.name+'/post/'+createdPost.id)+'">내가 쓴 글 보러가기 &gt;</a>';
						dealMessage( true, resultMessage );
						formElement.reset();
						formElement.querySelector('.preview').classList.add('off');
						return;
					case 400 :
						if (httpRequest.response == "Empty input data") {
							dealMessage( false, '내용을 입력해 주세요 ㅜㅜ' );
						}
						break;
					default :
						dealMessage( false );
				}
			}
		};
		httpRequest.open('POST', '/api/place/'+placeId+'/post', true);
		httpRequest.send(data);
	});

	formElement.querySelector('input[type="file"]').addEventListener('change', function (e) {
		var inputFiles = e.target.files;
		if (!inputFiles) {
			return;
		}
		var targetFile = inputFiles[0];
		if (targetFile.size > 10000000) {
			alert('파일 용량은 10메가를 넘길 수 없습니다 ㅜㅜ');
			return;
		}

		var fr = new FileReader();
		fr.onload = function (argument) {
			var previewElement = formElement.querySelector('img.preview');
			previewElement.src = fr.result;
			previewElement.classList.remove('off');
		};
		fr.readAsDataURL(targetFile);
	});

	// make component
	var textarea = document.querySelector('#new-letter textarea');
	var remainNotifier = document.querySelector('#new-letter .remain-length');
	function changeRemainLength () {
		var remainLength = 20000 - textarea.value.length;
		remainNotifier.textContent = remainLength;
		remainNotifier.classList[((remainLength < 0) ? 'add' : 'remove')]('over');
	}
	if (!textarea.readOnly) {
		textarea.addEventListener('change', changeRemainLength);
		textarea.addEventListener('keydown', function (e) { window.setTimeout(changeRemainLength, 0); });
		textarea.addEventListener('paste', function (e) { window.setTimeout(changeRemainLength, 0); });
		textarea.addEventListener('cut', function (e) { window.setTimeout(changeRemainLength, 0); });
		textarea.addEventListener('drop', function (e) { window.setTimeout(changeRemainLength, 0); });
	}
});