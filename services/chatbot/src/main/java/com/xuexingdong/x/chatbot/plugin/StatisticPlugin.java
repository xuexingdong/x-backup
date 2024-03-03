package com.xxd.x.chatbot.plugin;

import com.xxd.x.chatbot.component.StatisticComponent;
import com.xxd.x.chatbot.core.ChatbotPlugin;
import com.xxd.x.chatbot.event.Event;
import com.xxd.x.chatbot.webwx.*;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.Pair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;

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
    public List<Event> handleText(WebWxTextMessage textMessage) {
        countMessage(textMessage);
        logger.info("【{}】->【{}】, type: 【{}】, content: 【{}】", textMessage.getFromUsername(), textMessage.getToUsername(), textMessage.getMsgType(), textMessage.getContent());
        return new ArrayList<>();
    }

    @Override
    public List<Event> handleImage(WebWxImageMessage imageMessage) {
        countMessage(imageMessage);
        String filename = download(imageMessage.getBase64Content());
        if (StringUtils.isEmpty(filename)) {
            return new ArrayList<>();
        }
        logger.info("【{}】->【{}】, type: 【{}】, path: 【{}】", imageMessage.getFromUsername(), imageMessage.getToUsername(), imageMessage.getMsgType(), filename);
        return new ArrayList<>();
    }

    @Override
    public List<Event> handleEmotion(WebWxEmotionMessage emotionMessage) {
        countMessage(emotionMessage);
        logger.info("【{}】->【{}】, type: 【{}】, url: 【{}】", emotionMessage.getFromUsername(), emotionMessage.getToUsername(), emotionMessage.getMsgType(), emotionMessage.getUrl());
        return new ArrayList<>();
    }

    @Override
    public List<Event> handleLocation(WebWxLocationMessage locationMessage) {
        countMessage(locationMessage);
        String filename = download(locationMessage.getBase64Content());
        if (StringUtils.isEmpty(filename)) {
            return new ArrayList<>();
        }
        logger.info("【{}】->【{}】, type: 【{}】, url: 【{}】", locationMessage.getFromUsername(), locationMessage.getToUsername(), locationMessage.getMsgType(), filename);
        return new ArrayList<>();
    }

    private String download(String base64Content) {
        byte[] base64Bytes = Base64.getDecoder().decode(base64Content);
        String filename = "data/" + UUID.randomUUID().toString() + ".jpg";

        try {
            Files.write(Paths.get(filename), base64Bytes);
        } catch (IOException e) {
            logger.error("Write image file error");
            return "";
        }

        return filename;
    }

    private void countMessage(WebWxMessage webWxMessage) {
        LocalDate date = LocalDateTime.ofInstant(Instant.ofEpochSecond(webWxMessage.getCreateTime()), ZoneId.systemDefault()).toLocalDate();
        // message from chatroom (not self-sending)
        if (WebWxUtils.isFromChatroom(webWxMessage)) {
            Optional<Pair<String, String>> pairOptional = WebWxUtils.parseFromChatroomContent(webWxMessage.getContent());
            if (pairOptional.isPresent()) {
                Pair<String, String> pair = pairOptional.get();
                String realFromUsername = pair.getLeft();
                statisticComponent.add(realFromUsername, webWxMessage.getFromUsername(), webWxMessage.getMsgType(), date);
            }
        } else {
            statisticComponent.add(webWxMessage.getFromUsername(), webWxMessage.getToUsername(), webWxMessage.getMsgType(), date);
        }
    }
}
