<%@page language="java" pageEncoding="UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<t:layout>
	<form:form modelAttribute="user">
		<div class="form-group">
			<form:label path="email"><spring:message code="user.email"/></form:label>
			<form:input path="email" disabled="true" cssClass="form-control"/>
		</div>
	</form:form>
</t:layout>