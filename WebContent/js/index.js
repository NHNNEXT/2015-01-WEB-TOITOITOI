
var matches = document.body.matchesSelector || document.body.webkitMatchesSelector || document.body.mozMatchesSelector || document.body.msMatchesSelector || document.body.webkitMatchesSelector || document.body.matchesSelector;

data = [
	{'dear':'손톱깎이', 'preview':['오리아 안녕 123456789', 'ㅈㄹㅈㅂㄷㄹㅂㅈ 안녕 123456789', 'ㅈㄷㄹㅈㅂㄷ 123456789ㅇ']},
	{'dear':'오리', 'preview':['호호호ewfwef후후후 안녕 123456789', 'ipsum ㅈㄹㅈlorem ipuse 56789', 'lorem ㅈㄷipusum 6789ㅇ']},
	{'dear':'오리2', 'preview':['호호호ewfwef후후후 안녕 123456789', 'ipsum ㅈㄹㅈlorem ipuse 56789', 'lorem ㅈㄷipusum 6789ㅇ']},
	{'dear':'인하대후문', 'preview':['lrem imp789', 'ㅈipsrem islern 6789', 'ㅈㄷqwefqwefwqef wefsdaㅇ']},
	{'dear':'오리3', 'preview':['호호호ewfwef후후후 안녕 123456789', 'ipsum ㅈㄹㅈlorem ipuse 56789', 'lorem ㅈㄷipusum 6789ㅇ']},
	{'dear':'인하대후문2', 'preview':['lrem imp789', 'ㅈipsrem islern 6789', 'ㅈㄷqwefqwefwqef wefsdaㅇ']},
	{'dear':'인하대후문2', 'preview':['lrem imp789', 'ㅈipsrem islern 6789', 'ㅈㄷqwefqwefwqef wefsdaㅇ']},
	{'dear':'인하대후문2', 'preview':['lrem imp789', 'ㅈipsrem islern 6789', 'ㅈㄷqwefqwefwqef wefsdaㅇ']},
	{'dear':'인하대후문2', 'preview':['lrem imp789', 'ㅈipsrem islern 6789', 'ㅈㄷqwefqwefwqef wefsdaㅇ']},
	{'dear':'인하대후문2', 'preview':['lrem imp789', 'ㅈipsrem islern 6789', 'ㅈㄷqwefqwefwqef wefsdaㅇ']},
	{'dear':'오리4', 'preview':['호호호ewfwef후후후 안녕 123456789', 'ipsum ㅈㄹㅈlorem ipuse 56789', 'lorem ㅈㄷipusum 6789ㅇ']},
	{'dear':'인하대후문3', 'preview':['lrem imp789', 'ㅈipsrem islern 6789', 'ㅈㄷqwefqwefwqef wefsdaㅇ']}
];

function addHeaders (dataList) {
	var dataLen = dataList.length;
	var codes = '';
	for (var i = 0; i < dataLen; i++) {
		codes += '<article><header>'+dataList[i]+'</header></article>';
	}
	document.querySelector('#letters').insertAdjacentHTML('beforeend', codes);
}

function hasClass (el, name, reg) {
	if (!el) { return false; }
	if (!reg) reg = new RegExp('(\\s|^)'+name+'(\\s|$)');
	return el.className.match(reg);
}
function addClass (el, name) {
	if (!el) { return; }
	if (!this.hasClass(el, name)) el.className += " "+name;
}
function removeClass (el, name, reg) {
	if (!el) { return; }
	if (!reg) reg = new RegExp('(\\s|^)'+name+'(\\s|$)');
	el.className = el.className.replace(reg, " ").trim();
}
function toggleClass (el, name) {
	if (!el) { return; }
	var reg = new RegExp('(\\s|^)'+name+'(\\s|$)');
	(hasClass(el, name, reg) ? removeClass : addClass)(el, name, reg);
}

function addPreview (dataList, header) {
	var dataLen = dataList.length;
	var codes = '<ul>';
	for (var i = 0; i < dataLen; i++) {
		codes += '<li>'+dataList[i]+'</li>';
	}
	codes += '</ul>';
	header.insertAdjacentHTML('afterend', codes);
}

function findPreview (data, dear) {
	var dataLen = data.length;
	for (var i = 0; i < dataLen; i++) {
		var current = data[i];
		if (current.dear == dear) return current.preview;
	}
}

function cloneElement (el) {
	return JSON.parse(JSON.stringify(el));
}

document.addEventListener("DOMContentLoaded", function() {
	// document.addEventListener("scroll", function (event) {
	// 	var hr = document.querySelector('hr');
	// 	var newletter = document.querySelector('#new-letter');
	// 	var top = parseInt(newletter.offsetTop);
	// 	newletter.style.top = (top - 16)+'px';
	// 	addClass(hr, 'fix');
	// });
	addHeaders(data.map(function (item, index, array) {
		return item.dear;
	}));
	document.querySelector('#letters').addEventListener('click', function (e) {
		var header = e.target;
		if (!header.matches('header')) {
			return;
		}
		var list = header.parentNode.querySelector('ul');

		if (!list) {
			var previews = findPreview(data, header.textContent);
			addPreview(previews, header);
			addClass(header, 'on');
		} else {
			var currentOn = this.querySelector('.on');
			removeClass(currentOn, 'on');
			if (header !== currentOn) {addClass(header, 'on');}
		}
	});
});