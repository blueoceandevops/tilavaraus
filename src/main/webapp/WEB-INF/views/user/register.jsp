<%@ page pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/views/taglibs.jspf" %>

<t:layout title="register">
	<div class="row justify-content-md-center mt-3">
		<div class="col-md-6">
			<h1><spring:message code="register.title"/></h1>
			<form:form method="POST" action="${spring:mvcUrl('UC#register').build()}" modelAttribute="user">
				<form:errors cssClass="alert alert-danger"/>
				<div class="form-group">
					<form:label path="email"><spring:message code="user.email"/></form:label>
					<form:input path="email"
					            class="form-control"
					            required="true"
					            type="email"
					            cssErrorClass="form-control is-invalid"/>
					<form:errors path="email" cssClass="invalid-feedback"/>
				</div>
				<div class="form-group">
					<form:label path="password"><spring:message code="user.password"/></form:label>
					<form:password path="password"
					               class="form-control"
					               required="true"
					               cssErrorClass="form-control is-invalid"/>
					<form:errors path="password" cssClass="invalid-feedback"/>
				</div>
				<div class="form-group">
					<form:label path="name"><spring:message code="user.name"/></form:label>
					<form:input path="name"
					            class="form-control"
					            required="true"
					            cssErrorClass="form-control is-invalid"/>
					<form:errors path="name" cssClass="invalid-feedback"/>
				</div>
				<div class="form-group">
					<form:label path="phone"><spring:message code="user.phone"/></form:label>
					<form:input path="phone"
								type="tel"
								class="form-control"
								required="true"
								cssErrorClass="form-control is-invalid"/>
					<form:errors path="phone" cssClass="invalid-feedback"/>
				</div>
				<div class="form-group">
					<form:label path="address"><spring:message code="user.address"/></form:label>
					<form:input path="address"
								class="form-control"
								required="true"
								cssErrorClass="form-control is-invalid"/>
					<form:errors path="address" cssClass="invalid-feedback"/>
				</div>
				<div class="form-row">
					<div class="form-group col-md-8">
						<form:label path="city"><spring:message code="user.city"/></form:label>
						<form:input path="city"
						            class="form-control"
						            required="true"
						            cssErrorClass="form-control is-invalid"/>
						<form:errors path="city" cssClass="invalid-feedback"/>
					</div>
					<div class="form-group col-md-4">
						<form:label path="zip"><spring:message code="user.zip"/></form:label>
						<form:input type="number"
						            path="zip"
						            class="form-control"
						            required="true"
						            cssErrorClass="form-control is-invalid"/>
						<form:errors path="zip" cssClass="invalid-feedback"/>
					</div>
				</div>
				<div class="form-check">
					<label class="form-check-label">
						<input class="form-check-input" type="checkbox" id="n_tos" required>
						<spring:message code="register.acceptTos"/>
					</label>
				</div>
				<button type="submit" class="btn btn-primary"><spring:message code="register.submit"/></button>
			</form:form>
		</div>
	</div>
</t:layout>