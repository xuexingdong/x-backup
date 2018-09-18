package com.xuexingdong.x.chatbot.plugin;

import com.xuexingdong.x.chatbot.core.ChatbotPlugin;
import com.xuexingdong.x.chatbot.webwx.WebWxResponse;
import com.xuexingdong.x.chatbot.webwx.WebWxTextMessage;
import com.xuexingdong.x.chatbot.webwx.WebWxUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class AtMePlugin implements ChatbotPlugin {

    private static final Logger logger = LoggerFactory.getLogger(AtMePlugin.class);

    @Override
    public int order() {
        return 0;
    }

    @Override
    public Optional<WebWxResponse> handleText(WebWxTextMessage textMessage) {
        // 群聊检测
        if (WebWxUtils.isChatroom(textMessage.getFromUsername()) && textMessage.getContent().startsWith("@🤖")) {
            logger.info("Chatroom {} @ me", textMessage.getFromNickName());
            WebWxResponse response = new WebWxResponse();
            response.setContent("机器人正在开发中");
            return Optional.of(response);
        }
        return Optional.empty();
    }
}