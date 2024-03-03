package com.xxd.x.chatbot.event;

import com.xxd.x.chatbot.webwx.MsgType;

public class Event {
    private String toUsername;
    private EventType eventType;
    private MsgType msgType;
    private String content;


    public Event(String toUsername, EventType eventType) {
        this.toUsername = toUsername;
        this.eventType = eventType;
    }

    public Event(String toUsername, EventType eventType, String content) {
        this.toUsername = toUsername;
        this.eventType = eventType;
        this.content = content;
    }

    public Event(String toUsername, EventType eventType, MsgType msgType, String content) {
        this.toUsername = toUsername;
        this.eventType = eventType;
        this.msgType = msgType;
        this.content = content;
    }

    public String getToUsername() {
        return toUsername;
    }

    public void setToUsername(String toUsername) {
        this.toUsername = toUsername;
    }

    public EventType getEventType() {
        return eventType;
    }

    public void setEventType(EventType eventType) {
        this.eventType = eventType;
    }

    public MsgType getMsgType() {
        return msgType;
    }

    public void setMsgType(MsgType msgType) {
        this.msgType = msgType;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
