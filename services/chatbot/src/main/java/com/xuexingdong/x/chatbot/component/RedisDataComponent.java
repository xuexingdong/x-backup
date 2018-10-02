package com.xuexingdong.x.chatbot.component;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

@Component
public class RedisDataComponent {
    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    public String getSelfUsername() {
        return stringRedisTemplate.opsForValue().get("chatbot:self_chatid");
    }

}
