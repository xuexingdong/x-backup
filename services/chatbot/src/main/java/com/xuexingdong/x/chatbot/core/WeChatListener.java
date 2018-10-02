package com.xuexingdong.x.chatbot.core;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.xuexingdong.x.chatbot.config.RabbitConfig;
import com.xuexingdong.x.chatbot.webwx.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.stream.Collectors;

@Component
public class WeChatListener {

    private static final Logger logger = LoggerFactory.getLogger(WeChatListener.class);

    private final AmqpTemplate amqpTemplate;

    private final ObjectMapper objectMapper;

    private List<ChatbotPlugin> chatBotPlugins;

    private final AmqpAdmin admin;

    private final StringRedisTemplate stringRedisTemplate;

    @Autowired
    public WeChatListener(AmqpTemplate amqpTemplate, ObjectMapper objectMapper, AmqpAdmin admin, StringRedisTemplate stringRedisTemplate) {
        this.amqpTemplate = amqpTemplate;
        this.objectMapper = objectMapper;
        this.admin = admin;
        this.stringRedisTemplate = stringRedisTemplate;
    }

    @Autowired
    public void setChatBotPlugins(List<ChatbotPlugin> chatBotPlugins) {
        this.chatBotPlugins = chatBotPlugins;
    }

    @RabbitListener(queues = RabbitConfig.RECEIVE_QUEUE)
    @RabbitHandler
    public void process(@Payload Message message) {
        String msg = new String(message.getBody(), StandardCharsets.UTF_8);
        List<WebWxResponse> responses = new ArrayList<>();
        try {
            WebWxMessage wxMessage = objectMapper.readValue(msg, WebWxMessage.class);
            switch (wxMessage.getMsgType()) {
                case TEXT:
                    WebWxTextMessage textMessage = objectMapper.readValue(msg, WebWxTextMessage.class);
                    // 替换emoji表情代码
                    textMessage.setContent(replaceEmoji(textMessage.getContent()));
                    logger.info("收到用户【{}】的文本消息: {}", textMessage.getFromUsername(), textMessage.getContent());
                    for (ChatbotPlugin chatBotPlugin : chatBotPlugins) {
                        Optional<WebWxResponse> responseOptional = chatBotPlugin.handleText(textMessage);
                        if (responseOptional.isPresent()) {
                            responses.add(responseOptional.get());
                            if (chatBotPlugin.isExclusive()) {
                                break;
                            }
                        }
                    }
                    break;
                case IMAGE:
                    WebWxImageMessage imageMessage = objectMapper.readValue(msg, WebWxImageMessage.class);
                    logger.info("收到用户【{}】的图片消息: {}", imageMessage.getFromUsername(), imageMessage.getCreateTime());
                    for (ChatbotPlugin chatBotPlugin : chatBotPlugins) {
                        Optional<WebWxResponse> responseOptional = chatBotPlugin.handleImage(imageMessage);
                        responseOptional.ifPresent(responses::add);
                        if (chatBotPlugin.isExclusive()) {
                            break;
                        }
                    }
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
            }
        } catch (IOException e) {
            logger.error("json转换错误: {}", e);
            return;
        }
        responses.forEach(r -> {
            r.setToUsername("filehelper");
            // 文本消息追加机器人后缀
            if (r.getMsgType() == MsgType.TEXT) {
                r.setContent(r.getContent() + "\n(response by 🤖)");
            }
            logger.info("向用户【{}】发送消息: {}", r.getToUsername(), r.getContent());
            try {
                amqpTemplate.convertAndSend(RabbitConfig.SEND_QUEUE, objectMapper.writeValueAsString(r));
            } catch (JsonProcessingException e) {
                logger.error("json处理错误: {}", e);
            }
        });
    }

    private String replaceEmoji(String content) {
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
        // 清空redis
        stringRedisTemplate.delete("chatbot:*");
        // 清空消息队列
        admin.purgeQueue(RabbitConfig.RECEIVE_QUEUE, false);
        admin.purgeQueue(RabbitConfig.SEND_QUEUE, false);
        // 按照order倒序排序
        chatBotPlugins = chatBotPlugins.stream().sorted(Comparator.comparingInt(ChatbotPlugin::order).reversed()).collect(Collectors.toList());
    }
}