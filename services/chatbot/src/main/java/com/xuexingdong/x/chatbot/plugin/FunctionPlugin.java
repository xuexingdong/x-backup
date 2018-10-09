package com.xuexingdong.x.chatbot.plugin;

import com.xuexingdong.x.chatbot.config.PluginConfig;
import com.xuexingdong.x.chatbot.core.ChatbotPlugin;
import com.xuexingdong.x.chatbot.webwx.WebWxResponse;
import com.xuexingdong.x.chatbot.webwx.WebWxTextMessage;
import com.xuexingdong.x.entity.User;
import com.xuexingdong.x.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.Optional;

@Component
public class FunctionPlugin implements ChatbotPlugin {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private PluginConfig pluginConfig;

    @Override
    public int order() {
        return -1;
    }

    @Override
    public Optional<WebWxResponse> handleText(WebWxTextMessage textMessage) {
        WebWxResponse response;
        if (!textMessage.getContent().startsWith("#")) {
            return Optional.empty();
        }
        response = new WebWxResponse();
        response.setToUsername(textMessage.getFromUsername());
        String keyword = textMessage.getContent().substring(1);
        StringBuilder sb = new StringBuilder();
        switch (keyword) {
            case "帮助":
                sb.append("功能列表如下\n");
                for (String function : pluginConfig.getFunctions()) {
                    sb.append("#").append(function).append("\n");
                }
                response.setContent(sb.toString());
                break;
            case "积分":
                User user = userMapper.findByUsername(textMessage.getFromRemarkName());
                if (Objects.isNull(user)) {
                    return Optional.empty();
                }
                response.setContent("积分: " + user.getPoints());
                break;
            default:
                response.setContent("没有该功能");
        }
        return Optional.of(response);

    }
}
