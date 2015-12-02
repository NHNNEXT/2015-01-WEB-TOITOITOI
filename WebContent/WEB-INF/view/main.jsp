<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="java.util.*"%>
<%@ page import="cafein.post.Post"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
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
		<a class="logo" href="/"></a> <img class="search"
			src="http://i62.tinypic.com/20b0bog.png"></img>
	</header>
	<header class="title-posting">
		<div class="title">
			<div class="youarein">You're in</div>
			<div class="cafe-name">Coffea</div>
		</div>
		<div class="posting">
		
			<%-- <form:form modelAttribute="newpost" cssClass="posting-form" action="/createpost" method="post">
				<form:input path="contents" placeholder="Q. 이 카페 아메리카노 어때요?" />
				<div class="textbox-bg"></div>
				<form:hidden path="cid" />
				<button class="send">게시</button>
			</form:form> --%>
			
			<form class="posting-form" action="/createpost" method="post">
				<input class="textbox" name="contents" type="textbox"
					placeholder="	Q. 이 카페 아메리카노 어때요?">
				<div class="textbox-bg"></div>
				<input class="cid" type="hidden" name="cid" value="${param.cid}">
				<button class="send">게시</button>
			</form>
		</div>
	</header>
	<ul class="posts"></ul>
	<script src="https://code.jquery.com/jquery-1.11.3.min.js"></script>
	<script type="text/javascript"
		src="http://ajax.googleapis.com/ajax/libs/jquery/1.10.2/jquery.min.js"></script>
	<script src="${pageContext.request.contextPath}/js/scripts.js"></script>
</body>
</html>
