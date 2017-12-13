<%@ tag pageEncoding="UTF-8" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ attribute name="reservation" type="fi.xamk.tilavaraus.domain.Reservation" %>
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