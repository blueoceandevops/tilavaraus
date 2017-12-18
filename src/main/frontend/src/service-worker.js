importScripts('/workbox-sw.prod.v2.1.2.js');

const workbox = new WorkboxSW({
	skipWaiting: true,
	clientsClaim: true
});

const networkFirstStrategy = workbox.strategies.networkFirst();

workbox.router.registerRoute('/', networkFirstStrategy);
workbox.router.registerRoute(/\/\?utm_source=homescreen/, networkFirstStrategy);
workbox.router.registerRoute(/\/img\/(.*?)/, workbox.strategies.cacheFirst());
workbox.router.registerRoute(/\/files\/(.*?)/, workbox.strategies.cacheFirst());

workbox.precache([]);