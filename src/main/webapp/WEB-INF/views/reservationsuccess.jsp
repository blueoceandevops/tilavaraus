<%@ page pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/views/taglibs.jspf" %>

<t:layout>
	<div class="alert alert-success mt-3" role="alert">
		<spring:message code="reservationSuccess"/>
	</div>
	<ul>
		<li>${reservation.room.name}</li>
		<li><t:formatDate value="${reservation.startTime}"/> - <t:formatDate value="${reservation.endTime}"/></li>
		<li>${reservation.personCount}</li>
		<li>
			<ul>
				<c:forEach items="${reservation.additionalServices}" var="additionalService">
					<li><spring:message code="${additionalService}"/></li>
				</c:forEach>
			</ul>
		</li>
		<li>${reservation.totalPrice} €</li>
	</ul>
</t:layout>



