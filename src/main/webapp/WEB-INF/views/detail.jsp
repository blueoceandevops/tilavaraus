<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<t:layout>
	<jsp:attribute name="head">
		<link rel="stylesheet" href="${pageContext.request.contextPath}/dist/detail.css">
		<script>
			window.eventsUrl = '${pageContext.request.contextPath}/rooms/${room.id}/events';
			window.locale = '${pageContext.response.locale.language}';
		</script>
	</jsp:attribute>
	<jsp:body>
		<h1>${room.name}</h1>
		<img class="img-fluid mb-4" src="${room.thumbnailSrc}" alt="">

		<form:form id="reservationForm" action="${pageContext.request.contextPath}/rooms/${room.id}"
		           method="POST" modelAttribute="reservation">
			<div class="row">
				<form:errors path="*" cssClass="alert alert-danger"/>
			</div>
			<div class="row">
				<div class="col-md-8">
					<div id="calendar"></div>
				</div>
				<div class="col-md-4">

						<div class="form-group">
							<label for="startTime"><spring:message code="reservation.startTime"/></label>
							<input class="form-control"
							       type="datetime-local"
							       name="startTime"
							       id="startTime"
							       onchange="APP.updateDuration()"
							       required>
						</div>

						<div class="form-group">
							<label for="endTime"><spring:message code="reservation.endTime"/></label>
							<input class="form-control"
							       type="datetime-local"
							       name="endTime"
							       id="endTime"
							       onchange="APP.updateDuration()"
							       required>
							<small class="form-text text-muted">
								<spring:message code="reservation.duration"/>: <span id="duration"></span>
							</small>
						</div>

						<div class="form-group">
							<form:label path="personCount"><spring:message code="reservation.personCount"/></form:label>
							<form:input class="form-control"
							            path="personCount"
							            type="number"
							            name="count"
							            min="0"
							            max="${room.capacity}"
							            required="true" cssErrorClass="form-control is-invalid"/>
						</div>

						<fieldset class="form-group">
							<legend><spring:message code="reservation.additionalServices"/></legend>
							<c:forEach items="${additionalServices}" var="additionalService">
								<div class="form-check">
									<label class="form-check-label">
										<input type="checkbox"
										       name="additionalServices"
										       class="form-check-input"
										       value="${additionalService}">
										<spring:message code="${additionalService}"/>
									</label>
								</div>
							</c:forEach>
						</fieldset>

						<button type="submit" class="btn btn-primary"><spring:message code="reserve"/></button>

				</div>
			</div>
		</form:form>
		<script src="${pageContext.request.contextPath}/dist/detail.js"></script>
	</jsp:body>
</t:layout>