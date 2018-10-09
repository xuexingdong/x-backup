package com.xuexingdong.x.chatbot.webwx;

import org.apache.commons.lang3.StringUtils;

public class WebWxUtils {

    public static boolean isPerson(String fromUsername) {
        if (StringUtils.isEmpty(fromUsername)) {
            return false;
        }
        return !fromUsername.startsWith("@@") && fromUsername.startsWith("@");
    }

    public static boolean isChatroom(String fromUsername) {
        if (StringUtils.isEmpty(fromUsername)) {
            return false;
        }
        return fromUsername.startsWith("@@");
    }

    public static boolean isPrivateChat(WebWxMessage webWxMessage) {
        return isPerson(webWxMessage.getFromUsername()) && isPerson(webWxMessage.getToUsername());
    }
}
