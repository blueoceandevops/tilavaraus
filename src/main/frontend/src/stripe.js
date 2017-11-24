import $ from 'jquery';
import store from './store';

const {locale, email, $form, $payButton, price} = store;

export default () => {

	const handler = window.StripeCheckout.configure({
		key: 'pk_test_nxRS0g5Ve6rZAfRu3Jt6Bm6n',
		locale,
		currency: 'eur',
		email,
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
			amount: price * 100
		});
		e.preventDefault();
	});

	window.addEventListener('popstate', () => {
		handler.close();
	});
}