importScripts('/workbox-sw.prod.v2.1.2.js');

const workbox = new WorkboxSW({
	skipWaiting: true,
	clientsClaim: true
});

workbox.router.registerRoute('/', workbox.strategies.networkFirst());
workbox.router.registerRoute(/\/\?utm_source=homescreen/, workbox.strategies.networkFirst());
workbox.router.registerRoute(/\/img\/(.*?)/, workbox.strategies.cacheFirst());

workbox.precache([]);