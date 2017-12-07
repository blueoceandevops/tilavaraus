import initCalendar from './calendar';
import $ from 'jquery';
import moment from 'moment';

const state = {
	price: null,
	locale: window.locale,
	email: window.userEmail,
	events: window.eventsJson,
	$form: $('#reservationForm'),
	$checkoutBtn: $('#checkoutButton'),
	$price: $('#price'),
	$duration: $('#duration'),
	$date: $('#date'),
	$startTime: $('#startTime'),
	$endTime: $('#endTime'),
	$calendar: $('#calendar'),
	$errors: document.getElementById('reservation.errors')
};

const durationBetween = (start, end) => moment.duration(start.diff(end));
const toMoment = (date, time) => moment(`${date}T${time}`);

const refresh = () => {
	const setPrice = newPrice => {
		state.price = newPrice;
		state.$price.text(state.price ? state.price + ' €' : '-');
		state.$checkoutBtn.prop('disabled', !state.price);
	};
	const date = state.$date.val();
	state.$duration.text(
		durationBetween(toMoment(date, state.$startTime.val()), toMoment(date, state.$endTime.val())).humanize()
	);
	$.ajax({
		method: 'POST',
		url: '/api/calculatePrice',
		data: state.$form.serializeArray()
	}).then(data => {
		setPrice(parseInt(data));
	}, err => {
		setPrice(null);
	});
};

state.$startTime
	.add(state.$endTime)
	.add(state.$date)
	.change(() => {
		console.log('change');
		state.$calendar.fullCalendar('unselect');
		const date = state.$date.val();
		state.$calendar.fullCalendar('gotoDate', date);
		state.$calendar.fullCalendar(
			'select',
			toMoment(date, state.$startTime.val()), toMoment(date, state.$endTime.val())
		);
	});

$('input').on({
	change: refresh,
	keyup: refresh
});

if (state.$errors) {
	state.$errors.scrollIntoView();
}


initCalendar({
	container: state.$calendar,
	events: state.events,
	locale: state.locale,
	onSelect: (start, end) => {
		state.$date.val(start.format('YYYY-MM-DD'));
		state.$startTime.val(start.format('HH:mm'));
		state.$endTime.val(end.format('HH:mm'));
		refresh();
	}
});