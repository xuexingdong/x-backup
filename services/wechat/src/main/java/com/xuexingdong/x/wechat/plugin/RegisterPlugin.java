package com.xuexingdong.x.wechat.plugin;

import com.xuexingdong.x.wechat.sdk.message.request.common.TextMessage;
import com.xuexingdong.x.wechat.sdk.message.response.TextResponse;
import com.xuexingdong.x.wechat.sdk.message.response.WechatResponseMessage;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class RegisterPlugin implements WechatPlugin {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Override
    public Optional<? extends WechatResponseMessage> handleText(TextMessage textMessage) {
        if (!"#注册".equals(textMessage.getContent())) {
            return Optional.empty();
        }
        TextResponse textResponse = new TextResponse();
        textResponse.setFromUserName(textMessage.getToUserName());
        textResponse.setToUserName(textMessage.getFromUserName());
        String secretKey;
        do {
            secretKey = RandomStringUtils.randomAlphanumeric(6);
        } while (!BooleanUtils.isFalse(stringRedisTemplate.hasKey(secretKey)));
        textResponse.setContent(String.format("<a href=\"http://%s?openid=%s\">点此注册</a>", "e1718132.ngrok.io", textMessage.getFromUserName()));
        return Optional.of(textResponse);
    }
}
