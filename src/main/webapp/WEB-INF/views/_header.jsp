<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>

<security:authorize access="isAuthenticated()">
	<p>authenticated as <security:authentication property="principal.username"/></p>
</security:authorize>
<security:authorize access="isAnonymous()">
	<p>Not logged in</p>
</security:authorize>