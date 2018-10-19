package com.xuexingdong.x.chatbot.plugin;

import com.xuexingdong.x.chatbot.component.LocationComponent;
import com.xuexingdong.x.chatbot.component.StatisticComponent;
import com.xuexingdong.x.chatbot.config.PluginConfig;
import com.xuexingdong.x.chatbot.core.ChatbotPlugin;
import com.xuexingdong.x.chatbot.event.Event;
import com.xuexingdong.x.chatbot.event.WebWxEvents;
import com.xuexingdong.x.chatbot.webwx.MsgType;
import com.xuexingdong.x.chatbot.webwx.WebWxTextMessage;
import com.xuexingdong.x.chatbot.webwx.WebWxUtils;
import com.xuexingdong.x.entity.User;
import com.xuexingdong.x.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Component
public class FunctionPlugin implements ChatbotPlugin {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private PluginConfig pluginConfig;

    @Autowired
    private StatisticComponent statisticComponent;

    @Autowired
    private LocationComponent locationComponent;

    @Override
    public int order() {
        return -1;
    }

    @Override
    public List<Event> handleText(WebWxTextMessage textMessage) {
        List<Event> events = new ArrayList<>();
        // only private chat can trigger function
        if (WebWxUtils.isPrivateChat(textMessage) && textMessage.getContent().startsWith("#")) {
            String keyword = textMessage.getContent().substring(1);
            StringBuilder sb = new StringBuilder();
            User user = userMapper.findByRemarkName(textMessage.getFromRemarkName());
            switch (keyword) {
                case "帮助":
                    sb.append("功能列表如下\n");
                    for (String function : pluginConfig.getFunctions()) {
                        sb.append("#").append(function).append("\n");
                    }
                    break;
                case "统计":
                    Map<MsgType, Integer> result = statisticComponent.count(textMessage.getFromUsername(), textMessage.getToUsername(), LocalDate.now());
                    String content = statisticComponent.mapToString(result);
                    sb.append("今日聊天情况如下\n").append(content);
                    break;
                case "定位":
                    sb.append(locationComponent.getLocationInfo());
                    break;
                case "积分":
                    if (Objects.isNull(user)) {
                        sb.append("暂无积分信息");
                    } else {
                        sb.append("积分: ").append(user.getPoints());
                    }
                    break;
                default:
                    return events;
            }
            events.add(WebWxEvents.sendText(textMessage.getFromUsername(), sb.toString()));
        }
        return events;
    }
}


