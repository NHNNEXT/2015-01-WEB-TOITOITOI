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
	<link type="image/x-icon" rel="shortcut icon" href="img/favicon.ico" />
	<link type="text/css" rel="stylesheet" href="/css/index.css">
	<script src="https://cdnjs.cloudflare.com/ajax/libs/classlist/2014.01.31/classList.min.js"></script>
	<script defer src="/js/compiled.js"></script>
	<script>
		(function(i,s,o,g,r,a,m){i['GoogleAnalyticsObject']=r;i[r]=i[r]||function(){
		(i[r].q=i[r].q||[]).push(arguments)},i[r].l=1*new Date();a=s.createElement(o),
		m=s.getElementsByTagName(o)[0];a.async=1;a.src=g;m.parentNode.insertBefore(a,m)
		})(window,document,'script','//www.google-analytics.com/analytics.js','ga');

		ga('create', 'UA-71768526-1', 'auto');
		ga('send', 'pageview');
	</script>
</head>
<body>
	<hr class="top-bar">
	<header>
		<h1>Dear Here</h1>
		<p><span class="emphasis">${place.name}</span>의 누군가에게 편지 한 통 써보세요!</p>
	</header>
	<div id="letter-box"></div>

	<section id="letters">
		<h2 class="hidden">letters</h2>
		<button class="more">더 보기</button>
	</section>
</body>
</html>
 