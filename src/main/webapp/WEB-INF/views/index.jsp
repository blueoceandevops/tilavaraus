<%@ page pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/views/taglibs.jspf" %>

<t:layout>
	<div class="card-columns mt-3">
		<c:forEach items="${rooms}" var="room">
			<div class="card text-warning bg-dark">
				<a href="${spring:mvcUrl('FC#roomDetail').arg(0, room).build()}">
					<img class="card-img-top"
					     src="<spring:url value="${room.thumbnailSrc}"/>"
					     alt="Card image cap">
				</a>
				<div class="card-body">
					<h4 class="card-title">${room.name}</h4>
					<ul class="list-unstyled">
						<li>${room.hourlyPrice}€/h</li>
						<li><spring:message code="maxPersons" arguments="${room.capacity}"/></li>
					</ul>
					<a href="${spring:mvcUrl('FC#roomDetail').arg(0, room).build()}"
					   class="btn btn-outline-light text-uppercase font-weight-bold">
						<spring:message code="reserve"/>
					</a>
				</div>
			</div>
		</c:forEach>
	</div>
</t:layout>



