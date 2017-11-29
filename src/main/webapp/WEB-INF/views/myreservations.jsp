<%@ page pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/views/taglibs.jspf" %>

<t:layout title="myReservations">
	<h1 class="mt-3"><spring:message code="newReservations"/></h1>
	<c:if test="${empty newReservations}">
		<div class="text-center">
			<p class="h1 text-muted"><spring:message code="noReservations"/></p>
		</div>
	</c:if>
	<c:if test="${not empty newReservations}">
		<table class="table table-responsive">
			<tr>
				<th><spring:message code="room.name"/></th>
				<th><spring:message code="reservation.time"/></th>
				<th><spring:message code="reservation.personCount"/></th>
				<th><spring:message code="reservation.additionalServices"/></th>
				<th><spring:message code="reservation.totalPrice"/></th>
				<th><spring:message code="reservation.paymentMethod"/></th>
				<th><spring:message code="reservation.notes"/></th>
				<th><spring:message code="actions"/></th>
			</tr>
			<c:forEach items="${newReservations}" var="reservation">
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
					<td><spring:message code="reservation.paymentMethod.${reservation.paymentMethod}"/></td>
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

	<h1 class="mt-3"><spring:message code="oldReservations"/></h1>
	<c:if test="${empty oldReservations}">
		<div class="text-center">
			<p class="h1 text-muted"><spring:message code="noReservations"/></p>
		</div>
	</c:if>
	<c:if test="${not empty oldReservations}">
		<table class="table table-responsive">
			<tr>
				<th><spring:message code="room.name"/></th>
				<th><spring:message code="reservation.time"/></th>
				<th><spring:message code="reservation.personCount"/></th>
				<th><spring:message code="reservation.additionalServices"/></th>
				<th><spring:message code="reservation.totalPrice"/></th>
				<th><spring:message code="reservation.notes"/></th>
			</tr>
			<c:forEach items="${oldReservations}" var="reservation">
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
				</tr>
			</c:forEach>
		</table>
	</c:if>

</t:layout>



