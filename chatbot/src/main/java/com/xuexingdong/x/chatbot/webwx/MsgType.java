package com.xuexingdong.x.chatbot.webwx;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum MsgType {
    TEXT(1),
    IMAGE(3),
    FILE(6),
    VOICE(34),
    CARD(42),
    VIDEO1(43),
    EMOTION(47),
    LOCATION(48),
    LINK(49),
    CALL(50),
    SHARE_LOCATION(51),
    GET_CONTACTS_INFO(51),
    VIDEO2(62),
    SYSTEM(10000),
    BLOCKED(10002);

    private final int type;

    MsgType(int type) {
        this.type = type;
    }

    @JsonValue
    public int getType() {
        return type;
    }

    @JsonCreator
    public static MsgType valueOf(int value) {
        for (MsgType msgType : MsgType.values()) {
            if (msgType.getType() == value) {
                return msgType;
            }
        }
        return null;
    }
}
