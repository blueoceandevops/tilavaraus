<%@ page pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/views/taglibs.jspf" %>

<t:layout title="profile">
	<form:form action="${pageContext.request.contextPath}/users/${user.id}" modelAttribute="user" cssClass="mt-2">
		<form:errors cssClass="alert alert-danger m-3 col"/>

		<div class="form-group">
			<form:label path="email"><spring:message code="user.email"/></form:label>
			<form:input path="email" readonly="true" cssClass="form-control"/>
		</div>
		<div class="form-group">
			<form:label path="password"><spring:message code="user.password"/></form:label>
			<form:password showPassword="true" path="password" readonly="true" cssClass="form-control"
			               value="PlaceH0ld3r123!@$"/>
		</div>
		<div class="form-group">
			<form:label path="name"><spring:message code="user.name"/></form:label>
			<form:input path="name" cssClass="form-control" cssErrorClass="form-control is-invalid"/>
			<form:errors path="name" cssClass="invalid-feedback"/>
		</div>
		<div class="form-group">
			<form:label path="phone"><spring:message code="user.phone"/></form:label>
			<form:input type="tel" path="phone" cssClass="form-control" cssErrorClass="form-control is-invalid"/>
			<form:errors path="phone" cssClass="invalid-feedback"/>
		</div>
		<div class="form-group">
			<form:label path="address"><spring:message code="user.address"/></form:label>
			<form:input path="address" cssClass="form-control" cssErrorClass="form-control is-invalid"/>
			<form:errors path="address" cssClass="invalid-feedback"/>
		</div>
		<div class="form-row">
			<div class="form-group col-md-8">
				<form:label path="city"><spring:message code="user.city"/></form:label>
				<form:input path="city" cssClass="form-control" cssErrorClass="form-control is-invalid"/>
				<form:errors path="city" cssClass="invalid-feedback"/>
			</div>
			<div class="form-group col-md-4">
				<form:label path="zip"><spring:message code="user.zip"/></form:label>
				<form:input path="zip" cssClass="form-control" cssErrorClass="form-control is-invalid"/>
				<form:errors path="zip" cssClass="invalid-feedback"/>
			</div>
		</div>
		<button type="submit" class="btn btn-primary"><spring:message code="save"/></button>
	</form:form>
</t:layout>