package com.xuexingdong.x.chatbot.plugin;

import com.xuexingdong.x.chatbot.core.ChatbotPlugin;
import com.xuexingdong.x.chatbot.webwx.*;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.util.Base64;
import java.util.Optional;
import java.util.UUID;

@Component
public class LogPlugin implements ChatbotPlugin {

    private static final Logger logger = LoggerFactory.getLogger(LogPlugin.class);

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
        byte[] base64Content = Base64.getDecoder().decode(imageMessage.getBase64Content());
        String filename = "data/" + UUID.randomUUID().toString() + ".jpg";
        try {
            FileUtils.writeByteArrayToFile(new File(filename), base64Content);
        } catch (IOException e) {
            logger.error("Write image file error");
            return Optional.empty();
        }
        logger.info("【{}】->【{}】, type: 【{}】, path: 【{}】", imageMessage.getFromUsername(), imageMessage.getToUsername(), imageMessage.getMsgType(), filename);
        return Optional.empty();
    }

    @Override
    public Optional<WebWxResponse> handleEmotion(WebWxEmotionMessage emotionMessage) {
        logger.info("【{}】->【{}】, type: 【{}】, url: 【{}】", emotionMessage.getFromUsername(), emotionMessage.getToUsername(), emotionMessage.getMsgType(), emotionMessage.getUrl());
        return Optional.empty();
    }

    @Override
    public Optional<WebWxResponse> handleLocation(WebWxLocationMessage locationMessage) {
        return handleImage(locationMessage);
    }
}
