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
		$price
	] = [$('#calendar'), $('#startTime'), $('#endTime'), $('#duration'), $('#reservationForm'), $('#price')];


    let price = null;

	const FORMAT = 'YYYY-MM-DD[T]HH:mm';

	window.APP = {
		updateDuration: () => {
			$duration.text(durationBetween(moment($startTime.val()), moment($endTime.val())).humanize());
		}
	};

	const updatePrice = () => {
		const formData = $form.serializeArray();
		formData.push({name: 'roomId', value: window.roomId});
		$.ajax({
			method: 'POST',
			url: '/api/calculatePrice',
			data: formData
		}).then(data => {
            price = parseInt(data);
            $price.text(price + ' €');
		}, err => {
            price = null;
			$price.text('-');
		});
	};
    const handler = StripeCheckout.configure({
        key: 'pk_test_nxRS0g5Ve6rZAfRu3Jt6Bm6n',
        locale: window.locale,
        currency: 'eur',
        email: window.userEmail,
        bitcoin: true,
        token: token => {
            $('<input>', {
                type: 'hidden',
                name: 'stripeToken',
                value: token.id
            }).appendTo($form);
            $form.submit();
        }
    });
    document.getElementById('customButton').addEventListener('click', function (e) {
        handler.open({
            name: 'Demo Site',
            description: '2 widgets',
            amount: price * 100
        });
        e.preventDefault();
    });
    window.addEventListener('popstate', function () {
        handler.close();
    });

	$form.find(':input').change(updatePrice);

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
		select: (start, end) => {
			$startTime.val(start.format(FORMAT));
			$endTime.val(end.format(FORMAT));
			APP.updateDuration();
			updatePrice();
		},
		dayClick: date => {
			$startTime.val(date.format(FORMAT));
			$endTime.val(date.add({hours: 1}).format(FORMAT));
			APP.updateDuration();
			updatePrice();
		}
	});

});