<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ tag description="Layout" pageEncoding="UTF-8" %>
<%@ attribute name="head" fragment="true" %>
<%@ attribute name="scripts" fragment="true" %>
<%@ attribute name="title" %>
<%@ attribute name="noContainer" type="java.lang.Boolean" %>

<!doctype html>
<html lang="en">
<head>
	<meta charset="UTF-8">
	<meta name="viewport"
	      content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
	<meta http-equiv="X-UA-Compatible" content="ie=edge">
	<meta name="theme-color" content="#fbba18">
	<link rel="manifest" href="${pageContext.request.contextPath}/manifest.json">
	<title><spring:message code="${empty title ? 'title' : title}" text="${title}"/></title>
	<link rel="stylesheet" href="${pageContext.request.contextPath}/dist/main.css">
	<jsp:invoke fragment="head"/>
</head>
<body>
<jsp:include page="/WEB-INF/views/_header.jsp"/>
<div class="${noContainer ? '' : 'container'}">
	<jsp:doBody/>
</div>
<jsp:include page="/WEB-INF/views/_footer.jsp"/>
<script src="${pageContext.request.contextPath}/dist/main.js"></script>
<jsp:invoke fragment="scripts"/>
</body>
</html>