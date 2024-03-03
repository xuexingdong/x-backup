package com.xxd.x.wechat.service;

public interface WechatService {

    String getAccessToken();

    void refreshAccessToken();

    String getJsApiTicket();

    void refreshJsApiTicket();

    String getJsApiSignature();

}
