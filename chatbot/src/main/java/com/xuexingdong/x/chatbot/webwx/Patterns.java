package com.xuexingdong.x.chatbot.webwx;

import java.util.regex.Pattern;

public class Patterns {
    public static final Pattern EMOJI_PATTERN = Pattern.compile("<span class=\"emoji emoji([a-zA-Z0-9]+)\"></span>");
    public static final Pattern EMOTION_PATTERN = Pattern.compile("\\[\\S+]");
}
