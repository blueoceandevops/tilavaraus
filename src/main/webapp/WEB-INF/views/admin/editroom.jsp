<%@ page pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/views/taglibs.jspf" %>

<t:layout>
	<form:form cssClass="mt-3"
			   action="${pageContext.request.contextPath}/admin/rooms/"
			   method="POST"
			   modelAttribute="roomDto"
			   enctype="multipart/form-data">
		<form:hidden path="room.id"/>
		<form:hidden path="room.thumbnailSrc"/>
		<form:hidden path="room.coverImageSrc"/>
		<div class="row">
			<form:errors cssClass="alert alert-danger"/>
		</div>
		<div class="row">

			<div class="col">

				<div class="form-group">
					<form:label path="room.name"><spring:message code="room.name"/></form:label>
					<form:input class="form-control"
								path="room.name"
								cssErrorClass="form-control is-invalid"/>
					<form:errors path="room.name" cssClass="invalid-feedback"/>
				</div>

				<div class="form-group">
					<form:label path="thumbnailImage"><spring:message code="room.thumbnailImage"/></form:label>
					<form:input class="form-control"
								type="file"
								path="thumbnailImage"
								cssErrorClass="form-control is-invalid"/>
					<form:errors path="thumbnailImage" cssClass="invalid-feedback"/>
				</div>

				<div class="form-group">
					<form:label path="coverImage"><spring:message code="room.coverImage"/></form:label>
					<form:input class="form-control"
								type="file"
								path="coverImage"
								cssErrorClass="form-control is-invalid"/>
					<form:errors path="coverImage" cssClass="invalid-feedback"/>
				</div>

				<div class="form-group">
					<form:label path="room.hourlyPrice"><spring:message code="room.hourlyPrice"/></form:label>
					<form:input class="form-control"
								path="room.hourlyPrice"
								type="number"
								cssErrorClass="form-control is-invalid"/>
					<form:errors path="room.hourlyPrice" cssClass="invalid-feedback"/>
				</div>

				<div class="form-group">
					<form:label path="room.capacity"><spring:message code="room.capacity"/></form:label>
					<form:input class="form-control"
								path="room.capacity"
								type="number"
								cssErrorClass="form-control is-invalid"/>
					<form:errors path="room.capacity" cssClass="invalid-feedback"/>
				</div>

				<div class="form-group">
					<form:label path="room.description"><spring:message code="room.description"/></form:label>
					<form:textarea class="form-control"
								   path="room.description"
								   cssErrorClass="form-control is-invalid"/>
					<form:errors path="room.description" cssClass="invalid-feedback"/>
				</div>

				<fieldset class="form-group">
					<legend><spring:message code="reservation.additionalServices"/></legend>
					<c:forEach items="${additionalServices}" var="additionalService">
						<div class="form-check">
							<label class="form-check-label">
								<form:checkbox path="room.allowedAdditionalServices" class="form-check-input"
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