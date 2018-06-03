package com.xuexingdong.x.chatbot.plugin;

import com.xuexingdong.x.chatbot.core.ChatbotPlugin;
import com.xuexingdong.x.chatbot.webwx.MsgType;
import com.xuexingdong.x.chatbot.webwx.WebWxResponse;
import com.xuexingdong.x.chatbot.webwx.WebWxTextMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class TestPlugin implements ChatbotPlugin {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Override
    public int order() {
        return 0;
    }

    @Override
    public Optional<WebWxResponse> handleText(WebWxTextMessage textMessage) {
        // 不是自己，就退出
        String self = stringRedisTemplate.opsForValue().get("chatbot:my_from_username");
        if (!textMessage.getFromUsername().equals(self)) {
            return Optional.empty();
        }
        WebWxResponse response = new WebWxResponse();
        response.setToUsername("filehelper");
        switch (textMessage.getContent()) {
            case "测试":
                response.setMsgType(MsgType.TEXT);
                response.setContent("测试类型: 文字,图片,文件");
                break;
            case "测试文字":
                response.setMsgType(MsgType.TEXT);
                response.setContent(textMessage.getFromUsername());
                break;
            case "测试图片":
                response.setMsgType(MsgType.IMAGE);
                response.setContent("https://ss0.bdstatic.com/70cFuHSh_Q1YnxGkpoWK1HF6hhy/it/u=2665386746,1443186566&fm=27&gp=0.jpg");
                break;
            case "测试文件":
                response.setMsgType(MsgType.LINK);
                response.setContent("https://ss0.bdstatic.com/70cFuHSh_Q1YnxGkpoWK1HF6hhy/it/u=2665386746,1443186566&fm=27&gp=0.jpg");
                break;
            default:
                return Optional.empty();
        }
        return Optional.of(response);
    }
}
