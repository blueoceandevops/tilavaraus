<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<jsp:useBean id="rooms" scope="request" type="java.util.List<fi.xamk.tilavaraus.domain.Room>"/>

<t:layout>
	<jsp:attribute name="head">
		<style>
			a.link-unstyled, a.link-unstyled:hover {
				color: inherit;
				text-decoration: inherit;
			}
		</style>
	</jsp:attribute>
	<jsp:body>
		<div class="card-columns">
			<c:forEach items="${rooms}" var="room">
				<a href="${pageContext.request.contextPath}/rooms/${room.id}" class="link-unstyled">
					<div class="card">
						<img class="card-img-top"
						     src="${room.thumbnailSrc}"
						     alt="Card image cap">
						<div class="card-body">
							<h4 class="card-title">${room.name}</h4>
							<ul class="list-unstyled">
								<li>${room.hourlyPrice}€/h</li>
								<li><spring:message code="maxPersons" arguments="${room.capacity}"/></li>
							</ul>
							<a href="${pageContext.request.contextPath}/rooms/${room.id}" class="btn btn-primary">
								<spring:message code="reserve"/>
							</a>
						</div>
					</div>
				</a>
			</c:forEach>
		</div>
	</jsp:body>
</t:layout>



