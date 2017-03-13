package ua.home.jnp.cache.impl;

import lombok.extern.slf4j.Slf4j;

import javax.cache.Cache;
import javax.cache.CacheManager;
import javax.cache.Caching;
import javax.cache.configuration.MutableConfiguration;
import javax.cache.spi.CachingProvider;
import java.io.IOException;
import java.net.*;
import java.util.List;
import java.util.Map;

/**
 * @author evgeniy.pismenny on 10.03.17 17:29.
 */
@Slf4j
public class ResponseCacheImpl extends ResponseCache {

	private final Cache<URI, CacheRequest> requestCache;

	public ResponseCacheImpl(final String cacheName) {
		CachingProvider cachingProvider = Caching.getCachingProvider();
		CacheManager cacheManager = cachingProvider.getCacheManager();
		MutableConfiguration<URI, CacheRequest> configuration = new MutableConfiguration<URI, CacheRequest>().setStoreByValue(false).setTypes(URI.class, CacheRequest.class);
		this.requestCache = cacheManager.createCache(cacheName, configuration);
	}

	@Override
	public CacheResponse get(URI uri, String rqstMethod, Map<String, List<String>> rqstHeaders) throws IOException {

		log.info("request: uri = '{}', method = '{}'", uri, rqstMethod);

		if ("get".equalsIgnoreCase(rqstMethod)) {
			CacheRequest request = requestCache.get(uri);
			if (request != null) {
				log.info("Request found in cache for uri = '{}', method = '{}'", uri, rqstMethod);
				return new CacheResponseImpl((CacheRequestImpl) request);
			}
			log.info("No found cache for uri = '{}', method = '{}'", uri, rqstMethod);
		}



		return null;
	}


	@Override
	public CacheRequest put(URI uri, URLConnection conn) throws IOException {

		HttpURLConnection httpsURLConnection = (HttpURLConnection) conn;

		String contentType = httpsURLConnection.getContentType();
		String requestMethod = httpsURLConnection.getRequestMethod();

		log.info("request: uri = '{}', contentType = '{}', requestMethod = '{}'", uri, contentType, requestMethod);

		if (contentType != null && contentType.startsWith("application/json")
				&& "get".equalsIgnoreCase(requestMethod) && !requestCache.containsKey(uri)) {

			CacheRequestImpl request = new CacheRequestImpl(conn.getHeaderFields());
			log.info("Save response from '{}' in cache", uri);
			requestCache.put(uri, request);
			return request;
		}

		return null;
	}
}
