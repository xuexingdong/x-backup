package com.xuexingdong.x.chatbot.plugin;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.xuexingdong.x.chatbot.component.ChatbotClientComponent;
import com.xuexingdong.x.chatbot.config.RabbitConfig;
import com.xuexingdong.x.chatbot.core.ChatbotPlugin;
import com.xuexingdong.x.chatbot.event.Event;
import com.xuexingdong.x.chatbot.event.WebWxEvents;
import com.xuexingdong.x.chatbot.webwx.WebWxTextMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class TestPlugin implements ChatbotPlugin {

    private static final Logger logger = LoggerFactory.getLogger(TestPlugin.class);

    private final ChatbotClientComponent chatbotClientComponent;

    private final RabbitTemplate rabbitTemplate;

    private final ObjectMapper objectMapper;

    @Autowired
    public TestPlugin(RabbitTemplate rabbitTemplate, ChatbotClientComponent chatbotClientComponent, ObjectMapper objectMapper) {
        this.rabbitTemplate = rabbitTemplate;
        this.chatbotClientComponent = chatbotClientComponent;
        this.objectMapper = objectMapper;
    }

    @Override
    public int order() {
        return 0;
    }

    @Override
    public List<Event> handleText(WebWxTextMessage textMessage) {
        List<Event> events = new ArrayList<>();
        // 不是发给filehelper的消息不会触发该测试
        Optional<String> selfUsernameOptional = chatbotClientComponent.getSelfUsername();
        if (!selfUsernameOptional.isPresent()) {
            logger.warn("Self chat id is empty");
            return events;
        }
        String selfUsername = selfUsernameOptional.get();
        if (!textMessage.getFromUsername().equals(selfUsername)) {
            return events;
        }
        Event event;
        String toUsername = "filehelper";
        switch (textMessage.getContent()) {
            case "测试":
                event = WebWxEvents.sendText(toUsername, "测试类型: 文字,emoji,图片,文件");
                break;
            case "测试文字":
                event = WebWxEvents.sendText(toUsername, toUsername);
                break;
            case "测试emoji":
                event = WebWxEvents.sendText(toUsername, "😍");
                break;
            case "测试图片":
                event = WebWxEvents.sendImage(toUsername, "https://ss0.bdstatic.com/70cFuHSh_Q1YnxGkpoWK1HF6hhy/it/u=2665386746,1443186566&fm=27&gp=0.jpg");
                break;
            case "测试文件":
                event = WebWxEvents.sendFile(toUsername, "https://raw.githubusercontent.com/xuexingdong/chatbot/master/README.md");
                break;
            default:
                return events;
        }
        events.add(event);
        return events;
    }

    /**
     * 10分钟一次 心跳检测
     *
     * @throws JsonProcessingException
     */
    @Scheduled(fixedRate = 10 * 60 * 1000)
    public void heartbeat() throws JsonProcessingException {
        Event event = WebWxEvents.sendText("filehelper", "心跳检测");
        rabbitTemplate.convertAndSend(RabbitConfig.SEND_QUEUE, objectMapper.writeValueAsString(event));
        logger.info("心跳检测");
    }
}
