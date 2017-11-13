<%@page language="java" pageEncoding="UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>

<t:layout>
	<table class="table table-responsive">
		<tr>
			<th><spring:message code="reservation.user"/></th>
			<th><spring:message code="room.name"/></th>
			<th><spring:message code="reservation.startTime"/></th>
			<th><spring:message code="reservation.endTime"/></th>
			<th><spring:message code="reservation.personCount"/></th>
			<th><spring:message code="reservation.additionalServices"/></th>
			<th><spring:message code="actions"/></th>
		</tr>
		<c:forEach items="${reservations}" var="reservation">
			<tr>
				<td>${reservation.user}</td>
				<td>${reservation.room.name}</td>
				<td>${reservation.startTime}</td>
				<td>${reservation.endTime}</td>
				<td>${reservation.personCount}</td>
				<td>
					<ul>
						<c:forEach items="${reservation.additionalServices}" var="additionalService">
							<li>${additionalService}</li>
						</c:forEach>
					</ul>
				</td>
				<td>
					<a class="btn btn-danger"
					   href="${spring:mvcUrl('FC#deleteReservations').arg(0, reservation).build()}">
						<spring:message code="cancelReservation"/>
					</a>
				</td>
			</tr>
		</c:forEach>
	</table>
</t:layout>