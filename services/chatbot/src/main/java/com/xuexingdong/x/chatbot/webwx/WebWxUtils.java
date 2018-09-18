package com.xuexingdong.x.chatbot.webwx;

public class WebWxUtils {
    public static boolean isPerson(String fromUsername) {
        return !fromUsername.startsWith("@@") && fromUsername.startsWith("@");
    }

    public static boolean isChatroom(String fromUsername) {
        return fromUsername.startsWith("@@");
    }
}
