package com.xuexingdong.x.chatbot.plugin;

import com.xuexingdong.x.chatbot.core.ChatbotPlugin;
import com.xuexingdong.x.chatbot.webwx.WebWXResponse;
import com.xuexingdong.x.chatbot.webwx.WebWXTextMessage;
import org.jsoup.Jsoup;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

public class XiaobingPlugin implements ChatbotPlugin {

    private static final Logger logger = LoggerFactory.getLogger(XiaobingPlugin.class);

    @Autowired
    private RestTemplate restTemplate;

    @Override
    public int order() {
        return 9999;
    }

    @Override
    public Optional<WebWXResponse> handle(WebWXTextMessage textMessage) {
        WebWXResponse response = new WebWXResponse();
        response.setToUsername(textMessage.getFromUsername());
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Referer", "http://webapps.msxiaobing.com/simplechat");
        String json = "{\"SenderId\":\"123\",\"Content\":{\"Text\":\"%s\",\"Image\":\"\"}}";
        HttpEntity<String> formEntity = new HttpEntity<>(String.format(json, textMessage.getContent()), headers);
        String url = "http://webapps.msxiaobing.com/simplechat/getresponse";
        String html = restTemplate.postForObject(url, formEntity, String.class);
        if (html != null) {
            String content = Jsoup.parse(html).selectFirst(".xb_conv_left").text();
            logger.info("发送【{}】小冰回复【{}】", textMessage.getContent(), content);
            response.setContent(content);
            return Optional.of(response);
        }
        return Optional.empty();
    }
}
