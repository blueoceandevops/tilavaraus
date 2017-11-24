import 'fullcalendar';
import 'fullcalendar/dist/locale-all';
import 'fullcalendar/dist/fullcalendar.css';
import moment from 'moment';
import $ from 'jquery';
import store from './store';

const FORMAT = 'YYYY-MM-DD[T]HH:mm';
const durationBetween = (start, end) => moment.duration(start.diff(end));
const [$calendar, $startTime, $endTime, $duration, $price] = [$('#calendar'), $('#startTime'), $('#endTime'), $('#duration'), $('#price')];

const {$payButton, events, locale, $form} = store;
let {price} = store;

const refresh = () => {
	const setPrice = (newPrice) => {
		price = newPrice;
		$price.text(price ? price + ' €' : '-');
		$payButton.prop('disabled', !price);
	};
	$duration.text(durationBetween(moment($startTime.val()), moment($endTime.val())).humanize());
	$.ajax({
		method: 'POST',
		url: '/api/calculatePrice',
		data: $form.serializeArray()
	}).then(data => {
		setPrice(parseInt(data));
	}, err => {
		setPrice(null);
	});
};

$('input').on({
	change: refresh,
	keyup: refresh
});

$calendar.fullCalendar({
	defaultView: 'agendaWeek',
	events,
	locale,
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
	height: 'auto',
	unselectCancel: '#unselectCancel',
	selectAllow: ({start, end}) => start.isSame(end, 'day'),
	select: (start, end) => {
		$startTime.val(start.format(FORMAT));
		$endTime.val(end.format(FORMAT));
		refresh();
	},
	dayClick: date => {
		$startTime.val(date.format(FORMAT));
		$endTime.val(date.add({hours: 1}).format(FORMAT));
		refresh();
	}
});