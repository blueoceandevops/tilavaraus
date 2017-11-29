<%@ page pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/views/taglibs.jspf" %>

<t:layout title="myReservations">
	<h1 class="mt-3"><spring:message code="myReservations"/></h1>
	<c:if test="${empty reservations}">
		<div class="text-center">
			<p class="h1 text-muted"><spring:message code="noReservations"/></p>
		</div>
	</c:if>
	<c:if test="${not empty reservations}">
		<table class="table table-responsive">
			<tr>
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
					<td>${reservation.room.name}</td>
					<td>
						<t:formatDate value="${reservation.date}"/> ${reservation.startTime} - ${reservation.endTime}
					</td>
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
						<a class="btn btn-danger${reservation.cancellable ? '' : ' disabled'}"
						   href="${spring:mvcUrl('FC#cancelReservation').arg(0, reservation).build()}">
							<spring:message code="cancelReservation"/>
						</a>
					</td>
				</tr>
			</c:forEach>
		</table>
	</c:if>
</t:layout>



