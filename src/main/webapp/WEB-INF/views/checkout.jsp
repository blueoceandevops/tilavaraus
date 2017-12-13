<%@ page pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/views/taglibs.jspf" %>

<t:layout title="checkout">
	<jsp:attribute name="scripts">
		<script>
			window.totalPrice = '${reservation.totalPrice}';
		</script>
        <script src="https://checkout.stripe.com/checkout.js"></script>
		<script src="${pageContext.request.contextPath}/dist/checkout.js"></script>
	</jsp:attribute>
	<jsp:body>

		<div class="row mt-4">

			<div class="col">
				<t:reservationInfo reservation="${reservation}"/>
			</div>
			<div class="col">

				<form:form modelAttribute="reservation" action="${pageContext.request.contextPath}/reserve">
					<form:hidden path="startTime" value="${reservation.startTime}"/>
					<form:hidden path="room" value="${reservation.room.id}"/>
					<form:hidden path="user" value="${user.id}"/>
					<form:hidden path="date" value="${reservation.date}"/>
					<form:hidden path="endTime" value="${reservation.endTime}"/>
					<form:hidden path="personCount" value="${reservation.personCount}"/>
					<c:if test="${not empty reservation.additionalServices}">
						<div style="display: none;"><form:checkboxes path="additionalServices"
						                                             items="${reservation.additionalServices}"/></div>
					</c:if>
					<form:hidden path="notes" value="${reservation.notes}"/>
					<fieldset class="form-group">
						<legend><spring:message code="reservation.paymentMethod"/></legend>
						<spring:eval
							expression="T(fi.xamk.tilavaraus.domain.Reservation.PaymentMethod).values()"
							var="paymentMethods"/>
						<c:forEach items="${paymentMethods}" var="paymentMethod">
							<div class="form-check form-check-inline">
								<label class="form-check-label">
									<form:radiobutton cssClass="form-check-input" path="paymentMethod"
									                  value="${paymentMethod}" required="required"/>
									<spring:message code="reservation.paymentMethod.${paymentMethod}"/>
								</label>
							</div>
						</c:forEach>
					</fieldset>
					<button type="submit" id="customButton" class="btn btn-primary">
						<spring:message code="pay"/>
					</button>
				</form:form>

			</div>

		</div>

	</jsp:body>
</t:layout>