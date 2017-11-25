export default ({token, locale, email, getPrice}) => {

	const handler = window.StripeCheckout.configure({
		key: 'pk_test_nxRS0g5Ve6rZAfRu3Jt6Bm6n',
		locale,
		currency: 'eur',
		email,
		bitcoin: true,
		token
	});

	document.getElementById('customButton').addEventListener('click', e => {
		handler.open({
			name: 'Tilavaraus',
			amount: getPrice() * 100
		});
		e.preventDefault();
	});

	window.addEventListener('popstate', () => {
		handler.close();
	});
}