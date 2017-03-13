package ua.home.jnp.cache.impl;

import java.io.IOException;
import java.io.InputStream;
import java.net.CacheResponse;
import java.util.List;
import java.util.Map;

/**
 * @author evgeniy.pismenny on 10.03.17 17:34.
 */
class CacheResponseImpl extends CacheResponse {

	private final InputStream inputStream;
	private final Map<String, List<String>> headers;

	public CacheResponseImpl(CacheRequestImpl request) {
		this(request.getInputStream(), request.getHeaders());
	}

	public CacheResponseImpl(InputStream inputStream, Map<String, List<String>> headers) {
		this.inputStream = inputStream;
		this.headers = headers;
	}

	@Override
	public Map<String, List<String>> getHeaders() throws IOException {
		return this.headers;
	}

	@Override
	public InputStream getBody() throws IOException {
		return this.inputStream;
	}
}
