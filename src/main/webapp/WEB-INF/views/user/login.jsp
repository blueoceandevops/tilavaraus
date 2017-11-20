<%@ page pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/views/taglibs.jspf" %>

<t:layout>
	<div class="row justify-content-md-center">
		<div class="col-md-6">
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
			<form:form method="POST" action="${pageContext.request.contextPath}/login">
				<div class="form-group">
					<label for="username"><spring:message code="user.email"/></label>
					<input name="username" id="username" class="form-control" required/>
				</div>
				<div class="form-group">
					<label for="password"><spring:message code="user.password"/></label>
					<input id="password" name="password" type="password" class="form-control" required/>
				</div>
				<button type="submit" class="btn btn-primary"><spring:message code="login"/></button>
			</form:form>
		</div>
	</div>
</t:layout>