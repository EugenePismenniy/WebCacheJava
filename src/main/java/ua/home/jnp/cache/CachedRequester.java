package ua.home.jnp.cache;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.springframework.web.client.RestTemplate;
import ua.home.jnp.cache.impl.ResponseCacheImpl;

import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.MediaType;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.ResponseCache;
import java.net.URL;

/**
 * @author evgeniy.pismenny on 10.03.17 16:35.
 */
@Slf4j
public class CachedRequester {

	public static final String CACHE_NAME = "ua.home.jnp.cache.uriCache";

	static  {
		ResponseCache.setDefault(new ResponseCacheImpl(CACHE_NAME));
	}

	private final String requestUrl;

	public CachedRequester(String requestUrl) {
		this.requestUrl = requestUrl;
	}

	public String sendSpringRestTemplateGetRequest() {
		return new RestTemplate().getForObject(requestUrl, String.class);
	}

	public String sendJerseyGetRequest() {
		return ClientBuilder.newClient().target(requestUrl).request(MediaType.APPLICATION_JSON_TYPE).get(String.class);
	}

	public String sendHttpURLConnectionGetRequest() throws IOException {
		URL url = new URL(requestUrl);
		HttpURLConnection connection = (HttpURLConnection) url.openConnection();
		connection.setConnectTimeout(10000);
		connection.setReadTimeout(10000);
		connection.setRequestMethod("GET");
		connection.addRequestProperty("Accept", "application/json");

		try(InputStream in = connection.getInputStream()) {
			return IOUtils.toString(in, "utf8");
		}
	}
}
