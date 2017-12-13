<%@ page pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/views/taglibs.jspf" %>

<t:layout title="allReservations">

	<a class="btn btn-primary mt-3" href="${spring:mvcUrl('RC#newRoom').build()}">
		<spring:message code="add"/>
	</a>

	<table class="table table-responsive mt-3">
		<tr>
			<th><spring:message code="room.name"/></th>
			<th><spring:message code="room.capacity"/></th>
			<th><spring:message code="room.hourlyPrice"/></th>
			<th><spring:message code="actions"/></th>
		</tr>
		<c:forEach items="${rooms}" var="room">
			<tr>
				<td>
					<img src="${room.thumbnailSrc}"/>
						${room.name}
				</td>
				<td>${room.capacity}</td>
				<td>${room.hourlyPrice} €/h</td>
				<td>
					<a class="btn btn-danger"
					   href="${spring:mvcUrl('RC#deleteRoom').arg(0, room).build()}">
						<spring:message code="delete"/>
					</a>
					<a class="btn btn-primary"
					   href="${spring:mvcUrl('RC#editRoom').arg(0, room).build()}">
						<spring:message code="edit"/>
					</a>
				</td>
			</tr>
		</c:forEach>
	</table>
</t:layout>