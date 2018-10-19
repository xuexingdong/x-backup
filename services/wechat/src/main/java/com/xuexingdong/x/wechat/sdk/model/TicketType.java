package com.xuexingdong.x.wechat.sdk.model;

public enum TicketType {
    JSAPI("jsapi"),
    WX_CARD("wx_card");

    private String type;

    TicketType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }
}
