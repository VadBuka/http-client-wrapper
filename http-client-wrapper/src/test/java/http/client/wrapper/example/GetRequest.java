package http.client.wrapper.example;

import http.client.wrapper.HttpRequestExecutor;
import http.client.wrapper.request.RequestBuilder;
import http.client.wrapper.response.HttpResponseWrapper;
import org.apache.commons.lang.text.StrMatcher;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.junit.matchers.JUnitMatchers.containsString;

public class GetRequest {
    @Test
    public void getRequestToGoogleDotCom(){
        final HttpResponseWrapper response = HttpRequestExecutor.executeRequest(
                RequestBuilder.get("http://imsdemo.herokuapp.com").build()
        );

        assertThat(response.getStatusCode(), is(200));
        assertThat(response.getContentType(), is("text/html"));
        assertThat(response.getContentEncoding(), is(nullValue()));
    }

    @Test
    public void getRequestToGoogleDotComWith(){
        final HttpResponseWrapper response = HttpRequestExecutor.executeRequest(
                RequestBuilder.
                        putRequest("http://imsdemo.herokuapp.com/application/create")
                        .withParameter("tweet", "Hello World")
                        .build()
        );

        assertThat(response.getStatusCode(), is(200));
        assertThat(response.getContentType(), is("text/html"));
        assertThat(response.getOutput(), is(containsString("Hello World (0 seconds ago)")));
        assertThat(response.getContentEncoding(), is(nullValue()));
    }
}
