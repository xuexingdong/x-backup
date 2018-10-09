package com.xuexingdong.x.chatbot.plugin;

import com.xuexingdong.x.chatbot.component.ChatbotClientComponent;
import com.xuexingdong.x.chatbot.core.ChatbotPlugin;
import com.xuexingdong.x.chatbot.enumeration.ChatStatus;
import com.xuexingdong.x.chatbot.webwx.WebWxResponse;
import com.xuexingdong.x.chatbot.webwx.WebWxTextMessage;
import com.xuexingdong.x.chatbot.webwx.WebWxUtils;
import com.xuexingdong.x.common.utils.XRandomUtils;
import com.xuexingdong.x.entity.User;
import com.xuexingdong.x.mapper.UserMapper;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotEmpty;
import java.util.List;
import java.util.Optional;
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
    private ChatbotClientComponent chatbotClientComponent;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Autowired
    private UserMapper userMapper;

    @Override
    public void init() {
        // chatid user_id mapping init
        List<User> users = userMapper.findAll();
        for (User user : users) {
            String username = chatbotClientComponent.getUsernameByRemarkName(user.getUsername());
            if (StringUtils.isNotEmpty(username)) {
                stringRedisTemplate.opsForHash().put("chatbot:server:chatid_user_id_mapping", username, user.getId());
            }
        }

    }

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
        if (!WebWxUtils.isPrivateChat(textMessage)) {
            return Optional.empty();
        }
        String selfChatId = chatbotClientComponent.getSelfUsername();
        if (StringUtils.isEmpty(selfChatId)) {
            logger.warn("Self chat id is empty");
            return Optional.empty();
        }
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
            return Optional.empty();
        }
        Optional<ChatStatus> lastChatStatusOpt = getLastChatStatusByChatId(otherChatId);
        // 如果上条消息状态与此次相反，则代表产生了一次对话，可以加积分
        if (lastChatStatusOpt.isPresent() && lastChatStatusOpt.get() != currentChatStatus) {
            String otherUserId = (String) stringRedisTemplate.opsForHash().get("chatbot:server:chatid_user_id_mapping", otherChatId);
            // 未注册用户
            if (StringUtils.isEmpty(otherUserId)) {
                String userId = XRandomUtils.randomUUID();
                User user = new User()
                        .setId(userId)
                        // 默认用户名是昵称
                        .setUsername(otherRemarkName)
                        .setPassword("")
                        .setSalt("")
                        .setPoints(initPoint + chatPoint);
                try {
                    userMapper.insert(user);
                } catch (Exception ex) {
                    logger.error("Insert user {} failed", textMessage.getFromRemarkName());
                    return Optional.empty();
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
                return Optional.empty();
            }
            logger.info("User {} add {} points", otherChatId, chatPoint);
        }
        // 记录和这个人的消息收发情况
        setLastChatStatusToChatId(otherChatId, currentChatStatus);
        return Optional.empty();
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
}
