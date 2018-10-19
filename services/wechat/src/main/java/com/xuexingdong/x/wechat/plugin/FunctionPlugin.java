package com.xuexingdong.x.wechat.plugin;

import com.xuexingdong.x.entity.User;
import com.xuexingdong.x.mapper.UserMapper;
import com.xuexingdong.x.wechat.config.PluginConfig;
import com.xuexingdong.x.wechat.sdk.message.request.common.TextMessage;
import com.xuexingdong.x.wechat.sdk.message.response.TextResponse;
import com.xuexingdong.x.wechat.sdk.message.response.WechatResponseMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.Optional;

@Component
public class FunctionPlugin implements WechatPlugin {

    private static final Logger logger = LoggerFactory.getLogger(FunctionPlugin.class);

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private PluginConfig pluginConfig;

    @Value("${hostname}")
    private String hostname;

    @Override
    public Optional<? extends WechatResponseMessage> handleText(TextMessage textMessage) {
        TextResponse response;
        if (textMessage.getContent().startsWith("#")) {
            response = new TextResponse();
            response.setFromUserName(textMessage.getToUserName());
            response.setToUserName(textMessage.getFromUserName());
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
                case "注册":
                    response.setContent(String.format("<a href=\"http://%s/#/account?openid=%s\">点此注册</a>", hostname, textMessage.getFromUserName()));
                    break;
                case "积分":
                    User user = userMapper.findByOpenid(textMessage.getFromUserName());
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
        return Optional.empty();
    }
}
