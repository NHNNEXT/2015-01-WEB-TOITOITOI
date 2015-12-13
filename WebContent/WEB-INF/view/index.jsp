<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.*"%>
<%@ page import="cafein.cafe.Place"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<% request.setCharacterEncoding("UTF-8"); %>

<!DOCTYPE html>
<html>
<head>
	<meta charset="utf-8">
	<title>Dear Here</title>
	<meta name="viewport" content="width=device-width,initial-scale=1.0,minimum-scale=1.0,maximum-scale=1.0,user-scalable=no">
	<link type="text/css" rel="stylesheet" href="/css/index.css">
	<script defer src="/js/index.js"></script>
	<script defer src="/js/elastic.js"></script>
</head>
<body>
	<hr class="top-bar">
	<header>
		<h1>Dear Here</h1>
		<p><span class="emphasis">${place.name}</span>의 무언가에게 편지 한 통 써보세요!</p>
	</header>
	<section id="new-letter" class="the-letter">
		<h2 class="hidden-heading">new letter</h2>
		<form action="/api/place/${place.placeId}/post">
			<input type="hidden" name="placeId" value="${place.placeId}">
			<label class="dear-label" for="dear-input">Dear.</label>
			<input id="dear-input" type="text" name="dear">
			<textarea name="content"></textarea>
			<button>SEND</button>
		</form>
	</section>
	<section id="letters">
		<h2 class="hidden-heading">letters</h2>
		<div class="dummy"></div>
		<div class="dummy"></div>
		<div class="dummy"></div>
	</section>
</body>
</html>
 