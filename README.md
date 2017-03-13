Example Web Cache for Java 
==========================

**ua.home.jnp.cache.impl** - Extensions of abstract classes:

* [java.net.ResponseCache](https://docs.oracle.com/javase/7/docs/api/java/net/ResponseCache.html); 
* [java.net.CacheRequest](https://docs.oracle.com/javase/7/docs/api/java/net/CacheRequest.html);
* [java.net.CacheResponse](https://docs.oracle.com/javase/7/docs/api/java/net/CacheResponse.html);   
 
**ua.home.jnp.cache.CachedRequester** - methods for send http get request with:

* Raw [HttpURLConnection](http://docs.oracle.com/javase/7/docs/api/java/net/HttpURLConnection.html);
* [Jersey Client](https://jersey.java.net/documentation/latest/client.html);
* Spring Framework [RestTemplate](https://docs.spring.io/spring/docs/current/javadoc-api/org/springframework/web/client/RestTemplate.html);

Both, Jersey Client and RestTemplate using inside the HttpURLConnection

