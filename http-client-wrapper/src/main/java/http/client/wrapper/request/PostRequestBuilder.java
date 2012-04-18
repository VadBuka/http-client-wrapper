package http.client.wrapper.request;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.HttpPost;

import java.net.URI;

public abstract class PostRequestBuilder<T extends PostRequestBuilder> extends RequestBuilder<T> {

    public PostRequestBuilder(String baseURL) {
        super(URI.create(baseURL));
    }

    protected abstract HttpEntity getPostEntity();

    @Override
    public HttpRequestWrapper build() {
        final HttpPost httpPost = new HttpPost(uriBuilder.build());
        httpPost.setEntity(getPostEntity());
        return new HttpRequestWrapper(httpPost, httpContextBuilder.build());
    }
}
