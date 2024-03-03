package com.xxd.x.chatbot.core;

import com.xxd.x.chatbot.event.Event;
import com.xxd.x.chatbot.webwx.WebWxEmotionMessage;
import com.xxd.x.chatbot.webwx.WebWxImageMessage;
import com.xxd.x.chatbot.webwx.WebWxLocationMessage;
import com.xxd.x.chatbot.webwx.WebWxTextMessage;

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
