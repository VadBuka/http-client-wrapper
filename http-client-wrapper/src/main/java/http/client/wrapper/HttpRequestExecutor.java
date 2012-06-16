package http.client.wrapper;

import http.client.wrapper.request.Request;
import http.client.wrapper.response.Response;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;

import java.io.IOException;

public class HttpRequestExecutor {

    private final HttpClient httpClient;

    private HttpRequestExecutor(HttpClient httpClient) {
        this.httpClient = httpClient;
    }

    public static HttpRequestExecutor withSingleClient() {
        return new HttpRequestExecutor(HttpClientFactory.createSingleClient());
    }

    public static HttpRequestExecutor withMultipleClient() {
        return new HttpRequestExecutor(HttpClientFactory.createThreadedClient());
    }

    public static Response executeOnce(Request request) {
        return withSingleClient().execute(request);
    }

    public Response execute(Request request) {
        try {
            final HttpResponse httpResponse = httpClient.execute(request.getHttpClientRequest(), request.getHttpContext());
            return new Response(httpResponse, request.getHttpContext());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
