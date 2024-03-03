package com.xxd.x.wechat.sdk.message.request.common;

import com.xxd.x.wechat.sdk.constant.MsgType;
import com.xxd.x.wechat.sdk.message.request.WechatMessage;

public abstract class CommonMessage extends WechatMessage {
    protected String msgId;
    protected MsgType msgType;

    public String getMsgId() {
        return msgId;
    }

    public void setMsgId(String msgId) {
        this.msgId = msgId;
    }

    public MsgType getMsgType() {
        return msgType;
    }

    public void setMsgType(MsgType msgType) {
        this.msgType = msgType;
    }
}
