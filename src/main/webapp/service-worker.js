const PRECACHE = 'precache-v1';

const PRECACHE_URLS = [
	'/',
	'/?utm_source=homescreen'
];

self.addEventListener('install', event => {
	event.waitUntil(
		caches.open(PRECACHE)
			.then(cache => cache.addAll(PRECACHE_URLS))
			.then(self.skipWaiting())
	);
});

// The activate handler takes care of cleaning up old caches.
self.addEventListener('activate', event => {
	const currentCaches = [PRECACHE];
	event.waitUntil(
		caches.keys().then(cacheNames =>
			cacheNames.filter(cacheName => !currentCaches.includes(cacheName))
		).then(cachesToDelete =>
			Promise.all(cachesToDelete.map(cacheToDelete =>
				caches.delete(cacheToDelete)
			))
		).then(() => self.clients.claim())
	);
});

self.addEventListener('fetch', event => {
	event.respondWith(
		fetch(event.request).catch(() => caches.match(event.request))
	);
});