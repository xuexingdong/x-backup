package com.xuexingdong.x.wechat.sdk.message.request.common;

/**
 * 多媒体消息
 */
public class MediaMessage extends CommonMessage {
    private String mediaId;

    public String getMediaId() {
        return mediaId;
    }

    public void setMediaId(String mediaId) {
        this.mediaId = mediaId;
    }
}
