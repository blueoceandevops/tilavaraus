import $ from "jquery";

const $payButton = $('#customButton');
const $form = $('#reservation');

const handler = window.StripeCheckout.configure({
	key: 'pk_test_nxRS0g5Ve6rZAfRu3Jt6Bm6n',
	locale: window.locale,
	currency: 'eur',
	email: window.userEmail,
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

$payButton.click(event => {
	if ($form.find('input[name="paymentMethod"]:checked').val() === 'CARD') {
		handler.open({
			name: 'Tilavaraus',
			amount: window.totalPrice * 100
		});
		event.preventDefault();
	}
});

window.addEventListener('popstate', () => {
	handler.close();
});