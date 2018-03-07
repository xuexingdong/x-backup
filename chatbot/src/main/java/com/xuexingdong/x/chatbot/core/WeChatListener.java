package com.xuexingdong.x.chatbot.core;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.xuexingdong.x.chatbot.webwx.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.stream.Collectors;

@Component
public class WeChatListener {

    private static final Logger logger = LoggerFactory.getLogger(WeChatListener.class);

    private static final String RECEIVE_QUEUE = "chatbot.receive";

    private static final String SEND_QUEUE = "chatbot.send";

    private final AmqpTemplate amqpTemplate;

    private final ObjectMapper objectMapper;

    private List<ChatbotPlugin> chatBotPlugins;

    @Autowired
    public WeChatListener(AmqpTemplate amqpTemplate, ObjectMapper objectMapper) {
        this.amqpTemplate = amqpTemplate;
        this.objectMapper = objectMapper;
    }

    @Autowired
    public void setChatBotPlugins(List<ChatbotPlugin> chatBotPlugins) {
        this.chatBotPlugins = chatBotPlugins;
    }

    @RabbitListener(queues = RECEIVE_QUEUE)
    @RabbitHandler
    public void process(@Payload Message message) {
        String msg = new String(message.getBody(), Charset.forName("UTF-8"));
        WebWXMessage wxMessage;
        Optional<WebWXResponse> response = Optional.empty();
        try {
            wxMessage = objectMapper.readValue(msg, WebWXMessage.class);
            switch (wxMessage.getMsgType()) {
                case TEXT:
                    WebWXTextMessage textMessage = objectMapper.readValue(msg, WebWXTextMessage.class);
                    textMessage.setContent(replaceEmoji(textMessage.getContent()));
                    logger.info("收到用户【{}】的文本消息: {}", textMessage.getFromUsername(), textMessage.getContent());
                    response = chatBotPlugins.stream().map(chatBotPlugin -> chatBotPlugin.handle(textMessage)).filter(Optional::isPresent).findFirst().orElse(Optional.empty());
                    break;
                case IMAGE:
                    break;
                case VOICE:
                    break;
                case VIDEO1:
                    break;
                case EMOTION:
                    break;
                case VIDEO2:
                    break;
                case CALL:
                    break;
                case LOCATION:
                    break;
                case SHARE_LOCATION:
                    break;
                case CARD:
                    break;
                case LINK:
                    break;
                case SYSTEM:
                    break;
                case BLOCKED:
                    break;
                default:
                    break;
            }
        } catch (IOException e) {
            logger.error("json转换错误: {}", e);
            return;
        }
        response.ifPresent(r -> {
            // NOTE 测试
            // r.setToUsername(wxMessage.getToUsername());
            // 文本消息追加机器人后缀
            if (r.getMsgType() == MsgType.TEXT) {
                r.setContent(r.getContent() + "\n(response by 🤖)");
            }
            try {
                amqpTemplate.convertAndSend(SEND_QUEUE, objectMapper.writeValueAsString(r));
            } catch (JsonProcessingException e) {
                logger.error("json处理错误: {}", e);
            }
        });
    }

    public String replaceEmoji(String content) {
        // 表情符号
        StringBuffer sb = new StringBuffer(content.length());
        Matcher m = Patterns.EMOJI_PATTERN.matcher(content);
        while (m.find()) {
            // 要转换成大写
            String emoji = Emoji.get(m.group(1).toUpperCase());
            if (emoji == null) {
                m.appendReplacement(sb, m.group(1));
            } else {
                m.appendReplacement(sb, emoji);
            }
        }
        m.appendTail(sb);
        return sb.toString();
    }

    @PostConstruct
    public void init() {
        chatBotPlugins = chatBotPlugins.stream().sorted(Comparator.comparingInt(ChatbotPlugin::order).reversed()).collect(Collectors.toList());
    }
}