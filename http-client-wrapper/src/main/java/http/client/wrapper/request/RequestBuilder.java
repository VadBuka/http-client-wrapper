package http.client.wrapper.request;

import org.apache.http.protocol.HttpContext;

import javax.servlet.http.Cookie;
import java.net.URI;
import java.util.List;

public abstract class RequestBuilder<T extends RequestBuilder> {

    protected final URIBuilder uriBuilder;
    protected final HttpContextBuilder httpContextBuilder;

    public static GetRequestBuilder get(String baseUrl) {
        return new GetRequestBuilder(baseUrl);
    }

    public static PutStreamEntityRequestBuilder putRequest(String baseUrl) {
        return new PutStreamEntityRequestBuilder(baseUrl);
    }

    public static DeleteRequestBuilder deleteRequest(String baseUrl) {
        return new DeleteRequestBuilder(baseUrl);
    }

    public static PostFormRequestBuilder postForm(String baseUrl) {
        return new PostFormRequestBuilder(baseUrl);
    }

    public static PostStreamEntityRequestBuilder postRequest(String baseUrl) {
        return new PostStreamEntityRequestBuilder(baseUrl);
    }

    public RequestBuilder(URI baseURI) {
        uriBuilder = new URIBuilder(baseURI);
        httpContextBuilder = new HttpContextBuilder(baseURI.getHost());
    }

    public T withPath(String path) {
        uriBuilder.withPath(path);
        return thisInstance();
    }

    public T withPathParameter(String name, String value) {
        uriBuilder.withPathParameter(name, value);
        return thisInstance();
    }

    public T withCookies(List<Cookie> cookies) {
        httpContextBuilder.withCookies(cookies);
        return thisInstance();
    }

    public T withContext(HttpContext httpContext) {
        httpContextBuilder.useContext(httpContext);
        return thisInstance();
    }

    public abstract Request build();

    @SuppressWarnings("unchecked")
    protected T thisInstance() {
        return (T) this;
    }
}
