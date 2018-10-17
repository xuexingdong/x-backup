package com.xuexingdong.x.chatbot.plugin;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.xuexingdong.x.chatbot.config.RabbitConfig;
import com.xuexingdong.x.chatbot.core.ChatbotPlugin;
import com.xuexingdong.x.chatbot.webwx.MsgType;
import com.xuexingdong.x.chatbot.webwx.WebWxResponse;
import com.xuexingdong.x.chatbot.webwx.WebWxTextMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class TestPlugin implements ChatbotPlugin {

    private static final Logger logger = LoggerFactory.getLogger(TestPlugin.class);

    private final StringRedisTemplate stringRedisTemplate;

    private final RabbitTemplate rabbitTemplate;

    private final ObjectMapper objectMapper;

    public TestPlugin(StringRedisTemplate stringRedisTemplate, RabbitTemplate rabbitTemplate, ObjectMapper objectMapper) {
        this.stringRedisTemplate = stringRedisTemplate;
        this.rabbitTemplate = rabbitTemplate;
        this.objectMapper = objectMapper;
    }

    @Override
    public int order() {
        return 0;
    }

    @Override
    public Optional<WebWxResponse> handleText(WebWxTextMessage textMessage) {
        // 不是发给filehelper的消息不会触发该测试
        String selfUsername = stringRedisTemplate.opsForValue().get("chatbot:self_chatid");
        if (!textMessage.getFromUsername().equals(selfUsername)) {
            return Optional.empty();
        }
        WebWxResponse response = new WebWxResponse();
        // send test message to filehelper
        response.setToUsername("filehelper");
        switch (textMessage.getContent()) {
            case "测试":
                response.setMsgType(MsgType.TEXT);
                response.setContent("测试类型: 文字,emoji,图片,文件");
                break;
            case "测试文字":
                response.setMsgType(MsgType.TEXT);
                response.setContent(textMessage.getFromUsername());
                break;
            case "测试emoji":
                response.setMsgType(MsgType.TEXT);
                response.setContent("😍");
                break;
            case "测试图片":
                response.setMsgType(MsgType.IMAGE);
                response.setContent("https://ss0.bdstatic.com/70cFuHSh_Q1YnxGkpoWK1HF6hhy/it/u=2665386746,1443186566&fm=27&gp=0.jpg");
                break;
            case "测试文件":
                response.setMsgType(MsgType.FILE);
                response.setContent("https://github.com/xuexingdong/chatbot/blob/master/README.md");
                break;
            default:
                return Optional.empty();
        }
        return Optional.of(response);
    }

    /**
     * 10分钟一次 心跳检测
     *
     * @throws JsonProcessingException
     */
    @Scheduled(fixedRate = 10 * 60 * 1000)
    public void heartbeat() throws JsonProcessingException {
        WebWxResponse response = new WebWxResponse();
        response.setToUsername("filehelper");
        response.setContent("心跳检测");
        rabbitTemplate.convertAndSend(RabbitConfig.SEND_QUEUE, objectMapper.writeValueAsString(response));
        logger.info("心跳检测");
    }
}
