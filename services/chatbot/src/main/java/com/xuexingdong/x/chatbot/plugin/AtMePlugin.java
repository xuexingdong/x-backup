package com.xuexingdong.x.chatbot.plugin;

import com.xuexingdong.x.chatbot.component.StatisticComponent;
import com.xuexingdong.x.chatbot.core.ChatbotPlugin;
import com.xuexingdong.x.chatbot.webwx.*;
import org.apache.commons.lang3.tuple.Pair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Component
public class AtMePlugin implements ChatbotPlugin {

    private static final Logger logger = LoggerFactory.getLogger(AtMePlugin.class);

    private static final Map<MsgType, String> MAP = new HashMap<MsgType, String>() {{
        put(MsgType.TEXT, "文本");
        put(MsgType.IMAGE, "图片");
        put(MsgType.EMOTION, "表情");
        put(MsgType.LOCATION, "定位");
    }};

    @Autowired
    private StatisticComponent statisticComponent;

    @Override
    public int order() {
        return -100;
    }


    @Override
    public Optional<WebWxResponse> handleText(WebWxTextMessage textMessage) {
        if (WebWxUtils.isFromChatroom(textMessage)) {
            // when a message is from a chatroom
            // the content must be start with the message sender's username+:<br/>
            Optional<Pair<String, String>> pairOptional = WebWxUtils.parseFromChatroomContent(textMessage.getContent());
            if (pairOptional.isPresent()) {
                Pair<String, String> pair = pairOptional.get();
                String realFromUsername = pair.getLeft();
                statisticComponent.add(realFromUsername, textMessage.getToUsername(), textMessage.getMsgType());
                String realContent = pair.getLeft();
                boolean atme = WebWxUtils.parseAtedUsernames(realContent).contains("🤖");
                if (atme) {
                    logger.info("Chatroom {}'s {} @ me, content is {}", textMessage.getFromNickname(), realFromUsername, realContent);
                    WebWxResponse response = new WebWxResponse();
                    response.setToUsername(textMessage.getFromUsername());
                    switch (realContent) {
                        case "统计":
                            Map<MsgType, Integer> result = statisticComponent.count(realFromUsername, textMessage.getToUsername());
                            if (result.isEmpty()) {
                                response.setContent("今天你还没有说过话");
                            } else {
                                StringBuilder sb = new StringBuilder();
                                sb.append("发言情况如下:\n");
                                for (Map.Entry<MsgType, Integer> entry : result.entrySet()) {
                                    sb.append(String.format("%s类消息%s条\n", MAP.getOrDefault(entry.getKey(), "未知"), entry.getValue()));
                                }
                                sb.append("粗略统计，重启机器人统计清零\n");
                                response.setContent(sb.toString());
                            }
                            break;
                        default:
                            response.setContent("暂无对应指令");
                            break;
                    }
                    return Optional.of(response);
                }
            }
        }
        return Optional.empty();
    }

    @Override
    public Optional<WebWxResponse> handleImage(WebWxImageMessage imageMessage) {
        countChatroomMessage(imageMessage);
        return Optional.empty();
    }

    @Override
    public Optional<WebWxResponse> handleEmotion(WebWxEmotionMessage emotionMessage) {
        countChatroomMessage(emotionMessage);
        return Optional.empty();
    }

    @Override
    public Optional<WebWxResponse> handleLocation(WebWxLocationMessage locationMessage) {
        countChatroomMessage(locationMessage);
        return Optional.empty();
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