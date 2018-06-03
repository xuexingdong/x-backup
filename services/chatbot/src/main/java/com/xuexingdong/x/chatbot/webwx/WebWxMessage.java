package com.xuexingdong.x.chatbot.webwx;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;

public class WebWxMessage {
    private String msgId;
    private String fromUsername;
    private String toUsername;
    private String fromNickName;
    private String toNickName;
    private String fromRemarkName;
    private String toRemarkName;
    private MsgType msgType;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;

    public String getMsgId() {
        return msgId;
    }

    public void setMsgId(String msgId) {
        this.msgId = msgId;
    }

    public String getFromUsername() {
        return fromUsername;
    }

    public void setFromUsername(String fromUsername) {
        this.fromUsername = fromUsername;
    }

    public String getToUsername() {
        return toUsername;
    }

    public void setToUsername(String toUsername) {
        this.toUsername = toUsername;
    }

    public String getFromNickName() {
        return fromNickName;
    }

    public void setFromNickName(String fromNickName) {
        this.fromNickName = fromNickName;
    }

    public String getToNickName() {
        return toNickName;
    }

    public void setToNickName(String toNickName) {
        this.toNickName = toNickName;
    }

    public String getFromRemarkName() {
        return fromRemarkName;
    }

    public void setFromRemarkName(String fromRemarkName) {
        this.fromRemarkName = fromRemarkName;
    }

    public String getToRemarkName() {
        return toRemarkName;
    }

    public void setToRemarkName(String toRemarkName) {
        this.toRemarkName = toRemarkName;
    }

    public MsgType getMsgType() {
        return msgType;
    }

    public void setMsgType(MsgType msgType) {
        this.msgType = msgType;
    }

    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }
}