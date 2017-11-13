<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<t:layout>
	<div class="card-columns">
		<c:forEach items="${rooms}" var="room">
			<div class="card">
				<img class="card-img-top"
				     src="<spring:url value="${room.thumbnailSrc}"/>"
				     alt="Card image cap">
				<div class="card-body">
					<h4 class="card-title">${room.name}</h4>
					<ul class="list-unstyled">
						<li>${room.hourlyPrice}€/h</li>
						<li><spring:message code="maxPersons" arguments="${room.capacity}"/></li>
					</ul>
					<a href="${spring:mvcUrl('FC#roomDetail').arg(0, room).build()}" class="btn btn-primary">
						<spring:message code="reserve"/>
					</a>
				</div>
			</div>
		</c:forEach>
	</div>
</t:layout>



