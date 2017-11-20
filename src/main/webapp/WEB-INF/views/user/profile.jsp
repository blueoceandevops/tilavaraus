<%@ page pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/views/taglibs.jspf" %>

<t:layout>
	<form:form modelAttribute="user" cssClass="mt-4">
		<div class="form-group">
			<form:label path="email"><spring:message code="user.email"/></form:label>
			<form:input path="email" disabled="true" cssClass="form-control"/>
		</div>
	</form:form>
</t:layout>