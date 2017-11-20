<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<t:layout>
	<div class="alert alert-success" role="alert">
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



