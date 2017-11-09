<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<jsp:useBean id="reservations" scope="request" type="java.util.List<fi.xamk.tilavaraus.domain.Reservation>"/>

<t:layout>
    <h1><spring:message code="myReservations"/></h1>
    <table class="table table-responsive">
        <tr>
            <th><spring:message code="room.name"/></th>
            <th><spring:message code="reservation.startTime"/></th>
            <th><spring:message code="reservation.endTime"/></th>
            <th><spring:message code="reservation.personCount"/></th>
            <th><spring:message code="reservation.additionalServices"/></th>
            <th><spring:message code="actions"/></th>
        </tr>
        <c:forEach items="${reservations}" var="reservation">
            <tr>
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
                <td><a href="${pageContext.request.contextPath}/reservations/${reservation.id}/delete">Peruuta</a></td>
            </tr>
        </c:forEach>
    </table>
</t:layout>



