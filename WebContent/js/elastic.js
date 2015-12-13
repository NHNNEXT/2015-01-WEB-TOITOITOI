	var textarea = document.querySelector('textarea');
	console.log(textarea);
document.addEventListener("DOMContentLoaded", function() {
	function resizeVertical () {
		var x = window.scrollX, y = window.scrollY;
		textarea.style.height = 'auto';
		textarea.style.height = parseInt(textarea.scrollHeight)+'px';
		window.scrollTo(x, y);
	}
	resizeVertical();
	window.addEventListener('resize', resizeVertical);
	if (!textarea.readOnly) {
		textarea.addEventListener('change', resizeVertical);
		textarea.addEventListener('keydown', function (e) { window.setTimeout(resizeVertical, 0); });
		textarea.addEventListener('paste', function (e) { window.setTimeout(resizeVertical, 0); });
		textarea.addEventListener('cut', function (e) { window.setTimeout(resizeVertical, 0); });
		textarea.addEventListener('drop', function (e) { window.setTimeout(resizeVertical, 0); });
	}
});