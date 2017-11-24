import $ from "jquery";

const store = {
	price: null,
	locale: window.locale,
	email: window.userEmail,
	events: window.eventsJson,
	$form: $('#reservationForm'),
	$payButton: $('#customButton')
};

export default store;