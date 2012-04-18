package http.client.wrapper.response;

import org.apache.http.HttpResponse;
import org.apache.http.client.CookieStore;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.protocol.HttpContext;

public class HttpResponseWrapper {

    private final String output;
    private final String contentType;
    private final String contentEncoding;
    private final int statusCode;
    private final String statusMessage;
    private final CookieStore cookieStoreAfterResponse;
    private HttpContext httpContext;

    public HttpResponseWrapper(HttpResponse httpResponse, CookieStore cookieStoreAfterResponse) {
        this.cookieStoreAfterResponse = cookieStoreAfterResponse;
        final HttpRestResource httpRestResource = new HttpRestResource(httpResponse.getEntity());
        this.contentType = httpRestResource.getContentType();
        this.contentEncoding = httpRestResource.getContentEncoding();
        this.output = httpRestResource.toString();
        this.statusCode = httpResponse.getStatusLine().getStatusCode();
        this.statusMessage = httpResponse.getStatusLine().getReasonPhrase();
    }

    public HttpResponseWrapper(HttpResponse httpResponse, HttpContext httpContext) {
        this(httpResponse, new BasicCookieStore());
        this.httpContext = httpContext;
    }

    public String getOutput() {
        return output;
    }

    public String getContentType() {
        if (contentType != null && contentType.contains(";")) {
            return contentType.split(";")[0];
        } else {
            return contentType;
        }
    }

    public String getContentEncoding() {
        return contentEncoding;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public String getStatusMessage() {
        return statusMessage;
    }

    public CookieStore getCookieStoreAfterResponse() {
        return cookieStoreAfterResponse;
    }

    public HttpContext getHttpContext() {
        return httpContext;
    }
}