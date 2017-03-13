package ua.home.jnp.cache;

import org.junit.Test;

import javax.cache.Cache;
import javax.cache.CacheManager;
import javax.cache.Caching;
import javax.cache.spi.CachingProvider;
import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.net.CacheRequest;
import java.net.URI;
import java.util.concurrent.TimeUnit;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.assertThat;

/**
 * @author evgeniy.pismenny on 13.03.17 17:33.
 */
public class CachedRequesterTest {

	public static final String REQUEST_URL = "http://api.aladhan.com/currentTimestamp?zone=Europe/London";

	static final CachedRequester requester = new CachedRequester(REQUEST_URL);

	@Test
	public void testSendRequest() throws Exception {


		CachingProvider cachingProvider = Caching.getCachingProvider();
		assertThat(cachingProvider, notNullValue());

		CacheManager cacheManager = cachingProvider.getCacheManager();
		assertThat(cacheManager, notNullValue());

		Cache<URI, CacheRequest> uriCache = cacheManager.getCache(CachedRequester.CACHE_NAME, URI.class, CacheRequest.class);
		assertThat(uriCache, notNullValue());

		String hucResp = requester.sendHttpURLConnectionGetRequest();
		assertThat(hucResp, notNullValue());


		CacheRequest cacheRequest = uriCache.get(new URI(REQUEST_URL));
		assertThat(cacheRequest, notNullValue());

		OutputStream bodyStream = cacheRequest.getBody();
		assertThat(bodyStream, notNullValue());
		assertThat(bodyStream, instanceOf(ByteArrayOutputStream.class));

		String respBodyFromCache = bodyStream.toString();
		assertThat(hucResp, equalTo(respBodyFromCache));

		// ----------------------------------

		TimeUnit.SECONDS.sleep(5);

		String hucResp2 = requester.sendHttpURLConnectionGetRequest();
		assertThat(hucResp2, notNullValue());
		assertThat(hucResp2, equalTo(respBodyFromCache));
		assertThat(hucResp, equalTo(hucResp2));

		// ------------------------------------


		String jerseyResp = requester.sendJerseyGetRequest();
		assertThat(jerseyResp, notNullValue());
		assertThat(jerseyResp, equalTo(hucResp2));
		assertThat(jerseyResp, equalTo(respBodyFromCache));

		// ------------------------------

		String srtResp = requester.sendSpringRestTemplateGetRequest();
		assertThat(srtResp, notNullValue());
		assertThat(srtResp, equalTo(hucResp2));
		assertThat(srtResp, equalTo(respBodyFromCache));

	}
}
