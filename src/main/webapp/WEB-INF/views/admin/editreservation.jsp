<%@ page pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/views/taglibs.jspf" %>

<t:layout>
	<form:form cssClass="mt-3"
	           action="${pageContext.request.contextPath}/admin/reservations/${reservation.id}/edit"
	           method="POST" modelAttribute="reservation">
		<div class="row">
			<form:errors cssClass="alert alert-danger"/>
		</div>
		<div class="row">

			<div class="col">

				<div class="form-group">
					<form:label path="startTime"><spring:message code="reservation.startTime"/></form:label>
					<form:input class="form-control"
					            type="datetime-local"
					            path="startTime"
					            onchange="APP.updateDuration()"
					            required="true"
					            cssErrorClass="form-control is-invalid"/>
					<form:errors path="startTime" cssClass="invalid-feedback"/>
				</div>

				<div class="form-group">
					<form:label path="endTime"><spring:message code="reservation.endTime"/></form:label>
					<form:input class="form-control"
					            type="datetime-local"
					            path="endTime"
					            onchange="APP.updateDuration()"
					            required="true"
					            cssErrorClass="form-control is-invalid"/>
					<form:errors path="endTime" cssClass="invalid-feedback"/>
				</div>

				<div class="form-group">
					<form:label path="personCount"><spring:message code="reservation.personCount"/></form:label>
					<form:input class="form-control"
					            path="personCount"
					            type="number"
					            name="count"
					            min="0"
					            max="${reservation.room.capacity}"
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
</t:layout>