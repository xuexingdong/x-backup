package com.xuexingdong.x.chatbot.component;

import com.xuexingdong.x.chatbot.webwx.MsgType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class StatisticComponent {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    public void add(String fromUsername, String toUsername, MsgType msgType) {
        stringRedisTemplate.opsForHash().increment("chatbot:server:statistic:" + fromUsername + "-" + toUsername, msgType.name(), 1);
    }

    public Map<MsgType, Integer> count(String fromUsername, String toUsername) {
        Map<MsgType, Integer> result = new HashMap<>();
        Map<Object, Object> map = stringRedisTemplate.opsForHash().entries("chatbot:server:statistic:" + fromUsername + "-" + toUsername);
        for (Map.Entry<Object, Object> entry : map.entrySet()) {
            result.put(MsgType.valueOf(String.valueOf(entry)), (int) entry.getValue());
        }
        return result;
    }
}
