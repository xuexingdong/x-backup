package com.xuexingdong.x.chatbot.plugin;

import com.xuexingdong.x.chatbot.core.ChatbotPlugin;
import com.xuexingdong.x.chatbot.webwx.MsgType;
import com.xuexingdong.x.chatbot.webwx.WebWXResponse;
import com.xuexingdong.x.chatbot.webwx.WebWXTextMessage;
import org.apache.commons.lang3.BooleanUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.Random;
import java.util.concurrent.TimeUnit;

@Component
@Profile("dev")
public class TestPlugin implements ChatbotPlugin {

    @Override
    public int order() {
        return 0;
    }

    @Override
    public Optional<WebWXResponse> handle(WebWXTextMessage textMessage) {
        WebWXResponse response = new WebWXResponse();
        switch (textMessage.getContent()) {
            case "文字":
                response.setMsgType(MsgType.TEXT);
                response.setContent("测试文字");
                break;
            case "图片":
                response.setMsgType(MsgType.IMAGE);
                response.setContent("https://ss0.bdstatic.com/70cFuHSh_Q1YnxGkpoWK1HF6hhy/it/u=2665386746,1443186566&fm=27&gp=0.jpg");
                break;
            case "文件":
                response.setMsgType(MsgType.LINK);
                response.setContent("https://ss0.bdstatic.com/70cFuHSh_Q1YnxGkpoWK1HF6hhy/it/u=2665386746,1443186566&fm=27&gp=0.jpg");
                break;
            default:
                return Optional.empty();
        }
        return Optional.of(response);
    }
}
