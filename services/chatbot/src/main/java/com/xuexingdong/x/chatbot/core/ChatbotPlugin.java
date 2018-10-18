package com.xuexingdong.x.chatbot.core;

import com.xuexingdong.x.chatbot.event.Event;
import com.xuexingdong.x.chatbot.webwx.WebWxEmotionMessage;
import com.xuexingdong.x.chatbot.webwx.WebWxImageMessage;
import com.xuexingdong.x.chatbot.webwx.WebWxLocationMessage;
import com.xuexingdong.x.chatbot.webwx.WebWxTextMessage;

import java.util.ArrayList;
import java.util.List;

public interface ChatbotPlugin {

    default int order() {
        return Integer.MAX_VALUE;
    }

    default boolean isExclusive() {
        return true;
    }

    default List<Event> handleText(WebWxTextMessage textMessage) {
        return new ArrayList<>();
    }

    default List<Event> handleImage(WebWxImageMessage imageMessage) {
        return new ArrayList<>();
    }

    default List<Event> handleVoice(WebWxTextMessage textMessage) {
        return new ArrayList<>();
    }

    default List<Event> handleVideo(WebWxTextMessage textMessage) {
        return new ArrayList<>();
    }

    default List<Event> handleLocation(WebWxLocationMessage locationMessage) {
        return new ArrayList<>();
    }

    default List<Event> handleEmotion(WebWxEmotionMessage emotionMessage) {
        return new ArrayList<>();
    }
}
