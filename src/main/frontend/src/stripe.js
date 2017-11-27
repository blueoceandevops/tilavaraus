export default ({token, locale, email}) =>
	window.StripeCheckout.configure({
		key: 'pk_test_nxRS0g5Ve6rZAfRu3Jt6Bm6n',
		locale,
		currency: 'eur',
		email,
		bitcoin: true,
		token
	});