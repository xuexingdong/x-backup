package com.xuexingdong.x.chatbot.plugin;

import com.xuexingdong.x.chatbot.core.ChatbotPlugin;
import com.xuexingdong.x.chatbot.webwx.WebWxResponse;
import com.xuexingdong.x.chatbot.webwx.WebWxTextMessage;
import com.xuexingdong.x.chatbot.webwx.WebWxUtils;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.concurrent.TimeUnit;

@Component
public class SecretKeyPlugin implements ChatbotPlugin {

    private final StringRedisTemplate stringRedisTemplate;

    @Autowired
    public SecretKeyPlugin(StringRedisTemplate stringRedisTemplate) {
        this.stringRedisTemplate = stringRedisTemplate;
    }

    @Override
    public Optional<WebWxResponse> handleText(WebWxTextMessage textMessage) {
        // 必须是个人号
        if (WebWxUtils.isPerson(textMessage.getFromUsername())
                && "密钥".equals(textMessage.getContent())) {
            WebWxResponse response = new WebWxResponse();
            response.setToUsername(textMessage.getFromUsername());
            // 生成随机六位数
            String secretKey;
            String redisKey;
            do {
                secretKey = RandomStringUtils.randomNumeric(6);
                redisKey = "chatbot:secret_key:" + secretKey;
            } while (!BooleanUtils.isFalse(stringRedisTemplate.hasKey(redisKey)));
            stringRedisTemplate.opsForValue().set(redisKey, textMessage.getFromUsername(), 10, TimeUnit.MINUTES);
            response.setContent("密钥: " + secretKey + "（十分钟内有效）");
        }

        return Optional.empty();
    }
}
