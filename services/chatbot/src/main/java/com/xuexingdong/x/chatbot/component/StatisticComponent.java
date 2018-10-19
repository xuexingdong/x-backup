package com.xuexingdong.x.chatbot.component;

import com.xuexingdong.x.chatbot.webwx.MsgType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
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

    public void add(String fromUsername, String toUsername, MsgType msgType, LocalDate localDate) {
        String redisKey = getStatisticRedisKey(fromUsername, toUsername, localDate);
        stringRedisTemplate.opsForHash().increment(redisKey, msgType.name(), 1);
        stringRedisTemplate.expireAt(redisKey, localDateToDate(localDate.plusDays(1)));
    }

    public Map<MsgType, Integer> count(String fromUsername, String toUsername, LocalDate localDate) {
        String redisKey = getStatisticRedisKey(fromUsername, toUsername, localDate);
        Map<MsgType, Integer> result = new HashMap<>();
        Map<Object, Object> map = stringRedisTemplate.opsForHash().entries(redisKey);
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
            int total = 0;
            for (Map.Entry<MsgType, Integer> entry : result.entrySet()) {
                Integer count = entry.getValue();
                total += count;
                sb.append(String.format("%s类消息%s条\n", MAP.getOrDefault(entry.getKey(), "未知"), count));
            }
            sb.append(String.format("共计%d条\n", total));
            sb.append("重启机器人清零");
        }
        return sb.toString();
    }

    private String getStatisticRedisKey(String fromUsername, String toUsername, LocalDate localDate) {
        String dateString = DateTimeFormatter.ISO_LOCAL_DATE.format(localDate);
        return String.format("chatbot:statistic:%s:%s:%s", dateString, fromUsername, toUsername);
    }

    private Date localDateToDate(LocalDate localDate) {
        ZoneId zoneId = ZoneId.systemDefault();
        ZonedDateTime zdt = localDate.atStartOfDay(zoneId);
        return Date.from(zdt.toInstant());
    }
}
