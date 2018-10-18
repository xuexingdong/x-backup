package com.xuexingdong.x.chatbot.event;

public enum EventType {
    SEND_MESSAGE(101),
    AGREE_FRIEND_REQUEST(201),
    REJECT_FRIEND_REQUEST(202),
    MODIFY_FRIEND_REMARK_NAME(203),
    MODIFY_CHATROOM_NAME(301);

    private final int type;

    EventType(int type) {
        this.type = type;
    }

    public int value() {
        return type;
    }
}