<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="java.util.*"%>
<%@ page import="cafein.cafe.Cafe"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!doctype html>

<html lang="en">
<head>
<meta charset="utf-8">

<title>cafe in</title>
<meta name="description" content="cafe post wall">
<meta name="author" content="toitoitoi">
<meta name="viewport" content="width=device-width,initial-scale=1.0,minimum-scale=1.0,maximum-scale=1.0,user-scalable=no">
<link rel="stylesheet" type="text/css" href="/css/index.css">

<!--[if lt IE 9]>
<script src="http://html5shiv.googlecode.com/svn/trunk/html5.js"></script>
<![endif]-->

</head>
<body>
	<img class="logo" src="http://i62.tinypic.com/10f9eo4.png">
	<!-- <ul class="filter">
		<li><a href="/?sort=postNum">댓글순</a></li>
	</ul> -->
	<form class="search" name="search" method="get" action="/searchcafe">
		<input class="textbox" type="text" name="keyword" placeholder="카페 이름을 검색해주세요." value="${keyword}"/> 
		<input id="search-button" type="submit" value="검색"/>
		<label for="search-button"></label>
		<div class="search-bg"></div>
	</form>
	<ul class="cafe-list">
		<c:forEach items="${cafeList}" var="cafe">
			<li>
				<img src="http://placehold.it/80x80">
				<a class = "info" href="/cafe?cid=${cafe.cid}">
					<span class="name">${cafe.name}</span>
					<span class="post-num"><b>POST</b><br>${cafe.postNum}개</span>
					<span class="address">성남시 분당구 삼평동</span>
					<span class="distance">0.3km</span>
				</a>
			</li>
		</c:forEach>
	</ul>
	<!-- <div class="cafe-list-bg"></div>  -->
	<script src="https://code.jquery.com/jquery-1.11.3.min.js"></script>
	<script src="${pageContext.request.contextPath}/js/scripts.js"></script>
</body>
</html>