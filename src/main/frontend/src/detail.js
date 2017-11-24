import initStripe from './stripe';
import './calendar';
import store from './store';

if (store.email) {
	initStripe();
}