package com.xuexingdong.x.chatbot.core;

import com.xuexingdong.x.chatbot.webwx.WebWXImageMessage;
import com.xuexingdong.x.chatbot.webwx.WebWXResponse;
import com.xuexingdong.x.chatbot.webwx.WebWXTextMessage;

import java.util.Optional;

public interface ChatbotPlugin {

    default int order() {
        return Integer.MAX_VALUE;
    }

    default Optional<WebWXResponse> handleText(WebWXTextMessage textMessage) {
        return Optional.empty();
    }

    default Optional<WebWXResponse> handleImage(WebWXImageMessage imageMessage) {
        return Optional.empty();
    }

    default Optional<WebWXResponse> handleVoice(WebWXTextMessage textMessage) {
        return Optional.empty();
    }

    default Optional<WebWXResponse> handleVideo(WebWXTextMessage textMessage) {
        return Optional.empty();
    }

    default Optional<WebWXResponse> handleLocation(WebWXTextMessage textMessage) {
        return Optional.empty();
    }
}
