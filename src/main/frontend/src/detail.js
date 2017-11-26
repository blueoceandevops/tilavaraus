import initStripe from './stripe';
import initCalendar from './calendar';
import $ from 'jquery';
import moment from 'moment';

const state = {
	price: null,
	locale: window.locale,
	email: window.userEmail,
	events: window.eventsJson,
	$form: $('#reservationForm'),
	$payButton: $('#customButton'),
	$price: $('#price'),
	$duration: $('#duration'),
	$startTime: $('#startTime'),
	$endTime: $('#endTime'),
	$calendar: $('#calendar')
};

const durationBetween = (start, end) => moment.duration(start.diff(end));

const refresh = () => {
	const setPrice = newPrice => {
		state.price = newPrice;
		state.$price.text(state.price ? state.price + ' €' : '-');
		state.$payButton.prop('disabled', !state.price);
	};
	state.$duration.text(durationBetween(moment(state.$startTime.val()), moment(state.$endTime.val())).humanize());
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

state.$startTime.add(state.$endTime).change(() => {
	console.log('change');
	state.$calendar.fullCalendar('unselect');
	state.$calendar.fullCalendar('select', moment(state.$startTime.val()), moment(state.$endTime.val()));
});

$('input').on({
	change: refresh,
	keyup: refresh
});

initCalendar({
	container: state.$calendar,
	events: state.events,
	locale: state.locale,
	onSelect: (start, end) => {
		state.$startTime.val(start);
		state.$endTime.val(end);
		refresh();
	}
});

if (state.email) {
	initStripe({
		token: token => {
			state.$payButton.prop('disabled', true);
			$('<input>', {
				type: 'hidden',
				name: 'stripeToken',
				value: token.id
			}).appendTo(state.$form);
			state.$form.submit();
		},
		locale: state.locale,
		getPrice: () => state.price,
		email: state.email,
	});
}