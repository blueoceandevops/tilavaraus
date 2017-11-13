<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<t:layout>
	<c:if test="${param.error != null}">
		<div class="alert alert-danger" role="alert">
			<spring:message code="login.error"/>
		</div>
	</c:if>
	<c:if test="${param.logout != null}">
		<div class="alert alert-info" role="alert">
			<spring:message code="login.logout"/>
		</div>
	</c:if>
	<h1><spring:message code="login"/></h1>
	<form method="POST" action="${pageContext.request.contextPath}/login">
		<div class="form-group col-4">
			<label for="username"><spring:message code="user.email"/></label>
			<input name="username" id="username" class="form-control"/>
		</div>
		<div class="form-group col-4">
			<label for="password"><spring:message code="user.password"/></label>
			<input id="password" name="password" type="password" class="form-control"/>
		</div>
		<button type="submit" class="btn btn-primary"><spring:message code="login"/></button>
	</form>
</t:layout>