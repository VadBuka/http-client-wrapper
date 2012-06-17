package http.client.wrapper.request;

import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;

import java.net.URI;

public class DeleteRequestBuilder extends RequestBuilder<DeleteRequestBuilder> {

    public DeleteRequestBuilder(String baseURL) {
        super(URI.create(baseURL));
    }

    public DeleteRequestBuilder withParameter(String name, String value) {
        uriBuilder.withQueryParameter(name, value);
        return thisInstance();
    }

    @Override
    protected HttpDelete createHttpUriRequest() {
        return new HttpDelete(uriBuilder.build());
    }

}
