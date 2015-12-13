	var textarea = document.querySelector('textarea');
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
		textarea.addEventListener('keydown', function (e) { window.setTimeout(resizeVertical); });
		textarea.addEventListener('paste', function (e) { window.setTimeout(resizeVertical); });
		textarea.addEventListener('cut', function (e) { window.setTimeout(resizeVertical); });
		textarea.addEventListener('drop', function (e) { window.setTimeout(resizeVertical); });
	}
});