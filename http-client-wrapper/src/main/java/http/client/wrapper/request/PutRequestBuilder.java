package http.client.wrapper.request;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.HttpPut;

import java.net.URI;

public abstract class PutRequestBuilder<T extends PutRequestBuilder> extends RequestBuilder<T> {

    public PutRequestBuilder(String baseURL) {
        super(URI.create(baseURL));
    }

    protected abstract HttpEntity getPutEntity();

    @Override
    public HttpRequestWrapper build() {
        final HttpPut httpPut = new HttpPut(uriBuilder.build());
        httpPut.setEntity(getPutEntity());
        return new HttpRequestWrapper(httpPut, httpContextBuilder.build());
    }
}
