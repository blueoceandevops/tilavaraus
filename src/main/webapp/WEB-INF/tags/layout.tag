<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
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
	<meta name="theme-color" content="#000000">
	<link rel="shortcut icon" type="image/x-icon" href="${pageContext.request.contextPath}/favicon.ico"/>
	<link rel="manifest" href="${pageContext.request.contextPath}/manifest.json">
	<title><spring:message code="${empty title ? 'title' : title}" text="${title}"/></title>
	<link rel="stylesheet" href="${pageContext.request.contextPath}/dist/main.css">
	<link rel="stylesheet" href="${pageContext.request.contextPath}/outdatedbrowser.css">
	<jsp:invoke fragment="head"/>
	<style>
		html, body {
			height: 100%;
		}
	</style>
</head>
<body>

<div class="d-flex flex-column" style="height: 100%">
	<div>
		<jsp:include page="/WEB-INF/views/_header.jsp"/>
	</div>
	<div class="${noContainer ? '' : 'container'}">
		<jsp:doBody/>
	</div>
	<div class="mt-auto">
		<jsp:include page="/WEB-INF/views/_footer.jsp"/>
	</div>

</div>
<%@ include file="/WEB-INF/views/_outdatedbrowser.jsp" %>
<script>
	<security:authorize access="isFullyAuthenticated()">
	window.userEmail = '<security:authentication property="principal.username" htmlEscape="false" />';
	</security:authorize>
</script>
<script src="${pageContext.request.contextPath}/dist/main.js"></script>
<jsp:invoke fragment="scripts"/>
</body>
</html>