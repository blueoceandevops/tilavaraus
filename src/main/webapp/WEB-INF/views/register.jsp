<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<t:layout>
	<h1><spring:message code="register.title"/></h1>
	<form:form method="POST" action="${spring:mvcUrl('UC#register').build()}" modelAttribute="user">
		<div class="form-group col-4">
			<form:label path="email"><spring:message code="user.email"/></form:label>
			<form:input path="email" class="form-control" required="true"/>
		</div>
		<div class="form-group col-4">
			<form:label path="password"><spring:message code="user.password"/></form:label>
			<form:password path="password" class="form-control" required="true"/>
		</div>
		<div class="form-check col-4">
			<label class="form-check-label">
				<input class="form-check-input" type="checkbox" id="n_tos" required>
				<spring:message code="register.acceptTos"/>
			</label>
		</div>
		<button type="submit" class="btn btn-primary"><spring:message code="register.submit"/></button>
	</form:form>
</t:layout>