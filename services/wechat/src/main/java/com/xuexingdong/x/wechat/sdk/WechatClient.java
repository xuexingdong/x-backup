package com.xxd.x.wechat.sdk;

import com.fasterxml.jackson.databind.JsonNode;
import com.xxd.x.common.serialization.XSerialization;
import com.xxd.x.wechat.sdk.model.TicketType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.apache.commons.lang3.tuple.Pair;

import java.io.IOException;

public class WechatClient {

    private String appid;
    private String appsecret;

    public WechatClient(String appid, String appsecret) {
        this.appid = appid;
        this.appsecret = appsecret;
    }

    public Pair<String, Long> fetchAccessToken() throws IOException {
        OkHttpClient client = new OkHttpClient();
        String url = String.format(Api.GET_ACCESS_TOKEN, appid, appsecret);
        Request request = new Request.Builder().url(url).build();
        Response response = client.newCall(request).execute();
        JsonNode node = XSerialization.fromJSON(response.body().string());
        String accessToken = node.get("access_token").asText();
        long expire = node.get("expires_in").asLong();
        return Pair.of(accessToken, expire);
    }

    public Pair<String, Long> fetchTicket(String accessToken, TicketType ticketType) throws IOException {
        OkHttpClient client = new OkHttpClient();
        String url = String.format(Api.GET_TICKET, accessToken, ticketType.getType());
        Request request = new Request.Builder().url(url).build();
        Response response = client.newCall(request).execute();
        JsonNode node = XSerialization.fromJSON(response.body().string());
        String ticket = node.get("ticket").asText();
        long expire = node.get("expires_in").asLong();
        return Pair.of(ticket, expire);
    }
}
