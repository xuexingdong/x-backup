package com.xuexingdong.x.chatbot.core;

import com.xuexingdong.x.chatbot.webwx.*;

import java.util.Optional;

public interface ChatbotPlugin {

    default int order() {
        return Integer.MAX_VALUE;
    }

    default boolean isExclusive() {
        return true;
    }

    default Optional<WebWxResponse> handleText(WebWxTextMessage textMessage) {
        return Optional.empty();
    }

    default Optional<WebWxResponse> handleImage(WebWxImageMessage imageMessage) {
        return Optional.empty();
    }

    default Optional<WebWxResponse> handleVoice(WebWxTextMessage textMessage) {
        return Optional.empty();
    }

    default Optional<WebWxResponse> handleVideo(WebWxTextMessage textMessage) {
        return Optional.empty();
    }

    default Optional<WebWxResponse> handleLocation(WebWxLocationMessage locationMessage) {
        return Optional.empty();
    }

    default Optional<WebWxResponse> handleEmotion(WebWxEmotionMessage emotionMessage) {
        return Optional.empty();
    }
}
