package com.xxd.x.wechat.sdk.message.response;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlCData;
import com.xxd.x.wechat.sdk.constant.MsgType;

public class NewsResponse extends WechatResponseMessage {
    private final MsgType msgType = MsgType.TEXT;
    @JacksonXmlCData
    private String content;

    public MsgType getMsgType() {
        return msgType;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
