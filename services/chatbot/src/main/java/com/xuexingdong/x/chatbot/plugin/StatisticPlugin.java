package com.xuexingdong.x.chatbot.plugin;

import com.xuexingdong.x.chatbot.component.StatisticComponent;
import com.xuexingdong.x.chatbot.core.ChatbotPlugin;
import com.xuexingdong.x.chatbot.webwx.*;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.Pair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.util.Base64;
import java.util.Optional;
import java.util.UUID;

@Component
public class StatisticPlugin implements ChatbotPlugin {

    private static final Logger logger = LoggerFactory.getLogger(StatisticPlugin.class);

    @Autowired
    private StatisticComponent statisticComponent;

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
        countChatroomMessage(textMessage);
        logger.info("【{}】->【{}】, type: 【{}】, content: 【{}】", textMessage.getFromUsername(), textMessage.getToUsername(), textMessage.getMsgType(), textMessage.getContent());
        return Optional.empty();
    }

    @Override
    public Optional<WebWxResponse> handleImage(WebWxImageMessage imageMessage) {
        countChatroomMessage(imageMessage);
        String filename = download(imageMessage.getBase64Content());
        if (StringUtils.isEmpty(filename)) {
            return Optional.empty();
        }
        logger.info("【{}】->【{}】, type: 【{}】, path: 【{}】", imageMessage.getFromUsername(), imageMessage.getToUsername(), imageMessage.getMsgType(), filename);
        return Optional.empty();
    }

    @Override
    public Optional<WebWxResponse> handleEmotion(WebWxEmotionMessage emotionMessage) {
        countChatroomMessage(emotionMessage);
        logger.info("【{}】->【{}】, type: 【{}】, url: 【{}】", emotionMessage.getFromUsername(), emotionMessage.getToUsername(), emotionMessage.getMsgType(), emotionMessage.getUrl());
        return Optional.empty();
    }

    @Override
    public Optional<WebWxResponse> handleLocation(WebWxLocationMessage locationMessage) {
        countChatroomMessage(locationMessage);
        String filename = download(locationMessage.getBase64Content());
        if (StringUtils.isEmpty(filename)) {
            return Optional.empty();
        }
        logger.info("【{}】->【{}】, type: 【{}】, url: 【{}】", locationMessage.getFromUsername(), locationMessage.getToUsername(), locationMessage.getMsgType(), filename);
        return Optional.empty();
    }

    private String download(String base64Content) {
        byte[] base64Bytes = Base64.getDecoder().decode(base64Content);
        String filename = "data/" + UUID.randomUUID().toString() + ".jpg";
        try {
            FileUtils.writeByteArrayToFile(new File(filename), base64Bytes);
        } catch (IOException e) {
            logger.error("Write image file error");
            return null;
        }
        return filename;
    }

    private void countChatroomMessage(WebWxMessage webWxMessage) {
        if (WebWxUtils.isFromChatroom(webWxMessage)) {
            // when a text message is from a chatroom
            // it must be start with the message sender's username+:<br/>
            Optional<Pair<String, String>> pairOptional = WebWxUtils.parseFromChatroomContent(webWxMessage.getContent());
            if (pairOptional.isPresent()) {
                Pair<String, String> pair = pairOptional.get();
                String realFromUsername = pair.getLeft();
                statisticComponent.add(realFromUsername, webWxMessage.getToUsername(), webWxMessage.getMsgType());
            }

        }
    }
}
