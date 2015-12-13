<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.*"%>
<%@ page import="cafein.post.Post"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<% request.setCharacterEncoding("UTF-8"); %>

<!DOCTYPE html>
<html>
<head>
	<meta charset="utf-8">
	<title>Dear. ${post.dear}</title>
	<meta name="viewport" content="width=device-width,initial-scale=1.0,minimum-scale=1.0,maximum-scale=1.0,user-scalable=no">
	<link type="text/css" rel="stylesheet" href="/css/index.css">
	<script defer src="/js/elastic.js"></script>
	<script defer src="/js/main.js"></script>
</head>
<body>
	<nav class="top-bar"><a id="go-back" href="#" onclick="history.back();">뒤로가기</a></nav>
	<section class="the-letter">
		<div>
			<label class="dear-label" for="dear-input">Dear.</label>
			<img id="stamp" src="http://i64.tinypic.com/2v33m9l.png">
			<input id="dear-input" readonly value="${post.dear}">
			<textarea name="content" readonly>${post.content}</textarea>
		</div>
	</section>
	<section id="replies">
	</section>
</body>
</html>
