<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<nav class="navbar navbar-expand-sm bg-dark navbar-dark mb-4">
	<div class="container">
		<a class="navbar-brand" href="${spring:mvcUrl('FC#index').build()}">
			<img class="mr-2" src="https://www.xamk.fi/wp-content/themes/xamk/dist/images/logo_header.png"
			     width="150px"
			     alt="<spring:message code="title"/>">
		</a>
		<button class="navbar-toggler"
		        type="button"
		        data-toggle="collapse"
		        data-target="#navbarNavDropdown"
		        aria-controls="navbarNavDropdown"
		        aria-expanded="false"
		        aria-label="Toggle navigation">
			<span class="navbar-toggler-icon"></span>
		</button>
		<div class="collapse navbar-collapse" id="navbarNavDropdown">
			<ul class="ml-auto nav navbar-nav navbar-right">
				<security:authorize access="isAuthenticated()">
					<li class="nav-item dropdown">
						<a class="nav-link dropdown-toggle text-uppercase font-weight-bold"
						   href="#"
						   id="navbarDropdownMenuLink"
						   data-toggle="dropdown"
						   aria-haspopup="true"
						   aria-expanded="false">
							<security:authentication property="principal.username"/>
						</a>
						<div class="dropdown-menu" aria-labelledby="navbarDropdownMenuLink">
							<a class="dropdown-item"
							   href="${spring:mvcUrl('UC#showProfile').build()}">
								<spring:message code="profile"/>
							</a>
							<a class="dropdown-item"
							   href="${spring:mvcUrl('FC#myReservations').build()}">
								<spring:message code="myReservations"/>
							</a>
							<security:authorize access="hasRole('ADMIN')">
								<a class="dropdown-item"
								   href="${spring:mvcUrl('AC#listReservations').build()}">
									<spring:message code="allReservations"/>
								</a>
							</security:authorize>
							<form:form action="${pageContext.request.contextPath}/logout">
								<button type="submit" class="dropdown-item">
									<spring:message code="logout"/>
								</button>
							</form:form>
						</div>
					</li>
				</security:authorize>
				<security:authorize access="isAnonymous()">
					<li class="nav-item text-uppercase font-weight-bold">
						<a class="nav-link" href="${spring:mvcUrl('UC#showRegisterForm').build()}">
							<spring:message code="register"/>
						</a>
					</li>
					<li class="nav-item text-uppercase font-weight-bold">
						<a class="nav-link" href="${pageContext.request.contextPath}/login">
							<spring:message code="login"/>
						</a>
					</li>
				</security:authorize>
			</ul>
		</div>
	</div>
</nav>
