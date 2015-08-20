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
	<div>
		<form action="/createPost" method="post">
			<input type="hidden" value="${post.pid}" name="pId">
			<input name="contents" type="text" placeholder="이 카페 아메리카노 어때요?">		
			<button>올리기</button>
		</form>
	</div>
	<div>
		<form action="/createReply" method="post">
			<input name="reply" type="text" placeholder="댓글 달기...">		
			<button>등록</button>
		</form>
	</div>
	<script src="js/scripts.js"></script>
</body>
</html>