package com.xxd.x.wechat.sdk;

public class Api {
    public static final String GET_ACCESS_TOKEN = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=%s&secret=%s";

    public static final String GET_TICKET = "https://api.weixin.qq.com/cgi-bin/ticket/getticket?access_token=%s&type=%s";
}
