package com.xxd.x.wechat.sdk.builder;


import com.xxd.x.wechat.sdk.message.response.TextResponse;

public final class TextBuilder implements Builder<TextResponse> {

    private TextResponse textResponse = new TextResponse();

    public TextBuilder content(String content) {
        textResponse.setContent(content);
        return this;
    }

    public TextBuilder from(String fromUserName) {
        textResponse.setFromUserName(fromUserName);
        return this;
    }

    public TextBuilder to(String toUserName) {
        textResponse.setToUserName(toUserName);
        return this;
    }

    @Override
    public TextResponse build() {
        return textResponse;
    }
}
