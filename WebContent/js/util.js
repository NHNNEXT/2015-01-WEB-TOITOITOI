
var matches = document.body.matchesSelector || document.body.webkitMatchesSelector || document.body.mozMatchesSelector || document.body.msMatchesSelector || document.body.webkitMatchesSelector || document.body.matchesSelector;

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
