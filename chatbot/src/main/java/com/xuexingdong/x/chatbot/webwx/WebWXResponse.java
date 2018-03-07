package com.xuexingdong.x.chatbot.webwx;

public class WebWXResponse {
    private String toUsername;
    private MsgType msgType = MsgType.TEXT;
    // 如果是文本类型，则是文字内容，如果是其他，则是文件
    private String content;

    public String getToUsername() {
        return toUsername;
    }

    public void setToUsername(String toUsername) {
        this.toUsername = toUsername;
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
