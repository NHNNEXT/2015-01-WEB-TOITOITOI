function Dear (dearDataObject) {
	this.dearId = dearDataObject.dear_id;
	this.name = dearDataObject.name;
	this.maxPostNum = dearDataObject['COUNT(post.id)']; // change property name
	this.currentPage = 0;
	this.posts = [];
}
Dear.prototype.render = function () {
	// body...
};


function DearList (placeId, maxDearNum) {
	this.placeId = placeId;
	this.maxDearNum = maxDearNum;
	this.currentPage = 0;
	this.dears = []; // which is sorted by postNum DESC
}
DearList.prototype.render = function () {
	var dataLen = this.dears.length;
	var codes = '';
	for (var i = 0; i < dataLen; i++) {
		codes += '<article><h3 data-index="'+i+'">'+this.dears[i].name+'</h3></article>';
	}
	document.querySelector('#letters').insertAdjacentHTML('beforeend', codes);
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
				this.dears.push(new Dear(item));
			}.bind(this));
			this.render();
	    }
	}.bind(this);
	httpRequest.open('GET', requestURL, true);
	httpRequest.send(null);
};

function addPreview (dataList, header) {
	if (!dataList || !header || !dataList.length) {
		return;
	}

	var dataLen = dataList.length;
	var codes = '<ul>';
	for (var i = 0; i < dataLen; i++) {
		var data = dataList[i];
		codes += '<li><a href="'+window.location.pathname+'/dear/'+data.dear+'/post/'+data.postId+'">'+data.preview+'</a></li>';
	}
	codes += '</ul>';
	header.insertAdjacentHTML('afterend', codes);
}

function findPreview (data, dear) {
	var posts;
	if (!data || !(posts = data[dear]) || !posts.length) {
		return null;
	}
	var result = [];
	for (var i = 0; i < posts.length; i++) {
		var post = posts[i];
		result.push({'dear':post.dear, 'preview':post.content, 'postId':post.postId});
	}
	return result;
}

function cloneElement (el) {
	return JSON.parse(JSON.stringify(el));
}

// function getDearListDone () {
// 	var httpRequest = new XMLHttpRequest();
// 	var requestURL = '/api/place/1/dear?page=1';
// 	httpRequest.onreadystatechange = function(){
// 	    if (httpRequest.readyState === XMLHttpRequest.DONE) {
// 			var received = JSON.parse(httpRequest.response);
// 			if (!received.success) {
// 				console.error('something went wrong @'+requestURL);
// 				debugger;
// 				return;
// 			}
// 			data.dears = received.result;
// 			renderDearList(data.dears.map(function (el, index, array) {
// 				return el.name;
// 			}));
// 	    }
// 	};
// 	httpRequest.open('GET', requestURL, true);
// 	httpRequest.send(null);
// }

var dearList = new DearList (1, 10);

document.addEventListener("DOMContentLoaded", function() {
	dearList.getNextPageDears();

	document.querySelector('#new-letter form').addEventListener('submit', function (e) {
		e.preventDefault();
		console.log(this);
		var placeId = this.querySelector('input[name="placeId"]').value;
		var dear = this.querySelector('input[name="dear"]').value;
		var content = this.querySelector('textarea[name="content"]').value;
		var form = this;
		if (!dear || !content || !placeId) return;
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
		var header = e.target;
		if (!header.matches('h3')) {
			if (header.matches('article')) {
				header = header.querySelector('h3');
				console.log(header);
			} else {
				return;
			}
		}
		var prevOn = this.querySelector('.on');
		removeClass(prevOn, 'on');
		if (header === prevOn) { return; }

		addClass(header, 'on');
		var list = header.parentNode.querySelector('ul');
		if (!list) {
			var dear = header.textContent;
			getDearListDone();
			var previews = findPreview(data, dear);
			addPreview(previews, header);
		}
	});
});


function updateReplies(reply){
	console.log(reply);
	var code = '<article class="reply">re : '+reply.content+'</article>'
	document.querySelector('#replies').insertAdjacentHTML('afterbegin', code);
}