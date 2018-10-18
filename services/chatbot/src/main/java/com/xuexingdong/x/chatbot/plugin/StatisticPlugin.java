package com.xuexingdong.x.chatbot.plugin;

import com.xuexingdong.x.chatbot.component.ChatbotClientComponent;
import com.xuexingdong.x.chatbot.component.StatisticComponent;
import com.xuexingdong.x.chatbot.core.ChatbotPlugin;
import com.xuexingdong.x.chatbot.event.Event;
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
import java.util.*;

@Component
public class StatisticPlugin implements ChatbotPlugin {

    private static final Logger logger = LoggerFactory.getLogger(StatisticPlugin.class);

    @Autowired
    private StatisticComponent statisticComponent;

    @Autowired
    private ChatbotClientComponent chatbotClientComponent;

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
            FileUtils.writeByteArrayToFile(new File(filename), base64Bytes);
        } catch (IOException e) {
            logger.error("Write image file error");
            return "";
        }
        return filename;
    }

    private void countMessage(WebWxMessage webWxMessage) {
        if (isSelfSendingMessage(webWxMessage)) {
            // TODO self-sending message handle logic
            return;
        }
        // message from chatroom (not self-sending)
        if (WebWxUtils.isFromChatroom(webWxMessage)) {
            // when a text message is from a chatroom
            // it must be start with the message sender's username+:<br/>
            Optional<Pair<String, String>> pairOptional = WebWxUtils.parseFromChatroomContent(webWxMessage.getContent());
            if (pairOptional.isPresent()) {
                Pair<String, String> pair = pairOptional.get();
                String realFromUsername = pair.getLeft();
                statisticComponent.add(realFromUsername, webWxMessage.getFromUsername(), webWxMessage.getMsgType());
            }
        }
        // message from other person
        else if (WebWxUtils.isFromPerson(webWxMessage)) {
            Optional<String> selfUsernameOptional = chatbotClientComponent.getSelfUsername();
            selfUsernameOptional.ifPresent(s ->
                    statisticComponent.add(webWxMessage.getFromUsername(), s, webWxMessage.getMsgType())
            );
        }
        // unknown source
        else {
            // TODO unknown source message handle logic
            logger.warn("Unknown message from {} to {}: {} ", webWxMessage.getFromUsername(), webWxMessage.getToUsername(), webWxMessage.getContent());
        }
    }

    private boolean isSelfSendingMessage(WebWxMessage webWxMessage) {
        Optional<String> selfUsernameOptional = chatbotClientComponent.getSelfUsername();
        return selfUsernameOptional.map(s -> s.equals(webWxMessage.getFromUsername())).orElse(false);
    }
}
