<%@ page pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/views/taglibs.jspf" %>

<t:layout>
	<form:form cssClass="mt-3"
			   action="${pageContext.request.contextPath}/admin/rooms/"
			   method="POST"
			   modelAttribute="room">
		<form:hidden path="id"/>
		<div class="row">
			<form:errors cssClass="alert alert-danger"/>
		</div>
		<div class="row">

			<div class="col">

				<div class="form-group">
					<form:label path="name"><spring:message code="room.name"/></form:label>
					<form:input class="form-control"
								path="name"
								cssErrorClass="form-control is-invalid"/>
					<form:errors path="name" cssClass="invalid-feedback"/>
				</div>

				<div class="form-group">
					<form:label path="thumbnailSrc"><spring:message code="room.thumbnailSrc"/></form:label>
					<form:input class="form-control"
								path="thumbnailSrc"
								cssErrorClass="form-control is-invalid"/>
					<form:errors path="thumbnailSrc" cssClass="invalid-feedback"/>
				</div>

				<div class="form-group">
					<form:label path="coverImageSrc"><spring:message code="room.coverImageSrc"/></form:label>
					<form:input class="form-control"
								path="coverImageSrc"
								cssErrorClass="form-control is-invalid"/>
					<form:errors path="coverImageSrc" cssClass="invalid-feedback"/>
				</div>

				<div class="form-group">
					<form:label path="hourlyPrice"><spring:message code="room.hourlyPrice"/></form:label>
					<form:input class="form-control"
								path="hourlyPrice"
								type="number"
								cssErrorClass="form-control is-invalid"/>
					<form:errors path="hourlyPrice" cssClass="invalid-feedback"/>
				</div>

				<div class="form-group">
					<form:label path="capacity"><spring:message code="room.capacity"/></form:label>
					<form:input class="form-control"
								path="capacity"
								type="number"
								cssErrorClass="form-control is-invalid"/>
					<form:errors path="capacity" cssClass="invalid-feedback"/>
				</div>

				<div class="form-group">
					<form:label path="description"><spring:message code="room.description"/></form:label>
					<form:textarea class="form-control"
								   path="description"
								   cssErrorClass="form-control is-invalid"/>
					<form:errors path="description" cssClass="invalid-feedback"/>
				</div>

				<fieldset class="form-group">
					<legend><spring:message code="reservation.additionalServices"/></legend>
					<c:forEach items="${additionalServices}" var="additionalService">
						<div class="form-check">
							<label class="form-check-label">
								<form:checkbox path="allowedAdditionalServices" class="form-check-input"
											   value="${additionalService}"/>
								<spring:message code="additionalService.name.${additionalService.name}"/>
							</label>
						</div>
					</c:forEach>
				</fieldset>
				<button type="submit" class="btn btn-primary"><spring:message code="save"/></button>
			</div>
		</div>
	</form:form>
</t:layout>