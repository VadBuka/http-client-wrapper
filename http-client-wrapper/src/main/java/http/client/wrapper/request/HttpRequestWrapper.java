package http.client.wrapper.request;

import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.protocol.HttpContext;

public class HttpRequestWrapper {
    private final HttpUriRequest request;
    private final HttpContext httpContext;

    public HttpRequestWrapper(HttpUriRequest request, HttpContext httpContext) {
        this.request = request;
        this.httpContext = httpContext;
    }

    public HttpUriRequest getRequest() {
        return request;
    }

    public HttpContext getHttpContext() {
        return httpContext;
    }
}
