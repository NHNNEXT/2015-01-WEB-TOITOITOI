var data = [];
//var example = {
// 	"뷰티토이" : [
// 		{"postId":6,"dear":"뷰티토이","content":"슈테판이 간단한 사용법을 알려주었는데 , 일단은 SAP 언어셋팅을 영어로 바꾸는 것부터 해","createdtime":"2015-12-13 14:07:26.0","placeId":0,"likes":0},
// 		{"postId":7,"dear":"뷰티토이","content":"해외에서 한국인 혹은 한국과 조금이라도 관련된 일을 하게 된다면 반갑지 않은 일을 만나게","createdtime":"2015-12-13 14:07:26.0","placeId":0,"likes":0}
// 	], "깃토이" : [
// 		{"postId":9,"dear":"깃토이","content":"잘 되나요?","createdtime":"2015-12-13 14:48:02.0","placeId":0,"likes":0},
// 		{"postId":4,"dear":"깃토이","content":"깃 마스터 짱짱","createdtime":"2015-12-13 14:07:26.0","placeId":0,"likes":0},
// 		{"postId":5,"dear":"깃토이","content":"프론트 대장님","createdtime":"2015-12-13 14:07:26.0","placeId":0,"likes":0}
// 	], "toitoi" : [
// 		{"postId":1,"dear":"toitoi","content":"깃토이, 뷰티토이, 플토이,소토이","createdtime":"2015-12-13 14:07:26.0","placeId":0,"likes":0},
// 		{"postId":2,"dear":"toitoi","content":"윙가르디움 레비오우사","createdtime":"2015-12-13 14:07:26.0","placeId":0,"likes":0},
// 		{"postId":3,"dear":"toitoi","content":"해리포터","createdtime":"2015-12-13 14:07:26.0","placeId":0,"likes":0}
// 	]
//};
var matches = document.body.matchesSelector || document.body.webkitMatchesSelector || document.body.mozMatchesSelector || document.body.msMatchesSelector || document.body.webkitMatchesSelector || document.body.matchesSelector;

renderDearList(getKeys(example));

function getKeys (data) {
	var keys = [];
	for (var key in data) {
		if (example.hasOwnProperty(key)) {
			keys.push(key);
		}
	}
	return keys;
}

function renderDearList (dataList) {
	var dataLen = dataList.length;
	var codes = '';
	for (var i = 0; i < dataLen; i++) {
		codes += '<article><h3>'+dataList[i]+'</h3></article>';
	}
	document.querySelector('#replies').insertAdjacentHTML('beforeend', codes);
}

function getDearListDone () {
	var httpRequest = new XMLHttpRequest();
	httpRequest.onreadystatechange = function(){
	    if (httpRequest.readyState === XMLHttpRequest.DONE) {
			data = JSON.parse(httpRequest.response);
			renderDearList(getKeys(data));
	    }
	};
	httpRequest.open('GET', '/api/place/1/dear/', true);
	httpRequest.send(null);
}
