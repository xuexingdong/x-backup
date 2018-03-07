package com.xuexingdong.x.common.http;

import okhttp3.Headers;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import javax.annotation.Nonnull;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class XHttp {


    public static String get(@Nonnull String url) throws IOException {
        return request(url).body().string();
    }

    public static String post(@Nonnull String url) throws IOException {
        return request(url).body().string();
    }

    public static String body(@Nonnull String url) throws IOException {
        return request(url).body().string();
    }

    public static Headers headers(@Nonnull String url) throws IOException {
        return request(url).headers();
    }

    public static Map<String, String> cookies(@Nonnull String url) throws IOException {
        Headers headers = headers(url);
        Map<String, String> map = new HashMap<>();
        List<String> cookies = headers.values("Set-Cookie");
        for (String cookieStr : cookies) {
            String[] cookiePair = cookieStr.split(";");
            for (String pair : cookiePair) {
                String[] kv = pair.split("=", 2);
                if (kv.length < 2) {
                    continue;
                }
                map.put(kv[0].trim(), kv[1].trim());
            }
        }
        return map;
    }
    public static Response request(@Nonnull String url) throws IOException {
        OkHttpClient client = OkHttpClientFactory.getIgnoreSSLClient();
        Request request = new Request.Builder()
                .url(url)
                .build();
        return client.newCall(request).execute();
    }


}