package com.xuexingdong.x.chatbot.webwx;

public class WebWxMessage {
    private String msgId;
    private String fromUsername;
    private String toUsername;
    private String fromNickname;
    private String toNickname;
    private String fromRemarkName;
    private String toRemarkName;
    private MsgType msgType;
    private int createTime;

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

    public String getFromNickname() {
        return fromNickname;
    }

    public void setFromNickname(String fromNickname) {
        this.fromNickname = fromNickname;
    }

    public String getToNickname() {
        return toNickname;
    }

    public void setToNickname(String toNickname) {
        this.toNickname = toNickname;
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

    public int getCreateTime() {
        return createTime;
    }

    public void setCreateTime(int createTime) {
        this.createTime = createTime;
    }
}