package com.xxd.x.chatbot.client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;

@Component
public class ChatbotDataContainer implements CommandLineRunner {

    private static final String REDIS_KEY_PREFIX = "chatbot:client:";

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    private List<Map<String, String>> friends;

    public List<Map<String, String>> getFriends() {
        return friends;
    }

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

    @Override
    public void run(String... args) {
        friends = initFriends();
    }

    private List<Map<String, String>> initFriends() {
        Set<String> keys = stringRedisTemplate.keys(REDIS_KEY_PREFIX + "friend:");
        if (keys == null) {
            return new ArrayList<>();
        }
        return keys.parallelStream().map(key -> {
            Map<Object, Object> map = stringRedisTemplate.opsForHash().entries(key);
            return map.entrySet().parallelStream().collect(Collectors.toMap(k -> (String) k.getKey(), v -> (String) v.getValue()));
        }).collect(Collectors.toList());
    }
}
