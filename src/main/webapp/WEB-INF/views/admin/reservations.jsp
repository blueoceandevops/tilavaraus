<%@ page pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/views/taglibs.jspf" %>

<t:layout title="allReservations" noContainer="true">
	<jsp:attribute name="head">
		<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.min.css">
	</jsp:attribute>
	<jsp:body>
		<div class="container mt-3">
			<form:form modelAttribute="reservation" method="get" cssClass="form-inline">
				<form:label path="room" cssClass="sr-only"><spring:message code="reservation.room"/></form:label>
				<form:select path="room" cssClass="custom-select mr-1" onchange="this.form.submit()">
					<spring:message code="allRooms" var="allRoomsTxt"/>
					<form:option value="" label="${allRoomsTxt}"/>
					<form:options items="${rooms}" itemLabel="name" itemValue="id"/>
				</form:select>
				<form:label path="user" cssClass="sr-only"><spring:message code="reservation.user"/></form:label>
				<form:select path="user" cssClass="custom-select mr-1" onchange="this.form.submit()">
					<spring:message code="allUsers" var="allUsersTxt"/>
					<form:option value="" label="${allUsersTxt}"/>
					<form:options items="${users}" itemLabel="email" itemValue="id"/>
				</form:select>
				<form:label path="date" cssClass="sr-only"><spring:message code="reservation.date"/></form:label>
				<form:input path="date" type="date" cssClass="form-control mr-1" onchange="this.form.submit()"/>
				<spring:message code="clear" var="clearTxt"/>
				<a href="${spring:mvcUrl('AC#listReservations').build()}"
				   class="btn btn-secondary"
				   aria-label="${clearTxt}">
					<i class="fa fa-times" aria-hidden="true" title="${clearTxt}"></i>
				</a>
				<spring:message code="refresh" var="refreshTxt"/>
				<button type="submit" class="btn btn-secondary" aria-label="${refreshTxt}">
					<i class="fa fa-refresh" aria-hidden="true" title="${refreshTxt}"></i>
				</button>
			</form:form>
		</div>
		<c:if test="${!reservations.hasContent()}">
			<div class="text-center mt-3">
				<p class="text-muted"><spring:message code="noReservations"/></p>
			</div>
		</c:if>
		<c:if test="${reservations.hasContent()}">
			<table class="table table-responsive mt-3">
				<tr>
					<th><spring:message code="reservation.user"/></th>
					<th><spring:message code="room.name"/></th>
					<th><spring:message code="reservation.time"/></th>
					<th><spring:message code="reservation.personCount"/></th>
					<th><spring:message code="reservation.additionalServices"/></th>
					<th><spring:message code="reservation.totalPrice"/></th>
					<th><spring:message code="reservation.paymentMethod"/></th>
					<th><spring:message code="reservation.notes"/></th>
					<th><spring:message code="actions"/></th>
				</tr>
				<c:forEach items="${reservations.content}" var="reservation">
					<tr>
						<td>
							<a class="text-info"
							   href="${spring:mvcUrl('AC#showUser').arg(0, reservation.user).build()}">
									${reservation.user.email}
							</a>
						</td>
						<td>${reservation.room.name}</td>
						<td>
							<t:formatDate value="${reservation.date}"/> ${reservation.startTime}
							- ${reservation.endTime}
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
						<td>
							<c:choose>
								<c:when test="${not empty reservation.chargeToken}">
									<a class="text-info"
									   href="https://dashboard.stripe.com/test/payments/${reservation.chargeToken}">
										<spring:message code="reservation.paymentMethod.${reservation.paymentMethod}"/>
									</a>
								</c:when>
								<c:otherwise>
									<spring:message code="reservation.paymentMethod.${reservation.paymentMethod}"/>
								</c:otherwise>
							</c:choose>
						</td>
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
	</jsp:body>
</t:layout>