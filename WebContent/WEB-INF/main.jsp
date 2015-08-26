<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.*" %>
<%@ page import="cafein.post.Post" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<% request.setCharacterEncoding("UTF-8"); %>
<!doctype html>

<html lang="en">
<head>
	<meta charset="utf-8">
	<title>The HTML5 Herald</title>
	<meta name="viewport" content="width=device-width,initial-scale=1.0,minimum-scale=1.0,maximum-scale=1.0,user-scalable=no">
	<!--meta name="description" content="The HTML5 Herald"-->
	<!--meta name="author" content="SitePoint"-->
	<link rel="stylesheet" href="${pageContext.request.contextPath}/css/styles.css">
<!--[if lt IE 9]>
<script src="http://html5shiv.googlecode.com/svn/trunk/html5.js"></script>
<![endif]-->

</head>

<body>
	<div>
		<form action="/createpost" method="post">
			<input name="contents" type="text" placeholder="이 카페 아메리카노 어때요?">		
			<button>올리기</button>
		</form>
	</div>
	<ul>
	<!--%
	ArrayList<Post> posts = (ArrayList<Post>)request.getAttribute("posts");
	Iterator<Post> iterator = posts.iterator();
    while (iterator.hasNext()) {
        out.print("<li>");
    	Post post = iterator.next();
        out.print(post.getPid()+", ");
        out.print(post.getContents()+", ");
        out.print(post.getCreattime());
        out.println("</li>");
    }
	%-->
	<c:forEach items="${posts}" var="post">
		<li class="post">
			<div class="contents">${post.contents}</div>
			<div class="like">${post.liked}</div>
			<div class="time">${post.creattime}</div>
		</li>
	</c:forEach>
	</ul>
	<script src="js/scripts.js"></script>
</body>
</html>