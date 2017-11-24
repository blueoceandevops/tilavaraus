import $ from 'jquery';
import moment from 'moment';
import 'fullcalendar';
import 'fullcalendar/dist/locale-all';
import 'fullcalendar/dist/fullcalendar.css';

const durationBetween = (start, end) => moment.duration(start.diff(end));

$(() => {

	const [
		$calendar,
		$startTime,
		$endTime,
		$duration,
		$form,
		$price,
		$payButton
	] = [
		$('#calendar'),
		$('#startTime'),
		$('#endTime'),
		$('#duration'),
		$('#reservationForm'),
		$('#price'),
		$('#customButton')
	];

	const store = {price: null};
	const FORMAT = 'YYYY-MM-DD[T]HH:mm';

	const refresh = () => {
		const setPrice = (price) => {
			store.price = price;
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

	if (window.userEmail) {
		const handler = StripeCheckout.configure({
			key: 'pk_test_nxRS0g5Ve6rZAfRu3Jt6Bm6n',
			locale: window.locale,
			currency: 'eur',
			email: window.userEmail,
			bitcoin: true,
			token: token => {
				$payButton.prop('disabled', true);
				$('<input>', {
					type: 'hidden',
					name: 'stripeToken',
					value: token.id
				}).appendTo($form);
				$form.submit();
			}
		});
		document.getElementById('customButton').addEventListener('click', e => {
			handler.open({
				name: 'Tilavaraus',
				amount: store.price * 100
			});
			e.preventDefault();
		});
		window.addEventListener('popstate', () => {
			handler.close();
		});
	}

	$('input').on({
		change: refresh,
		keyup: refresh
	});

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

});