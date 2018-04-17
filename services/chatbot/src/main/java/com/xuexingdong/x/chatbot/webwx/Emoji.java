package com.xuexingdong.x.chatbot.webwx;

import java.util.HashMap;
import java.util.Map;

public class Emoji {

    public static final Map<String, String> EMOJI_MAP = new HashMap<String, String>() {{
        put("1F60A", "😊");
        put("1F637", "😷");
        put("1F639", "😂");
    }};

    public static String get(String code) {
        return EMOJI_MAP.get(code);
    }
}
