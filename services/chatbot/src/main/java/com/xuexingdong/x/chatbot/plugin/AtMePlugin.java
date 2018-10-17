package com.xuexingdong.x.chatbot.plugin;

import com.xuexingdong.x.chatbot.component.ChatbotClientComponent;
import com.xuexingdong.x.chatbot.component.StatisticComponent;
import com.xuexingdong.x.chatbot.core.ChatbotPlugin;
import com.xuexingdong.x.chatbot.webwx.MsgType;
import com.xuexingdong.x.chatbot.webwx.WebWxResponse;
import com.xuexingdong.x.chatbot.webwx.WebWxTextMessage;
import com.xuexingdong.x.chatbot.webwx.WebWxUtils;
import org.apache.commons.lang3.tuple.Pair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

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

    @Override
    public int order() {
        return -100;
    }


    @Override
    public Optional<WebWxResponse> handleText(WebWxTextMessage textMessage) {
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
                    WebWxResponse response = new WebWxResponse();
                    response.setToUsername(textMessage.getFromUsername());
                    switch (realContent) {
                        case "统计":
                            Map<MsgType, Integer> result = statisticComponent.analyze(realFromUsername, textMessage.getToUsername());
                            Optional<String> realNicknameOptional = chatbotClientComponent.getNicknameByUsername(realFromUsername);
                            if (realNicknameOptional.isPresent()) {
                                String reply = String.format("用户【%s】的发言情况如下\n", realNicknameOptional.get());
                                String content = reply + statisticComponent.mapToString(result);
                                response.setContent(content);
                            } else {
                                logger.warn("Can't find nickname for username {}", realFromUsername);
                                response.setContent("获取用户昵称出错");
                            }
                            break;
                        default:
                            response.setContent("暂无对应指令");
                            break;
                    }
                    return Optional.of(response);
                }
            }
        }
        return Optional.empty();
    }
}