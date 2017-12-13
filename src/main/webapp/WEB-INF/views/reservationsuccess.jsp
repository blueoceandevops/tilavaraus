<%@ page pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/views/taglibs.jspf" %>

<t:layout>
	<div class="alert alert-success mt-3" role="alert">
		<spring:message code="reservationSuccess"/>
	</div>
	<t:reservationInfo reservation="${reservation}"/>
</t:layout>



