package http.client.wrapper.request;

import org.apache.http.client.methods.HttpGet;

import java.net.URI;

public class GetRequestBuilder extends RequestBuilder<GetRequestBuilder> {

    public GetRequestBuilder(String baseURL) {
        super(URI.create(baseURL));
    }

    public GetRequestBuilder withParameter(String name, String value) {
        uriBuilder.withQueryParameter(name, value);
        return thisInstance();
    }

    public HttpRequestWrapper build() {
        return new HttpRequestWrapper(new HttpGet(uriBuilder.build()), httpContextBuilder.build());
    }

}
