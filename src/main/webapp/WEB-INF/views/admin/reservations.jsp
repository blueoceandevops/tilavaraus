<%@ page pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/views/taglibs.jspf" %>

<t:layout>
	<table class="table table-responsive">
		<tr>
			<th><spring:message code="reservation.user"/></th>
			<th><spring:message code="room.name"/></th>
			<th><spring:message code="reservation.time"/></th>
			<th><spring:message code="reservation.personCount"/></th>
			<th><spring:message code="reservation.additionalServices"/></th>
			<th><spring:message code="actions"/></th>
		</tr>
		<c:forEach items="${reservations}" var="reservation">
			<tr>
				<td>${reservation.user.email}</td>
				<td>${reservation.room.name}</td>
				<td><t:formatDate value="${reservation.startTime}"/> - <t:formatDate
						value="${reservation.endTime}"/></td>
				<td>${reservation.personCount}</td>
				<td>
					<ul>
						<c:forEach items="${reservation.additionalServices}" var="additionalService">
							<li><spring:message code="${additionalService}"/></li>
						</c:forEach>
					</ul>
				</td>
				<td>
					<a class="btn btn-danger"
					   href="${spring:mvcUrl('AC#deleteReservations').arg(0, reservation).build()}">
						<spring:message code="cancelReservation"/>
					</a>
					<a class="btn btn-primary"
					   href="${spring:mvcUrl('AC#showReservationEditForm').arg(0, reservation).build()}">
						<spring:message code="editReservation"/>
					</a>
				</td>
			</tr>
		</c:forEach>
	</table>
</t:layout>