<%@ page pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/views/taglibs.jspf" %>

<t:layout title="checkout">
	<jsp:attribute name="scripts">
		<script>
			window.totalPrice = '${reservation.totalPrice}';
			<security:authorize access="isFullyAuthenticated()">
			window.userEmail = '<security:authentication property="principal.username" htmlEscape="false" />';
			</security:authorize>
		</script>
        <script src="https://checkout.stripe.com/checkout.js"></script>
		<script src="${pageContext.request.contextPath}/dist/checkout.js"></script>
	</jsp:attribute>
	<jsp:body>

		<div class="row">

			<div class="col">


				<dl>
					<dt><spring:message code="room.name"/></dt>
					<dd>${reservation.room.name}</dd>
					<dt><spring:message code="reservation.time"/></dt>
					<dd><t:formatDate value="${reservation.date}"/> ${reservation.startTime}
						- ${reservation.endTime}</dd>
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