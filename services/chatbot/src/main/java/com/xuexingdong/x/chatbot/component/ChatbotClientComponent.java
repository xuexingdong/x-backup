package com.xuexingdong.x.chatbot.component;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

@Component
public class ChatbotClientComponent {
    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    public String getSelfUsername() {
        return stringRedisTemplate.opsForValue().get("chatbot:self_chatid");
    }

    public String getUsernameByRemarkName(String remarkName) {
        return (String) stringRedisTemplate.opsForHash().get("chatbot:remark_name_username_mapping", remarkName);
    }

    public String getRemarkNameByUsername(String username) {
        return (String) stringRedisTemplate.opsForHash().get("chatbot:username_remark_name_mapping", username);
    }
}
