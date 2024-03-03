package com.xxd.x.wechat.service.impl;

import com.xxd.x.wechat.sdk.WechatClient;
import com.xxd.x.wechat.sdk.model.TicketType;
import com.xxd.x.wechat.service.WechatService;
import org.apache.commons.lang3.tuple.Pair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

@Service
public class WechatServiceImpl implements WechatService {

    private static final Logger logger = LoggerFactory.getLogger(WechatServiceImpl.class);

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Autowired
    private WechatClient wechatClient;

    @Override
    public String getAccessToken() {
        Boolean exists = stringRedisTemplate.hasKey("wechat:access_token");
        if (Objects.isNull(exists) || !exists) {
            refreshAccessToken();
        }
        return stringRedisTemplate.opsForValue().get("wechat:access_token");
    }

    @Override
    public void refreshAccessToken() {
        Pair<String, Long> pair;
        try {
            pair = wechatClient.fetchAccessToken();
        } catch (IOException e) {
            logger.error("Network error");
            return;
        }
        logger.info("Get access token: {}", pair);
        stringRedisTemplate.opsForValue().set("wechat:access_token", pair.getLeft());
        // 提前五分钟过期
        stringRedisTemplate.expire("wechat:access_token", pair.getRight() - 300, TimeUnit.SECONDS);
    }


    @Override
    public String getJsApiTicket() {
        Boolean exists = stringRedisTemplate.hasKey("wechat:jsapi_ticket");
        if (Objects.isNull(exists) || !exists) {
            refreshAccessToken();
        }
        return stringRedisTemplate.opsForValue().get("wechat:jsapi_ticket");
    }

    @Override
    public void refreshJsApiTicket() {
        Pair<String, Long> pair;
        try {
            pair = wechatClient.fetchTicket(getAccessToken(), TicketType.JSAPI);
        } catch (IOException e) {
            logger.error("Network error");
            return;
        }
        logger.info("Get js api ticket: {}", pair);
        stringRedisTemplate.opsForValue().set("wechat:jsapi_ticket", pair.getLeft());
        // 提前五分钟过期
        stringRedisTemplate.expire("wechat:jsapi_ticket", pair.getRight() - 300, TimeUnit.SECONDS);
    }

    @Override
    public String getJsApiSignature() {
        return null;
    }

}
