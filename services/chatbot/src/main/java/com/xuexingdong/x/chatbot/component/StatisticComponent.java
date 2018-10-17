package com.xuexingdong.x.chatbot.component;

import com.xuexingdong.x.chatbot.webwx.MsgType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class StatisticComponent {

    private static final Map<MsgType, String> MAP = new HashMap<MsgType, String>() {{
        put(MsgType.TEXT, "文本");
        put(MsgType.IMAGE, "图片");
        put(MsgType.EMOTION, "表情");
        put(MsgType.LOCATION, "定位");
    }};

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    public void add(String fromUsername, String toUsername, MsgType msgType) {
        stringRedisTemplate.opsForHash().increment("chatbot:server:statistic:" + fromUsername + "-" + toUsername, msgType.name(), 1);
    }

    public Map<MsgType, Integer> analyze(String fromUsername, String toUsername) {
        Map<MsgType, Integer> result = new HashMap<>();
        Map<Object, Object> map = stringRedisTemplate.opsForHash().entries("chatbot:server:statistic:" + fromUsername + "-" + toUsername);
        for (Map.Entry<Object, Object> entry : map.entrySet()) {
            result.put(MsgType.valueOf(String.valueOf(entry.getKey())), Integer.parseInt(String.valueOf(entry.getValue())));
        }
        return result;
    }

    public String mapToString(Map<MsgType, Integer> result) {
        StringBuilder sb = new StringBuilder();
        if (result.isEmpty()) {
            sb.append("暂无发言记录");
        } else {
            for (Map.Entry<MsgType, Integer> entry : result.entrySet()) {
                sb.append(String.format("%s类消息%s条\n", MAP.getOrDefault(entry.getKey(), "未知"), entry.getValue()));
            }
            sb.append("粗略统计，重启后台统计清零");
        }
        return sb.toString();

    }

}
