package com.xuexingdong.x.chatbot.plugin;

import com.xuexingdong.x.chatbot.core.ChatbotPlugin;
import com.xuexingdong.x.chatbot.webwx.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class LogPlugin implements ChatbotPlugin {

    private static final Logger logger = LoggerFactory.getLogger(TestPlugin.class);

    @Override
    public boolean isExclusive() {
        return false;
    }

    @Override
    public int order() {
        return Integer.MIN_VALUE;
    }

    @Override
    public Optional<WebWxResponse> handleText(WebWxTextMessage textMessage) {
        logger.info("【{}】->【{}】, type: 【{}】, content: 【{}】", textMessage.getFromUsername(), textMessage.getToUsername(), textMessage.getMsgType(), textMessage.getContent());
        return Optional.empty();
    }

    @Override
    public Optional<WebWxResponse> handleImage(WebWxImageMessage imageMessage) {
        logger.info("【{}】->【{}】, type: 【{}】, url: 【{}】", imageMessage.getFromUsername(), imageMessage.getToUsername(), imageMessage.getMsgType(), imageMessage.getUrl());
        return Optional.empty();
    }

    @Override
    public Optional<WebWxResponse> handleEmotion(WebWxEmotionMessage emotionMessage) {
        logger.info("【{}】->【{}】, type: 【{}】, url: 【{}】", emotionMessage.getFromUsername(), emotionMessage.getToUsername(), emotionMessage.getMsgType(), emotionMessage.getUrl());
        return Optional.empty();
    }
}
