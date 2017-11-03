<%@page pageEncoding="UTF-8" %>
<jsp:useBean id="room" scope="request" type="fi.xamk.tilavaraus.domain.Room"/>
<!doctype html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>Document</title>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0-beta.2/css/bootstrap.min.css" integrity="sha384-PsH8R72JQ3SOdhVi3uxftmaW6Vc51MKb0q5P2rRUpPvrszuE4W1povHYgTpBfshb" crossorigin="anonymous">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/lib/fullcalendar-3.6.2/fullcalendar.css">
    <script src="${pageContext.request.contextPath}/lib/fullcalendar-3.6.2/lib/jquery.min.js"></script>
    <script src="${pageContext.request.contextPath}/lib/fullcalendar-3.6.2/lib/moment.min.js"></script>
    <script src="${pageContext.request.contextPath}/lib/fullcalendar-3.6.2/fullcalendar.js"></script>
    <script src="${pageContext.request.contextPath}/lib/fullcalendar-3.6.2/locale-all.js"></script>

</head>
<body>

    <h1>${room.name}</h1>
    <form action="${pageContext.request.contextPath}/${room.id}/reserve" method="POST">
        <label for="startTime">Aloitusaika</label>
        <input type="datetime-local" name="startTime" id="startTime">
        <br>

        <label for="endTime">Lopetusaika</label>
        <input type="datetime-local" name="endTime" id="endTime">
        <br>

        <label for="count">Henkilömäärä</label>
        <input id="count" type="number" name="count" max="${room.capacity}">
        <br>

        <input type="checkbox" name="additionalServices" value="es">ES<br>
        <input type="checkbox" name="additionalServices" value="kahvi">Kahvi<br>

        <input type="submit">
    </form>
    <div id="calendar"></div>
    <script>
        $(document).ready(function() {
            $('#calendar').fullCalendar({
                defaultView: 'agendaWeek',
	            events: '${pageContext.request.contextPath}/${room.id}/events',
	            locale: 'fi',
	            firstDay: 1,
	            hiddenDays: [0],
	            allDaySlot: false,
	            dayClick: function (date) {
		            $('#startTime').val(date.format());
                }
            });
        });
    </script>

</body>
</html>