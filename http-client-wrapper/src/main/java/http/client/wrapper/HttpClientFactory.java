package http.client.wrapper;

import org.apache.http.impl.client.AbstractHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.SingleClientConnManager;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.params.HttpParams;
import org.apache.http.params.SyncBasicHttpParams;

import static java.util.concurrent.TimeUnit.SECONDS;
import static org.apache.http.client.params.ClientPNames.*;
import static org.apache.http.params.CoreConnectionPNames.CONNECTION_TIMEOUT;
import static org.apache.http.params.CoreConnectionPNames.SO_TIMEOUT;
import static org.apache.http.params.CoreProtocolPNames.USE_EXPECT_CONTINUE;

public class HttpClientFactory {

    private HttpClientFactory() {
        super();
    }

    public static AbstractHttpClient createThreadedClient() {
        return new DefaultHttpClient(new ThreadSafeClientConnManager() {{
            setMaxTotal(20);
            setDefaultMaxPerRoute(10);
        }}, httpParams());
    }

    public static AbstractHttpClient createSingleClient() {
        return new DefaultHttpClient(new SingleClientConnManager(), httpParams());
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
