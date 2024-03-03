package com.xxd.x.common.utils;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public final class XURLUtils {

    public static Map<String, String> getParams(String link) {
        Map<String, String> map = new HashMap<>();
        URL url;
        try {
            url = new URL(link);
        } catch (MalformedURLException e) {
            return map;
        }
        String[] simpleQueries = url.getQuery().split("&");
        for (String simpleQuery : simpleQueries) {
            String[] kv = simpleQuery.split("=", 2);
            if (kv.length < 2) {
                continue;
            }
            map.put(kv[0], kv[1]);
        }
        return map;
    }

    public static String paramsToString(Map<?, ?> params) {
        StringBuilder sb = new StringBuilder();
        for (Map.Entry<?, ?> entry : params.entrySet()) {
            sb.append(entry.getKey()).append("=").append(entry.getValue());
            sb.append("&");
        }
        if (sb.length() > 1) {
            sb.deleteCharAt(sb.length() - 1);
        }
        return sb.toString();
    }
}
