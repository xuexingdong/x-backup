package com.xuexingdong.x.chatbot.core;

import com.xuexingdong.x.chatbot.webwx.WebWXResponse;
import com.xuexingdong.x.chatbot.webwx.WebWXTextMessage;

import java.util.Optional;

public interface ChatbotPlugin {

    default int order() {
        return Integer.MAX_VALUE;
    }

    Optional<WebWXResponse> handle(WebWXTextMessage textMessage);
}
