<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.*" %>
<% request.setCharacterEncoding("UTF-8"); %>
<!doctype html>

<html lang="en">
<head>
	<meta charset="utf-8">

	<title>The HTML5 Herald</title>
	<meta name="description" content="The HTML5 Herald">
	<meta name="author" content="SitePoint">

	<link rel="stylesheet" href="css/styles.css?v=1.0">

<!--[if lt IE 9]>
<script src="http://html5shiv.googlecode.com/svn/trunk/html5.js"></script>
<![endif]-->

</head>

<body>
	<header class="top-bar">CafeIn</header>
	<div class = "posting">
		<form action="/createpost" method="post">
			<input class="posting-textbox" name="contents" type="text" placeholder="  이 카페 아메리카노 어때요?">		
			<button class="posting-send">게 시</button>
		</form>		
	</div>
	<div class="q2-contents">
		<p>원칙적으로 모든 XML 텍스트들은 XML parser에 의해 파싱된다.</p>
		<p>그런데 스크립트는 통상적인 XHTML 언어가 아니므로, 파싱할 때 오류가 발생한다. </p>
		<p>즉, 파싱되지 않도록 방지해야 하는데, 이러한 데이터를 “CDATA(unparsed Character Data)”라고 한다. </p>
		<p>CDATA 작성 방식은 문제에 나와있다. 양 옆을 “< >”로 묶는 이유는,  “< >“가 데이터가 아닌 태그의 시작으로 해석되기 때문이다.</p>
		<p>또한 //로 주석처리한 이유는, CDATA[]로 묶어주는 부분이 스크립트 문법에 맞지 않기 때문이다. 즉, 브라우저가 이 부분을 해석하지 못하는 경우에 대비하기 위해서이다.</p>
	</div>
	<div class="q2-contents">
		<p>원칙적으로 모든 XML 텍스트들은 XML parser에 의해 파싱된다.</p>
		<p>그런데 스크립트는 통상적인 XHTML 언어가 아니므로, 파싱할 때 오류가 발생한다. </p>
		<p>즉, 파싱되지 않도록 방지해야 하는데, 이러한 데이터를 “CDATA(unparsed Character Data)”라고 한다. </p>
		<p>CDATA 작성 방식은 문제에 나와있다. 양 옆을 “< >”로 묶는 이유는,  “< >“가 데이터가 아닌 태그의 시작으로 해석되기 때문이다.</p>
		<p>또한 //로 주석처리한 이유는, CDATA[]로 묶어주는 부분이 스크립트 문법에 맞지 않기 때문이다. 즉, 브라우저가 이 부분을 해석하지 못하는 경우에 대비하기 위해서이다.</p>
	</div>
	<div class="q2-contents">
		<p>원칙적으로 모든 XML 텍스트들은 XML parser에 의해 파싱된다.</p>
		<p>그런데 스크립트는 통상적인 XHTML 언어가 아니므로, 파싱할 때 오류가 발생한다. </p>
		<p>즉, 파싱되지 않도록 방지해야 하는데, 이러한 데이터를 “CDATA(unparsed Character Data)”라고 한다. </p>
		<p>CDATA 작성 방식은 문제에 나와있다. 양 옆을 “< >”로 묶는 이유는,  “< >“가 데이터가 아닌 태그의 시작으로 해석되기 때문이다.</p>
		<p>또한 //로 주석처리한 이유는, CDATA[]로 묶어주는 부분이 스크립트 문법에 맞지 않기 때문이다. 즉, 브라우저가 이 부분을 해석하지 못하는 경우에 대비하기 위해서이다.</p>
	</div>
	<script src="index.js"></script>
</body>
</html>