<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="java.util.*"%>
<%@ page import="cafein.post.Post"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
	request.setCharacterEncoding("UTF-8");
%>
<!doctype html>

<html lang="en">
<head>
<meta charset="utf-8">
<title>cafe In</title>
<meta name="viewport"
	content="width=device-width,initial-scale=1.0,minimum-scale=1.0,maximum-scale=1.0,user-scalable=no">
<!--meta name="description" content="The HTML5 Herald"-->
<!--meta name="author" content="SitePoint"-->
<link rel="stylesheet" href="/css/styles.css">
<!--[if lt IE 9]>
<script src="http://html5shiv.googlecode.com/svn/trunk/html5.js"></script>
<![endif]-->

</head>

<body>
	<header class="top-bar">CafeIn</header>
	<div class="posting">
		<form action="/createpost" method="post">
			<input class="posting-textbox" name="contents" type="text"
				placeholder="이 카페 아메리카노 어때요?">
			<input type="hidden" name="cid" value="${param.cid}">
			<button class="posting-send">게 시</button>
		</form>
	</div>

	<ul>
		<c:forEach items="${posts}" var="post">
			<li class="post">
				<div class="contents">${post.contents}</div>
				<div class="like-post" value="${post.pid}" name="likesOnPost" action="/likedOnPost">${post.liked}</div>
				<div class="time">${post.creattime}</div>
				<button class="replyButton">Re</button>
				<div class="replyBox">
					<form action="/createReply" method="post">
						<input name="reply" type="text" placeholder="댓글 달기..."> <input
							name="pid" type="hidden" value="${post.pid}">
						<button>등록</button>
					</form>
				</div>
				<ul>
					<c:forEach items="${post.replyList}" var="reply">
						<li class="reply">${reply.replyContent}
						<div class="like-reply" value="${reply.reId}" name ="likesOnReply" action="/likedOnReply" method= "post" >${reply.liked}</div></li>
						
					</c:forEach>
				</ul>
			</li>
		</c:forEach>
	</ul>
	<script src="https://code.jquery.com/jquery-1.11.3.min.js"></script>
	<script src="${pageContext.request.contextPath}/js/scripts.js"></script>
</body>
</html>