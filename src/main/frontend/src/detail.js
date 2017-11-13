import $ from 'jquery';
import moment from 'moment';
import 'fullcalendar';
import 'fullcalendar/dist/fullcalendar.css';

const durationBetween = (start, end) => moment.duration(start.diff(end));

$(() => {
	const [$startTime, $endTime, $duration] = [$('#startTime'), $('#endTime'), $('#duration')];
	const FORMAT = 'YYYY-MM-DD[T]hh:mm';

	window.APP = {
		updateDuration: () => {
			$duration.text(durationBetween(moment($startTime.val()), moment($endTime.val())).humanize());
		}
	};

	$('#calendar').fullCalendar({
		defaultView: 'agendaWeek',
		events: window.eventsUrl,
		locale: window.locale,
		firstDay: 1,
		hiddenDays: [0],
		allDaySlot: false,
		selectable: true,
		selectHelper: true,
		selectLongPressDelay: 500,
		unselectCancel: '#reservationForm',
		select: (start, end) => {
			$startTime.val(start.format(FORMAT));
			$endTime.val(end.format(FORMAT));
			APP.updateDuration();
		},
		dayClick: function (date) {
			$startTime.val(date.format(FORMAT));
			$endTime.val(date.add({hours: 1}).format(FORMAT));
			APP.updateDuration();
		}
	});
});