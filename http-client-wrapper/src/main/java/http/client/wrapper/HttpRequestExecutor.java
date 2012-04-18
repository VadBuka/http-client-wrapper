package http.client.wrapper;

import http.client.wrapper.request.HttpRequestWrapper;
import http.client.wrapper.response.HttpResponseWrapper;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.AbstractHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.params.HttpParams;
import org.apache.http.params.SyncBasicHttpParams;

import java.io.IOException;

import static java.util.concurrent.TimeUnit.SECONDS;
import static org.apache.http.client.params.ClientPNames.*;
import static org.apache.http.params.CoreConnectionPNames.CONNECTION_TIMEOUT;
import static org.apache.http.params.CoreConnectionPNames.SO_TIMEOUT;
import static org.apache.http.params.CoreProtocolPNames.USE_EXPECT_CONTINUE;

public class HttpRequestExecutor {

    public static HttpResponseWrapper executeRequest(HttpRequestWrapper httpRequestWrapper) {
        return new HttpRequestExecutor().execute(httpRequestWrapper);
    }

    public HttpResponseWrapper execute(HttpRequestWrapper httpRequestWrapper) {
        final HttpClient httpClient = HttpClientFactory.getInstance();
        try {
            final HttpResponse httpResponse = httpClient.execute(httpRequestWrapper.getRequest(), httpRequestWrapper.getHttpContext());
            return new HttpResponseWrapper(httpResponse, httpRequestWrapper.getHttpContext());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public abstract static class HttpClientFactory {
        private static final AbstractHttpClient INSTANCE = createInstance();

        public static AbstractHttpClient getInstance() {
            return INSTANCE;
        }

        private static AbstractHttpClient createInstance() {
            return new DefaultHttpClient(connectionManager(), httpParams());
        }

        private static ThreadSafeClientConnManager connectionManager() {
            final ThreadSafeClientConnManager connManager = new ThreadSafeClientConnManager();
            connManager.setMaxTotal(20);
            connManager.setDefaultMaxPerRoute(10);
            return connManager;
        }

        private static HttpParams httpParams() {
            return new SyncBasicHttpParams()
                    .setParameter(CONNECTION_TIMEOUT, (int) SECONDS.toMillis(2 * 60 * 1000))
                    .setParameter(SO_TIMEOUT, (int) SECONDS.toMillis(2 * 60 * 1000))
                    .setParameter(HANDLE_REDIRECTS, true)
                    .setParameter(ALLOW_CIRCULAR_REDIRECTS, true)
                    .setParameter(HANDLE_AUTHENTICATION, true)
                    .setParameter(USE_EXPECT_CONTINUE, true);
        }
    }
}
