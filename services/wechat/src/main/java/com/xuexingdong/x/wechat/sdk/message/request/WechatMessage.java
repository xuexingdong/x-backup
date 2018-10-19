package com.xuexingdong.x.wechat.sdk.message.request;


import java.time.Clock;

public abstract class WechatMessage {
    
    private String toUserName;
    private String fromUserName;
    private long createTime = Math.floorDiv(Clock.systemDefaultZone().millis(), 1000L);

    public String getToUserName() {
        return toUserName;
    }

    public void setToUserName(String toUserName) {
        this.toUserName = toUserName;
    }

    public String getFromUserName() {
        return fromUserName;
    }

    public void setFromUserName(String fromUserName) {
        this.fromUserName = fromUserName;
    }

    public long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }
}
