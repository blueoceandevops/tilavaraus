import './main.scss';
import 'bootstrap/js/src/index';

if ('serviceWorker' in navigator) {
	navigator.serviceWorker.register('/service-worker.js')
	// .then(registration => {
	//	registration.pushManager.subscribe({userVisibleOnly: true});
	// })
		.catch((err) => {
			console.log("Failed to register service worker: ", err)
		});
}