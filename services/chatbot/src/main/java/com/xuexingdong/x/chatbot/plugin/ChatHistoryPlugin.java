package com.xuexingdong.x.chatbot.plugin;

import com.xuexingdong.x.chatbot.core.ChatbotPlugin;
import com.xuexingdong.x.chatbot.webwx.WebWXResponse;
import com.xuexingdong.x.chatbot.webwx.WebWXTextMessage;
import org.apache.commons.lang3.BooleanUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.Random;
import java.util.concurrent.TimeUnit;

@Component
@Profile({"test", "prod"})
public class ChatHistoryPlugin implements ChatbotPlugin {

    private static final Logger logger = LoggerFactory.getLogger(ChatHistoryPlugin.class);

    private static final String RANDOM_CHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";

    private final StringRedisTemplate stringRedisTemplate;

    @Autowired
    public ChatHistoryPlugin(StringRedisTemplate stringRedisTemplate) {
        this.stringRedisTemplate = stringRedisTemplate;
    }

    @Override
    public int order() {
        return 1;
    }

    @Override
    public Optional<WebWXResponse> handleText(WebWXTextMessage textMessage) {
        WebWXResponse response = new WebWXResponse();
        if ("😂".equals(textMessage.getContent())) {
            String secretKey;
            do {
                secretKey = genSecretKey();
                // 循环到不存在这个key为止
                if (BooleanUtils.isFalse(stringRedisTemplate.hasKey(secretKey.toLowerCase()))) {
                    stringRedisTemplate.opsForValue().set(secretKey.toLowerCase(), textMessage.getFromUsername(), 3, TimeUnit.MINUTES);
                    break;
                }
            } while (true);
            response.setContent("您的秘钥为: " + secretKey + "(忽略大小写，三分钟内有效)");
            logger.info("用户【{}】的秘钥: {}", textMessage.getFromUsername(), secretKey);
            return Optional.of(response);
        }
        return Optional.empty();
    }

    private static String genSecretKey() {
        int bound = RANDOM_CHARS.length();
        Random random = new Random();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 6; i++) {
            sb.append(RANDOM_CHARS.charAt(random.nextInt(bound)));
        }
        return sb.toString();
    }
}
