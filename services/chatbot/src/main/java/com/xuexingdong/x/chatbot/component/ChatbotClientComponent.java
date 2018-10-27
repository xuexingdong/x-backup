package com.xuexingdong.x.chatbot.component;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class ChatbotClientComponent {

    private static final String REDIS_KEY_PREFIX = "chatbot:client:";

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    public Optional<String> getSelfUsername() {
        return Optional.ofNullable(stringRedisTemplate.opsForValue().get(REDIS_KEY_PREFIX + "self_chatid"));
    }

    public Optional<String> getUsernameByRemarkName(String remarkName) {
        Object res = stringRedisTemplate.opsForHash().get(REDIS_KEY_PREFIX + "remark_name_username_mapping", remarkName);
        if (res == null) {
            return Optional.empty();
        }
        return Optional.of((String) res);
    }

    public Optional<String> getRemarkNameByUsername(String username) {
        Object res = stringRedisTemplate.opsForHash().get(REDIS_KEY_PREFIX + "username_remark_name_mapping", username);
        if (res == null) {
            return Optional.empty();
        }
        return Optional.of((String) res);
    }

    public Optional<String> getNicknameByUsername(String username) {
        Object res = stringRedisTemplate.opsForHash().get(REDIS_KEY_PREFIX + "username_nickname_mapping", username);
        if (res == null) {
            return Optional.empty();
        }
        return Optional.of((String) res);
    }

    public Optional<String> getDisplayNameInChatroomByUsername(String chatroomUsername, String username) {
        Object res = stringRedisTemplate.opsForHash().get(REDIS_KEY_PREFIX + "chatroom:" + chatroomUsername + ":username_display_name_mapping", username);
        if (res == null) {
            return Optional.empty();
        }
        return Optional.of((String) res);
    }
}
