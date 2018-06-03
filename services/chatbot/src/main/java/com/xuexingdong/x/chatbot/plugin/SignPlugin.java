package com.xuexingdong.x.chatbot.plugin;

import com.xuexingdong.x.chatbot.core.ChatbotPlugin;
import com.xuexingdong.x.chatbot.webwx.WebWxResponse;
import com.xuexingdong.x.chatbot.webwx.WebWxTextMessage;
import com.xuexingdong.x.mapper.UserMapper;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Objects;
import java.util.Optional;

@Component
public class SignPlugin implements ChatbotPlugin {
    private static final Logger logger = LoggerFactory.getLogger(SignPlugin.class);

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Autowired
    private UserMapper userMapper;

    @Override
    public int order() {
        return 0;
    }

    @Override
    public boolean isExclusive() {
        return false;
    }

    @Override
    public Optional<WebWxResponse> handleText(WebWxTextMessage textMessage) {
        if (StringUtils.isEmpty(textMessage.getFromRemarkName())) {
            return Optional.empty();
        }
        String dateStr = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"));
        String openid = (String) stringRedisTemplate.opsForHash().get("chatbot:openid_username_mapping", textMessage.getFromUsername());
        if (Objects.isNull(openid)) {
            logger.error("Can't find openid for remark name: {}", textMessage.getFromRemarkName());
            return Optional.empty();
        }
        Boolean signed = stringRedisTemplate.opsForValue().getBit("chatbot:sign:" + dateStr, openid.hashCode());
        // 已经签到
        if (BooleanUtils.isTrue(signed)) {
            return Optional.empty();
        }
        // 更新失败
        if (!userMapper.updatePointsByOpenid(openid, 5)) {
            return Optional.empty();
        }
        stringRedisTemplate.opsForValue().setBit("chatbot:sign:" + dateStr, openid.hashCode(), true);
        WebWxResponse webWXResponse = new WebWxResponse();
        webWXResponse.setToUsername(textMessage.getFromUsername());
        webWXResponse.setContent("签到成功，积分+5");
        return Optional.of(webWXResponse);

    }
}
