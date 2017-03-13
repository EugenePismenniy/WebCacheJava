package ua.home.jnp.cache.impl;


import java.io.*;
import java.net.CacheRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author evgeniy.pismenny on 10.03.17 17:31.
 */
class CacheRequestImpl extends CacheRequest {

	private final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
	private final Map<String, List<String>> headers;

	public CacheRequestImpl(Map<String, List<String>> headers) {
		this.headers = headers;
	}

	@Override
	public OutputStream getBody() throws IOException {
		return outputStream;
	}


	public InputStream getInputStream() {
		return new ByteArrayInputStream(outputStream.toByteArray());
	}

	public Map<String, List<String>> getHeaders() {
		return new HashMap<>(headers);
	}

	@Override
	public void abort() {

	}
}
