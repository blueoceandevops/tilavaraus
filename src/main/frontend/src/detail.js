import $ from 'jquery';
import moment from 'moment';
import 'fullcalendar';
import 'fullcalendar/dist/locale-all';
import 'fullcalendar/dist/fullcalendar.css';

const durationBetween = (start, end) => moment.duration(start.diff(end));

$(() => {
	const [$calendar, $startTime, $endTime, $duration] = [$('#calendar'), $('#startTime'), $('#endTime'), $('#duration')];
	const FORMAT = 'YYYY-MM-DD[T]HH:mm';

	window.APP = {
		updateDuration: () => {
			$duration.text(durationBetween(moment($startTime.val()), moment($endTime.val())).humanize());
		}
	};

	$calendar.fullCalendar({
		defaultView: 'agendaWeek',
		events: window.eventsJson,
		locale: window.locale,
		firstDay: 1,
		defaultDate: moment().add(8, 'days'),
		validRange: {
			start: moment().add(6, 'days')
		},
		minTime: "08:00:00",
		maxTime: "20:00:00",
		hiddenDays: [0],
		allDaySlot: false,
		selectable: true,
		selectHelper: true,
		selectOverlap: false,
		selectLongPressDelay: 500,
		selectMinDistance: 5,
		unselectCancel: '#reservationForm',
		select: (start, end) => {
			$startTime.val(start.format(FORMAT));
			$endTime.val(end.format(FORMAT));
			APP.updateDuration();
		},
		dayClick: date => {
			$startTime.val(date.format(FORMAT));
			$endTime.val(date.add({hours: 1}).format(FORMAT));
			APP.updateDuration();
		}
	});

	setInterval(() => {
		$calendar.fullCalendar('refetchEvents');
	}, 3000);

});