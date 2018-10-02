package com.xuexingdong.x.chatbot.plugin;

import com.xuexingdong.x.chatbot.component.RedisDataComponent;
import com.xuexingdong.x.chatbot.core.ChatbotPlugin;
import com.xuexingdong.x.chatbot.webwx.WebWxResponse;
import com.xuexingdong.x.chatbot.webwx.WebWxTextMessage;
import com.xuexingdong.x.chatbot.webwx.WebWxUtils;
import com.xuexingdong.x.mapper.UserMapper;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.concurrent.TimeUnit;

/**
 * 积分插件，根据用户行为改变积分
 */
@Component
public class PointsPlugin implements ChatbotPlugin {

    private static final Logger logger = LoggerFactory.getLogger(PointsPlugin.class);

    private static final int CHAT_POINTS = 1;

    @Autowired
    private RedisDataComponent component;

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
        // 私聊才会加分
        if (WebWxUtils.isPerson(textMessage.getFromUsername()) && WebWxUtils.isPerson(textMessage.getToUsername())) {
            String selfChatId = component.getSelfUsername();
            if (StringUtils.isEmpty(selfChatId)) {
                logger.warn("Self chat id is empty");
                return Optional.empty();
            }
            // 本条消息的收发状态
            String currentChatStatus;
            // 对方的chatId
            String otherChatId;
            // 判断当前是接收者还是发送者
            if (selfChatId.equals(textMessage.getFromUsername())) {
                currentChatStatus = "SEND";
                otherChatId = textMessage.getToUsername();
            } else {
                currentChatStatus = "RECEIVE";
                otherChatId = textMessage.getFromUsername();
            }
            // 获取与对方上条消息的收发状态
            String lastChatStatus = stringRedisTemplate.opsForValue().get("chatbot:last_chat_status:" + otherChatId);
            // 如果上条消息状态与此次相反，则代表产生了一次对话，可以加积分
            if (StringUtils.isNotEmpty(lastChatStatus) && !lastChatStatus.equals(currentChatStatus)) {
                String otherUserId = (String) stringRedisTemplate.opsForHash().get("backend:chatid_userid_mapping", otherChatId);
                // 未知用户，说明这个人还没注册
                if (StringUtils.isEmpty(otherUserId)) {
                    logger.debug("Unknown user with chatId {}", otherChatId);
                    return Optional.empty();
                }
                boolean success = userMapper.plusPoints(otherUserId, CHAT_POINTS);
                // 更新失败
                if (!success) {
                    logger.debug("User {} add points failed", otherUserId);
                    return Optional.empty();
                }
                logger.info("User {} add {} points", otherChatId, CHAT_POINTS);
            }
            // 记录和这个人的消息收发情况
            stringRedisTemplate.opsForValue().set("chatbot:last_chat_status:" + otherChatId, currentChatStatus, 30, TimeUnit.MINUTES);
        }
        return Optional.empty();
    }
}
