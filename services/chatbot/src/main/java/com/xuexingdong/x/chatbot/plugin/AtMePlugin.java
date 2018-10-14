package com.xuexingdong.x.chatbot.plugin;

import com.xuexingdong.x.chatbot.core.ChatbotPlugin;
import com.xuexingdong.x.chatbot.webwx.WebWxResponse;
import com.xuexingdong.x.chatbot.webwx.WebWxTextMessage;
import com.xuexingdong.x.chatbot.webwx.WebWxUtils;
import org.apache.commons.lang3.tuple.Pair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class AtMePlugin implements ChatbotPlugin {

    private static final Logger logger = LoggerFactory.getLogger(AtMePlugin.class);

    @Override
    public int order() {
        return -100;
    }

    @Override
    public Optional<WebWxResponse> handleText(WebWxTextMessage textMessage) {
        if (WebWxUtils.isFromChatroom(textMessage)) {
            // when a text message is from a chatroom
            // it must be start with the message sender's username+:<br/>
            Optional<Pair<String, String>> pairOptional = WebWxUtils.parseFromChatroomTextMessage(textMessage.getContent());
            if (pairOptional.isPresent()) {
                Pair<String, String> pair = pairOptional.get();
                boolean atme = WebWxUtils.parseAtedUsernames(pair.getRight()).contains("🤖");
                if (atme) {
                    logger.info("Chatroom {}'s {} @ me", textMessage.getFromNickname(), pair.getLeft());
                    WebWxResponse response = new WebWxResponse();
                    response.setToUsername(textMessage.getFromUsername());
                    response.setContent("有事别@我，没事更别@我，不急的事不需要@，紧急的事请打电话！");
                    return Optional.of(response);
                }
            }
        }
        return Optional.empty();
    }
}