package com.xuexingdong.x.chatbot.plugin;

import com.xuexingdong.x.chatbot.component.ChatbotClientComponent;
import com.xuexingdong.x.chatbot.component.LocationComponent;
import com.xuexingdong.x.chatbot.component.StatisticComponent;
import com.xuexingdong.x.chatbot.core.ChatbotPlugin;
import com.xuexingdong.x.chatbot.event.Event;
import com.xuexingdong.x.chatbot.event.WebWxEvents;
import com.xuexingdong.x.chatbot.webwx.MsgType;
import com.xuexingdong.x.chatbot.webwx.WebWxTextMessage;
import com.xuexingdong.x.chatbot.webwx.WebWxUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.Pair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Component
public class AtMePlugin implements ChatbotPlugin {

    private static final Logger logger = LoggerFactory.getLogger(AtMePlugin.class);

    @Autowired
    private StatisticComponent statisticComponent;

    @Autowired
    private ChatbotClientComponent chatbotClientComponent;

    @Autowired
    private LocationComponent locationComponent;

    @Override
    public int order() {
        return -100;
    }


    @Override
    public List<Event> handleText(WebWxTextMessage textMessage) {
        List<Event> events = new ArrayList<>();
        if (WebWxUtils.isFromChatroom(textMessage)) {
            // when a message is from a chatroom
            // the content must be start with the message sender's username+:<br/>
            Optional<Pair<String, String>> pairOptional = WebWxUtils.parseFromChatroomContent(textMessage.getContent());
            if (pairOptional.isPresent()) {
                Pair<String, String> pair = pairOptional.get();
                String realFromUsername = pair.getLeft();
                Pair<List<String>, String> atedUsernamesAndRealContent = WebWxUtils.parseAtedUsernamesAndRealContent(pair.getRight());
                List<String> atedUsernames = atedUsernamesAndRealContent.getLeft();
                boolean atme = atedUsernames.contains("🤖");
                if (atme) {
                    String realContent = atedUsernamesAndRealContent.getRight();
                    logger.info("Chatroom {}'s {} @ me, content is {}", textMessage.getFromNickname(), realFromUsername, realContent);
                    String returnText;
                    switch (realContent) {
                        case "统计":
                            Map<MsgType, Integer> result = statisticComponent.count(realFromUsername, textMessage.getFromUsername(), LocalDate.now());
                            // the name to show to the user
                            String finalName;
                            Optional<String> displayNameOptional = chatbotClientComponent.getDisplayNameInChatroomByUsername(textMessage.getFromUsername(), realFromUsername);
                            if (displayNameOptional.isPresent() && StringUtils.isNotEmpty(displayNameOptional.get())) {
                                finalName = displayNameOptional.get();
                            } else {
                                Optional<String> realNicknameOptional = chatbotClientComponent.getNicknameByUsername(realFromUsername);
                                finalName = realNicknameOptional.orElse("未知用户");
                            }
                            String reply = String.format("今日【%s】在群内的发言情况如下\n", finalName);
                            returnText = reply + statisticComponent.mapToString(result);
                            break;
                        case "定位":
                            returnText = locationComponent.getLocationInfo();
                            break;
                        default:
                            returnText = "暂无对应指令";
                            break;
                    }
                    events.add(WebWxEvents.sendText(textMessage.getFromUsername(), returnText));
                }
            }
        }
        return events;
    }
}