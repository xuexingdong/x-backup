package com.xxd.x.common.http;

import okhttp3.Cookie;
import okhttp3.CookieJar;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;

import javax.net.ssl.SSLContext;
import javax.net.ssl.X509TrustManager;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.List;

public class OkHttpClientFactory {

    private static SSLContext sslContext;

    static {
        try {
            sslContext = SSLContext.getInstance("SSL");
            sslContext.init(null, new X509TrustManager[]{new IgnoreX509TrustManager()}, null);
        } catch (GeneralSecurityException ignored) {

        }
    }

    public static OkHttpClient getIgnoreSSLClient() {
        return new OkHttpClient.Builder()
                .sslSocketFactory(sslContext.getSocketFactory(), new IgnoreX509TrustManager()).hostnameVerifier((s, sslSession) -> true)
                .followRedirects(false)
                .followSslRedirects(false)
                .cookieJar(new LocalCookieJar())
                .build();
    }

    static class LocalCookieJar implements CookieJar {
        private List<Cookie> cookies;

        @Override
        public void saveFromResponse(HttpUrl url, List<Cookie> cookies) {
            this.cookies = cookies;
        }

        @Override
        public List<Cookie> loadForRequest(HttpUrl url) {
            if (cookies != null)
                return cookies;
            return new ArrayList<>();
        }
    }
}
