document.addEventListener("DOMContentLoaded", function() {
	var textarea = document.querySelector('textarea');
	function resizeVertical () {
		var x = window.scrollX, y = window.scrollY;
		textarea.style.height = 'auto';
		textarea.style.height = parseInt(textarea.scrollHeight)+'px';
		window.scrollTo(x, y);
	}
	resizeVertical();
	textarea.addEventListener('change', resizeVertical);
	textarea.addEventListener('keydown', function (e) { window.setTimeout(resizeVertical); });
	textarea.addEventListener('paste', function (e) { window.setTimeout(resizeVertical); });
	textarea.addEventListener('cut', function (e) { window.setTimeout(resizeVertical); });
	textarea.addEventListener('drop', function (e) { window.setTimeout(resizeVertical); });
});