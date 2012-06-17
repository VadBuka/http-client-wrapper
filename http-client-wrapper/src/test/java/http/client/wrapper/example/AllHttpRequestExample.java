package http.client.wrapper.example;

import http.client.wrapper.HttpRequestExecutor;
import http.client.wrapper.request.RequestBuilder;
import http.client.wrapper.response.Response;
import org.apache.http.HttpHeaders;
import org.apache.http.protocol.HttpContext;
import org.junit.Test;

import static org.apache.http.HttpHeaders.CONTENT_TYPE;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.junit.matchers.JUnitMatchers.containsString;

public class AllHttpRequestExample {

    private final HttpRequestExecutor executor = HttpRequestExecutor.withSingleClient();

    @Test
    public void executeGetRequest() {
        final Response response = executor.execute(
                RequestBuilder.get("http://thames.herokuapp.com").withPath("/test/get").build()
        );

        assertThat(response.getStatusCode(), is(200));
        assertThat(response.getContentType(), is("text/html"));
        assertThat(response.getContentEncoding(), is(nullValue()));
        assertThat(response.getOutput(), is(containsString("Hello World, I support GET.")));
    }

    @Test
    public void executePutRequest() {
        final Response response = executor.execute(
                RequestBuilder.
                        putRequest("http://thames.herokuapp.com")
                        .withPath("/test/put")
                        .withHeader(CONTENT_TYPE, "application/json")
                        .withData("{\"user\":\"timbaktoo\"}")
                        .build()
        );

        assertThat(response.getStatusCode(), is(200));
        assertThat(response.getContentType(), is("text/html"));
        assertThat(response.getOutput(), is(containsString("Hello World, I support PUT. {\"user\":\"timbaktoo\"}")));
        assertThat(response.getContentEncoding(), is(nullValue()));
    }

    @Test
    public void executePutFormRequest() {
        final Response response = executor.execute(
                RequestBuilder.
                        putRequest("http://imsdemo.herokuapp.com")
                        .withPath("/application/create")
                        .withParameter("tweet", "Hello World")
                        .build()
        );

        assertThat(response.getStatusCode(), is(200));
        assertThat(response.getContentType(), is("text/html"));
        assertThat(response.getOutput(), is(containsString("Hello World (0 seconds ago)")));
        assertThat(response.getContentEncoding(), is(nullValue()));
    }

    @Test
    public void executeDelete() {
        final Response response = executor.execute(
                RequestBuilder.
                        deleteRequest("http://thames.herokuapp.com")
                        .withPath("/test/delete")
                        .build()
        );

        assertThat(response.getStatusCode(), is(200));
        assertThat(response.getContentType(), is("text/html"));
        assertThat(response.getOutput(), is(containsString("Hello World, I support DELETE.")));
        assertThat(response.getContentEncoding(), is(nullValue()));
    }
}
