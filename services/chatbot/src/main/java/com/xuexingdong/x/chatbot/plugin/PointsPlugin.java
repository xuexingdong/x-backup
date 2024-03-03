package com.xxd.x.chatbot.plugin;

import com.xxd.x.chatbot.client.ChatbotDataContainer;
import com.xxd.x.chatbot.core.ChatbotPlugin;
import com.xxd.x.chatbot.enumeration.ChatStatus;
import com.xxd.x.chatbot.event.Event;
import com.xxd.x.chatbot.webwx.WebWxTextMessage;
import com.xxd.x.chatbot.webwx.WebWxUtils;
import com.xxd.x.common.utils.XDateTimeUtils;
import com.xxd.x.common.utils.XRandomUtils;
import com.xxd.x.entity.User;
import com.xxd.x.mapper.UserMapper;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.validation.constraints.NotEmpty;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * 积分插件，根据用户行为改变积分
 */
@Component
public class PointsPlugin implements ChatbotPlugin {

    private static final Logger logger = LoggerFactory.getLogger(PointsPlugin.class);

    @NotEmpty
    @Value("${point.chat}")
    private int chatPoint;

    @NotEmpty
    @Value("${point.init}")
    private int initPoint;

    @Autowired
    private ChatbotDataContainer chatbotDataContainer;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Autowired
    private UserMapper userMapper;

    @Override
    public int order() {
        return -1;
    }

    @Override
    public boolean isExclusive() {
        return false;
    }

    @Override
    public List<Event> handleText(WebWxTextMessage textMessage) {
        List<Event> events = new ArrayList<>();
        if (WebWxUtils.isPrivateChat(textMessage)) {
            Optional<String> selfChatIdOptional = chatbotDataContainer.getSelfUsername();
            if (!selfChatIdOptional.isPresent()) {
                logger.warn("Self chat id is empty");
                return events;
            }
            String selfChatId = selfChatIdOptional.get();
            ChatStatus currentChatStatus;
            String otherChatId;
            String otherRemarkName;
            if (selfChatId.equals(textMessage.getFromUsername())) {
                // send by me
                currentChatStatus = ChatStatus.SEND;
                otherChatId = textMessage.getToUsername();
                otherRemarkName = textMessage.getToRemarkName();
            } else {
                // send to me
                currentChatStatus = ChatStatus.RECEIVE;
                otherChatId = textMessage.getFromUsername();
                otherRemarkName = textMessage.getFromRemarkName();
            }
            // filter empty remark name
            if (StringUtils.isEmpty(otherRemarkName)) {
                return events;
            }
            Optional<ChatStatus> lastChatStatusOptional = getLastChatStatusByChatId(otherChatId);
            // 如果上条消息状态与此次相反，则代表产生了一次对话，可以加积分
            if (lastChatStatusOptional.isPresent() && lastChatStatusOptional.get() != currentChatStatus) {
                String otherUserId = (String) stringRedisTemplate.opsForHash().get("chatbot:server:chatid_user_id_mapping", otherChatId);
                // 未注册用户
                if (StringUtils.isEmpty(otherUserId)) {
                    String userId = XRandomUtils.randomUUID();
                    User user = new User()
                            .setId(userId)
                            .setUsername("用户" + XDateTimeUtils.get13TimestampStr())
                            .setPassword("")
                            .setRemarkName(otherRemarkName)
                            .setSalt("")
                            .setPoints(initPoint + chatPoint);
                    try {
                        userMapper.insert(user);
                    } catch (Exception ex) {
                        logger.error("Insert user {} failed", textMessage.getFromRemarkName());
                        return new ArrayList<>();
                    }

                    otherUserId = userId;
                    // 存储chatId与userId的关系
                    stringRedisTemplate.opsForHash().put("chatbot:server:chatid_user_id_mapping", otherChatId, userId);
                    logger.info("User {} registered with chatId {}", userId, otherUserId);
                }
                boolean success = userMapper.plusPointsToUser(otherUserId, chatPoint);
                // 更新失败
                if (!success) {
                    logger.debug("User {} add points failed", otherUserId);
                    return new ArrayList<>();
                }
                logger.info("User {} add {} points", otherChatId, chatPoint);
            }
            // 记录和这个人的消息收发情况
            setLastChatStatusToChatId(otherChatId, currentChatStatus);
        }
        return events;
    }

    private Optional<ChatStatus> getLastChatStatusByChatId(String chatId) {
        String lastChatStatusText = stringRedisTemplate.opsForValue().get("chatbot:server:last_chat_status:" + chatId);
        if (StringUtils.isEmpty(lastChatStatusText)) {
            return Optional.empty();
        }
        return Optional.of(ChatStatus.valueOf(lastChatStatusText));
    }

    private void setLastChatStatusToChatId(String chatId, ChatStatus chatStatus) {
        stringRedisTemplate.opsForValue().set("chatbot:server:last_chat_status:" + chatId, chatStatus.name(), 10, TimeUnit.MINUTES);
    }

    @PostConstruct
    public void init() {
        // 清空redis
        Set<String> keys = stringRedisTemplate.keys("chatbot:server:*");
        if (keys != null && !keys.isEmpty()) {
            stringRedisTemplate.delete(keys);
        }
        // chatid user_id mapping init
        List<User> users = userMapper.findAll();
        for (User user : users) {
            Optional<String> usernameOptional = chatbotDataContainer.getUsernameByRemarkName(user.getRemarkName());
            usernameOptional.ifPresent(username ->
                    stringRedisTemplate.opsForHash().put("chatbot:server:chatid_user_id_mapping", username, user.getId()));
        }
    }
}

