package com.xuexingdong.x.chatbot.event;

import com.xuexingdong.x.chatbot.webwx.MsgType;

public final class WebWxEvents {

    private WebWxEvents() {

    }

    public static Event sendText(String toUsername, String content) {
        return new Event(toUsername, EventType.SEND_MESSAGE, MsgType.TEXT, content);
    }

    public static Event sendImage(String toUsername, String content) {
        return new Event(toUsername, EventType.SEND_MESSAGE, MsgType.IMAGE, content);
    }

    public static Event sendFile(String toUsername, String content) {
        return new Event(toUsername, EventType.SEND_MESSAGE, MsgType.FILE, content);
    }

    public static Event modifyChatroomName(String chatroomUsername, String name) {
        return new Event(chatroomUsername, EventType.MODIFY_CHATROOM_NAME, name);
    }

    public static Event modifyFriendRemarkName(String toUsername, String remarkName) {
        return new Event(toUsername, EventType.MODIFY_FRIEND_REMARK_NAME, remarkName);
    }

}
