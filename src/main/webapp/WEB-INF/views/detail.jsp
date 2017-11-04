<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<jsp:useBean id="room" scope="request" type="fi.xamk.tilavaraus.domain.Room"/>
<!doctype html>
<html lang="en">
<head>
	<meta charset="UTF-8">
	<meta name="viewport"
	      content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
	<meta http-equiv="X-UA-Compatible" content="ie=edge">
	<title>Document</title>
	<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0-beta.2/css/bootstrap.min.css"
	      integrity="sha384-PsH8R72JQ3SOdhVi3uxftmaW6Vc51MKb0q5P2rRUpPvrszuE4W1povHYgTpBfshb" crossorigin="anonymous">
	<link rel="stylesheet" href="${pageContext.request.contextPath}/lib/fullcalendar-3.6.2/fullcalendar.css">
	<script src="${pageContext.request.contextPath}/lib/fullcalendar-3.6.2/lib/jquery.min.js"></script>
	<script src="${pageContext.request.contextPath}/lib/fullcalendar-3.6.2/lib/moment.min.js"></script>
	<script src="${pageContext.request.contextPath}/lib/fullcalendar-3.6.2/fullcalendar.js"></script>
	<script src="${pageContext.request.contextPath}/lib/fullcalendar-3.6.2/locale-all.js"></script>
</head>
<body>
<h1>${room.name}</h1>
<form action="${pageContext.request.contextPath}/${room.id}/reserve" method="POST">
	<label for="startTime"><spring:message code="reservation.startTime"/></label>
	<input type="datetime-local" name="startTime" id="startTime" onchange="APP.updateDuration()">
	<br>

	<label for="endTime"><spring:message code="reservation.endTime"/></label>
	<input type="datetime-local" name="endTime" id="endTime" onchange="APP.updateDuration()">
	<br>

	<div><spring:message code="reservation.duration"/>: <span id="duration"></span></div>
	<br>

	<label for="count"><spring:message code="reservation.personCount"/></label>
	<input id="count" type="number" name="count" max="${room.capacity}">
	<br>

	<input type="checkbox" name="additionalServices" value="es">ES<br>
	<input type="checkbox" name="additionalServices" value="kahvi">Kahvi<br>

	<input type="submit">
</form>
<div id="calendar"></div>
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
				events: '${pageContext.request.contextPath}/${room.id}/events',
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
</body>
</html>