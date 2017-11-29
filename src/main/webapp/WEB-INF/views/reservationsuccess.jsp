<%@ page pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/views/taglibs.jspf" %>

<t:layout>
	<div class="alert alert-success mt-3" role="alert">
		<spring:message code="reservationSuccess"/>
	</div>

	<dl>
		<dt><spring:message code="room.name"/></dt>
		<dd>${reservation.room.name}</dd>
		<dt><spring:message code="reservation.time"/></dt>
		<dd><t:formatDate value="${reservation.date}"/> ${reservation.startTime} - ${reservation.endTime}</dd>
		<dt><spring:message code="reservation.personCount"/></dt>
		<dd>${reservation.personCount}</dd>
		<dt><spring:message code="reservation.additionalServices"/></dt>
		<dd>
			<ul>
				<c:forEach items="${reservation.additionalServices}" var="additionalService">
					<li><spring:message code="additionalService.name.${additionalService.name}"/></li>
				</c:forEach>
			</ul>
		</dd>
		<dt><spring:message code="reservation.totalPrice"/></dt>
		<dd>${reservation.totalPrice} €</dd>
	</dl>
</t:layout>



