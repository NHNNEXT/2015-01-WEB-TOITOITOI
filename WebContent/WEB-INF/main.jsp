<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="java.util.*"%>
<%@ page import="cafein.post.Post"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
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
	<header class="top-bar">
		<img class="logo" src="http://i60.tinypic.com/1jujr7.png"></img>
		<img class="search" src="http://i62.tinypic.com/20b0bog.png"></img>
	</header>
	<div class="title">
		<div class="youarein">You're in</div>
		<div class="cafe-name">Coffea</div>
	</div>
	<div class="posting">
		<form action="/createpost" method="post">
			<input class="textbox" name="contents" type="textbox" placeholder="	Q. 이 카페 아메리카노 어때요?">
			<div class="textbox-bg"></div>
			<input type="hidden" name="cid" value="${param.cid}">
			<button class="send">게 시</button>
		</form>
	</div>
	<ul>
		<c:forEach items="${posts}" var="post">
			<li class="post">
				<img class="quatation-up" src="http://i58.tinypic.com/30holtz.png">
				<div class="contents">${post.contents}</div>
				<img class="quatation-down" src="http://i59.tinypic.com/dr46mw.png">
				<div class="info">
					<span class="like-post" value="${post.pid}" name="likesOnPost" action="/likedOnPost">${post.liked}</span>
					<span class="replies-post">${fn:length(post.replyList)}</span>
					<c:set var="date" value="${fn:split(post.creattime, ' ')}" />
					<div class="time">${date[0]}</div>
				</div>
				<ul class="replies">
					<c:forEach items="${post.replyList}" var="reply">
						<li class="reply">
							<span class="reply-content">re: ${reply.replyContent}</span>
							<span class="like-reply" value="${reply.reId}" name ="likesOnReply" action="/likedOnReply" method= "post" >${reply.liked}</span>
						</li>
					</c:forEach>
				</ul>
				<div class="replyBox">
					<form action="/createReply" method="post">
						<input name="reply" type="text" placeholder=" re: 댓글 달기..."> 
						<input name="pid" type="hidden" value="${post.pid}">
						<button>게 시</button>
					</form>
				</div>
			</li>
		</c:forEach>
	</ul>
	<script src="https://code.jquery.com/jquery-1.11.3.min.js"></script>
	<script src="${pageContext.request.contextPath}/js/scripts.js"></script>
</body>
</html>