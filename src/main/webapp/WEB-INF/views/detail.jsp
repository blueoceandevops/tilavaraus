<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<t:layout>
	<jsp:attribute name="head">
		<link rel="stylesheet" href="${pageContext.request.contextPath}/lib/fullcalendar-3.6.2/fullcalendar.css">
		<script src="${pageContext.request.contextPath}/lib/fullcalendar-3.6.2/lib/jquery.min.js"></script>
		<script src="${pageContext.request.contextPath}/lib/fullcalendar-3.6.2/lib/moment.min.js"></script>
		<script src="${pageContext.request.contextPath}/lib/fullcalendar-3.6.2/fullcalendar.js"></script>
		<script src="${pageContext.request.contextPath}/lib/fullcalendar-3.6.2/locale-all.js"></script>
	</jsp:attribute>
	<jsp:body>
		<h1>${room.name}</h1>
		<img src="http://via.placeholder.com/350x150" alt="">
		<div class="row">
			<div class="col-sm">
				<div id="calendar"></div>
			</div>
			<div class="col-sm">
				<form action="${pageContext.request.contextPath}/rooms/${room.id}" method="POST">
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
						<label for="count"><spring:message code="reservation.personCount"/></label>
						<input class="form-control"
						       id="count"
						       type="number"
						       name="count"
						       min="0"
						       max="${room.capacity}"
						       required>
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
				</form>
			</div>
		</div>
		<script>
			(($) => {
				const durationBetween = (start, end) => moment.duration(start.diff(end));

				$(() => {
					const [$startTime, $endTime, $duration] = [$('#startTime'), $('#endTime'), $('#duration')];

					window.APP = {
						updateDuration: () => {
							$duration.text(durationBetween(moment($startTime.val()), moment($endTime.val())).humanize());
						}
					};

					$('#calendar').fullCalendar({
						defaultView: 'agendaWeek',
						events: '${pageContext.request.contextPath}/rooms/${room.id}/events',
						locale: '${pageContext.response.locale}',
						firstDay: 1,
						hiddenDays: [0],
						allDaySlot: false,
						dayClick: function (date) {
							$startTime.val(date.format());
							$endTime.val(date.add({hours: 1}).format());
							APP.updateDuration();
						}
					});
				});
			})(window.jQuery);
		</script>
	</jsp:body>
</t:layout>