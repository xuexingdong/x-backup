package com.xuexingdong.x.chatbot.core;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.xuexingdong.x.chatbot.config.RabbitConfig;
import com.xuexingdong.x.chatbot.webwx.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.stream.Collectors;

@Component
public class WeChatListener {

    private static final Logger logger = LoggerFactory.getLogger(WeChatListener.class);

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private List<ChatbotPlugin> chatbotPlugins;

    @Autowired
    private AmqpAdmin admin;

    @Autowired
    private MongoTemplate mongoTemplate;

    @Autowired
    public void setChatbotPlugins(List<ChatbotPlugin> chatbotPlugins) {
        this.chatbotPlugins = chatbotPlugins;
    }

    @RabbitListener(queues = RabbitConfig.RECEIVE_QUEUE)
    @RabbitHandler
    public void process(@Payload Message message) {
        List<WebWxResponse> responses = new ArrayList<>();
        WebWxMessage wxMessage;
        String msg = new String(message.getBody(), StandardCharsets.UTF_8);
        // persist chat record
        mongoTemplate.insert(msg, "chat_record");
        try {
            wxMessage = objectMapper.readValue(msg, WebWxMessage.class);
            switch (wxMessage.getMsgType()) {
                case TEXT:
                    WebWxTextMessage textMessage = objectMapper.readValue(
                            msg, WebWxTextMessage.class);
                    for (ChatbotPlugin chatBotPlugin : chatbotPlugins) {
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
                    for (ChatbotPlugin chatBotPlugin : chatbotPlugins) {
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
                    WebWxEmotionMessage emotionMessage = objectMapper.readValue(msg, WebWxEmotionMessage.class);
                    for (ChatbotPlugin chatBotPlugin : chatbotPlugins) {
                        Optional<WebWxResponse> responseOptional = chatBotPlugin.handleEmotion(emotionMessage);
                        if (responseOptional.isPresent()) {
                            responses.add(responseOptional.get());
                            if (chatBotPlugin.isExclusive()) {
                                break;
                            }
                        }
                    }
                    break;
                case VIDEO2:
                    break;
                case CALL:
                    break;
                case LOCATION:
                    WebWxLocationMessage locationMessage = objectMapper.readValue(msg, WebWxLocationMessage.class);
                    for (ChatbotPlugin chatBotPlugin : chatbotPlugins) {
                        Optional<WebWxResponse> responseOptional = chatBotPlugin.handleLocation(locationMessage);
                        if (responseOptional.isPresent()) {
                            responses.add(responseOptional.get());
                            if (chatBotPlugin.isExclusive()) {
                                break;
                            }
                        }
                    }
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
            logger.error("Error decoding json: {}", msg);
            return;
        }
        responses.forEach(r -> {
            // r.setToUsername("filehelper");
            // 文本消息追加机器人后缀
            if (r.getMsgType() == MsgType.TEXT) {
                r.setContent(r.getContent() + "\n(response by 🤖)");
            }
            logger.info("向用户【{}】发送消息: {}", r.getToUsername(), r.getContent());
            try {
                rabbitTemplate.convertAndSend(RabbitConfig.SEND_QUEUE, objectMapper.writeValueAsString(r));
            } catch (JsonProcessingException e) {
                logger.error("json处理错误: {}", e);
            }
        });
    }

    @PostConstruct
    public void init() {
        admin.declareQueue(new Queue(RabbitConfig.RECEIVE_QUEUE, false));
        admin.declareQueue(new Queue(RabbitConfig.SEND_QUEUE, false));
        // 清空消息队列
        admin.purgeQueue(RabbitConfig.RECEIVE_QUEUE, false);
        admin.purgeQueue(RabbitConfig.SEND_QUEUE, false);
        // 插件按照order从小到大排序
        chatbotPlugins = chatbotPlugins.stream().sorted(Comparator.comparingInt(ChatbotPlugin::order)).collect(Collectors.toList());
    }
}