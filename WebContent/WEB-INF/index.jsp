<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="java.util.*"%>
<%@ page import="cafein.cafe.Cafe"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!doctype html>

<html lang="en">
<head>
<meta charset="utf-8">

<title>The HTML5 Herald</title>
<meta name="description" content="The HTML5 Herald">
<meta name="author" content="SitePoint">
<meta name="viewport"
	content="width=device-width,initial-scale=1.0,minimum-scale=1.0,maximum-scale=1.0,user-scalable=no">
<link rel="stylesheet" type="text/css" href="/css/index.css">

<!--[if lt IE 9]>
<script src="http://html5shiv.googlecode.com/svn/trunk/html5.js"></script>
<![endif]-->

</head>
<body>
	<a href="/cafe?cid=하이">gogogo</a>

	<h1>CafeIn</h1>
	<form name="search" method="get" action="/searchcafe">
		<input type="text" name="keyword" placeholder="카페 이름을 검색해주세요." value="${keyword}"/> <input type="submit" value="검색" />
	</form>

	<hr>
	<ul class="cafe-list">
		<c:forEach items="${cafeList}" var="cafe">
			<li><img src="http://placehold.it/80x80">
				<div class="cid">${cafe.cid}</div>
				<div class="name">${cafe.name}</div>
				<div class="post-num">포스트 3개</div>
				<div class="address">성남시 분당구 삼평동</div>
				<div class="distance">0.3km</div></li>
		</c:forEach>
		<!-- 
			<li>
				<img src="http://placehold.it/80x80">
				<div class="name">김다방</div>
				<div class="post-num">포스트 23개</div>
				<div class="address">토이시 토이구 토이동</div>
				<div class="distance">0.4km</div>
			</li>
			<li>
				<img src="http://placehold.it/80x80">
				<div class="name">박다방</div>
				<div class="post-num">포스트 300개</div>
				<div class="address">성남시 분당구 삼평동</div>
				<div class="distance">1.0km</div>
			</li>
			<li>
				<img src="http://placehold.it/80x80">
				<div class="name">정다방</div>
				<div class="post-num">포스트 1234개</div>
				<div class="address">성남시 분당구 삼평동</div>
				<div class="distance">48.8km</div>
			</li>
		-->
	</ul>
	<script src="https://code.jquery.com/jquery-1.11.3.min.js"></script>
	<script src="${pageContext.request.contextPath}/js/scripts.js"></script>
</body>
</html>