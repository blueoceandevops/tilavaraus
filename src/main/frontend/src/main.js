import './main.scss';
import 'bootstrap/js/src/index';

if ('serviceWorker' in navigator) {
	navigator.serviceWorker.register('/service-worker.js')
		.catch((err) => {
			console.log("Failed to register service worker: ", err)
		});
}