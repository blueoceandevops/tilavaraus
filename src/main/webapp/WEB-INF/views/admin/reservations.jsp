<%@ page pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/views/taglibs.jspf" %>

<t:layout title="allReservations">
	<c:if test="${empty reservations}">
		<div class="text-center mt-3">
			<p class="h1 text-muted"><spring:message code="noReservations"/></p>
		</div>
	</c:if>
	<c:if test="${not empty reservations}">
		<table class="table table-responsive mt-3">
			<tr>
				<th><spring:message code="reservation.user"/></th>
				<th><spring:message code="room.name"/></th>
				<th><spring:message code="reservation.time"/></th>
				<th><spring:message code="reservation.personCount"/></th>
				<th><spring:message code="reservation.additionalServices"/></th>
				<th><spring:message code="reservation.totalPrice"/></th>
				<th><spring:message code="reservation.notes"/></th>
				<th><spring:message code="actions"/></th>
			</tr>
			<c:forEach items="${reservations}" var="reservation">
				<tr>
					<td>
						<a class="text-info" href="${spring:mvcUrl('AC#showUser').arg(0, reservation.user).build()}">
								${reservation.user.email}
						</a>
					</td>
					<td>${reservation.room.name}</td>
					<td><t:formatDate value="${reservation.startTime}"/> - <t:formatDate
						value="${reservation.endTime}"/></td>
					<td>${reservation.personCount}</td>
					<td>
						<ul>
							<c:forEach items="${reservation.additionalServices}" var="additionalService">
								<li><spring:message code="additionalService.name.${additionalService.name}"/></li>
							</c:forEach>
						</ul>
					</td>
					<td>${reservation.totalPrice} €</td>
					<td><c:out value="${reservation.notes}"/></td>
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
	</c:if>
</t:layout>