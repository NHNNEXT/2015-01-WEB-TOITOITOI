<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.*"%>
<%@ page import="cafein.cafe.Place"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html>
<head>
	<meta charset="utf-8">
	<title>Dear Here</title>
	<meta name="viewport" content="width=device-width,initial-scale=1.0,minimum-scale=1.0,maximum-scale=1.0,user-scalable=no">
	<link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath}/css/index.css">
	<script defer src="${pageContext.request.contextPath}/js/index.js"></script>
</head>
<body>
	<hr>
	<header>
		<h1>Dear Here</h1>
		<p><span class="emphasis">인하대 정문</span>의 무언가에게 편지 한 통 써보세요!</p>
	</header>
	<section id="new-letter">
		<form>
			<label class="dear-label" for="dear-input">Dear.</label>
			<input id="dear-input" type="text" name="dear">
			<textarea></textarea>
			<button>SEND</button>
		</form>
	</section>
	<section id="letters">
		<article>
			<header>샘플</header>
			<ul>
				<li>2샘플아 안녕..?ㅂㅈㄷㄹㅂㅈ대렂ㄷㄹㄹ</li>
				<li>1샘플아 안녕..?ㅂㅈㄷㄹㅂㅈ대렂ㄷㄹㄹ</li>
				<li>3샘4플아 안녕..?ㅂㅈㄷㄹㅂㅈ대렂ㄷㄹㄹ</li>
				<li>5샘2플아 안녕..?ㅂㅈㄷㄹㅂㅈ대렂ㄷㄹㄹ</li>
			</ul>
		</article>
	</section>
</body>
</html>
 