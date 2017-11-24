<%@ page pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/views/taglibs.jspf" %>

<t:layout noContainer="true" title="${room.name}">
	<jsp:attribute name="head">
		<link rel="stylesheet" href="${pageContext.request.contextPath}/dist/detail.css">
	</jsp:attribute>
	<jsp:attribute name="scripts">
		<script>
			window.locale = '${pageContext.response.locale.language}';
			window.eventsJson = JSON.parse('${eventsJson}');
			<security:authorize access="isFullyAuthenticated()">
			window.userEmail = '<security:authentication property="principal.username" htmlEscape="false" />';
			</security:authorize>
		</script>
        <script src="https://checkout.stripe.com/checkout.js"></script>
		<script src="${pageContext.request.contextPath}/dist/detail.js"></script>
	</jsp:attribute>
	<jsp:body>
		<div class="bg-dark">
			<img class="img-fluid mx-auto d-block" src="${room.thumbnailSrc}" alt="">
		</div>
		<div class="p-4 bg-warning">
			<h1 class="text-uppercase text-center font-weight-bold">${room.name}</h1>
		</div>
		<div class="container">
			<form:form action="${pageContext.request.contextPath}/rooms/${room.id}"
			           id="reservationForm"
			           method="POST" modelAttribute="reservation">
				<form:hidden path="room" value="${room.id}"/>
				<div class="row">
					<form:errors cssClass="alert alert-danger"/>
				</div>
				<div class="row">
					<div class="col-md-8 mt-4">
						<div id="calendar"></div>
					</div>
					<div class="col-md-4 mt-4" id="unselectCancel">
						<security:authorize access="isAuthenticated()">
							<div class="form-group">
								<form:label path="startTime"><spring:message code="reservation.startTime"/></form:label>
								<form:input class="form-control"
								            type="datetime-local"
								            path="startTime"
								            required="true"
								            cssErrorClass="form-control is-invalid"/>
								<form:errors path="startTime" cssClass="invalid-feedback"/>
							</div>

							<div class="form-group">
								<form:label path="endTime"><spring:message code="reservation.endTime"/></form:label>
								<form:input class="form-control"
								            type="datetime-local"
								            path="endTime"
								            required="true"
								            cssErrorClass="form-control is-invalid"/>
								<form:errors path="endTime" cssClass="invalid-feedback"/>
								<small class="form-text text-muted">
									<spring:message code="reservation.duration"/>: <span id="duration"></span>
								</small>
							</div>

							<div class="form-group">
								<form:label path="personCount"><spring:message
									code="reservation.personCount"/></form:label>
								<form:input class="form-control"
								            path="personCount"
								            type="number"
								            name="count"
								            min="0"
								            max="${room.capacity}"
								            required="true"
								            cssErrorClass="form-control is-invalid"/>
								<form:errors path="personCount" cssClass="invalid-feedback"/>
							</div>

							<fieldset class="form-group">
								<legend><spring:message code="reservation.additionalServices"/></legend>
								<c:forEach items="${additionalServices}" var="additionalService">
									<div class="form-check">
										<label class="form-check-label">
											<input type="checkbox"
											       name="additionalServices"
											       class="form-check-input"
											       value="${additionalService.id}">
											<spring:message code="additionalService.name.${additionalService.name}"/>
											(${additionalService.price} €)
										</label>
									</div>
								</c:forEach>
							</fieldset>

							<div class="form-group">
								<form:label path="notes"><spring:message
									code="reservation.notes"/></form:label>
								<form:textarea class="form-control"
								               path="notes"
								               cssErrorClass="form-control is-invalid"/>
								<form:errors path="notes" cssClass="invalid-feedback"/>
							</div>

							<p><spring:message code="reservation.totalPrice"/>: <span id="price"
							                                                          class="font-weight-bold">-</span>
							</p>

							<button id="customButton" class="btn btn-primary" disabled><spring:message
								code="pay"/></button>
						</security:authorize>
						<security:authorize access="isAnonymous()">
							<div class="alert alert-info" role="alert">Sinun täytyy kirjautua sisään tehdäksesi
								varauksia
							</div>
						</security:authorize>
					</div>
				</div>
			</form:form>
		</div>
	</jsp:body>
</t:layout>