<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<t:layout>
    <h1><spring:message code="myReservations"/></h1>
    <table class="table table-responsive">
        <tr>
            <th><spring:message code="room.name"/></th>
            <th><spring:message code="reservation.time"/></th>
            <th><spring:message code="reservation.personCount"/></th>
            <th><spring:message code="reservation.additionalServices"/></th>
            <th><spring:message code="actions"/></th>
        </tr>
        <c:forEach items="${reservations}" var="reservation">
            <tr>
                <td>${reservation.room.name}</td>
                <td><t:formatDate value="${reservation.startTime}"/> - <t:formatDate value="${reservation.endTime}"/></td>
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
                       href="${spring:mvcUrl('FC#cancelReservation').arg(0, reservation).build()}">
                        <spring:message code="cancelReservation"/>
                    </a>
                </td>
            </tr>
        </c:forEach>
    </table>
</t:layout>



