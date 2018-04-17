package com.xuexingdong.x.chatbot;

import com.xuexingdong.x.chatbot.model.Chat;
import com.xuexingdong.x.chatbot.webwx.WebWXMessage;
import com.xuexingdong.x.chatbot.repository.ChatRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.BDDMockito.given;

@RunWith(SpringRunner.class)
@AutoConfigureWebTestClient
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class MongoTest {

    @Autowired
    private WebTestClient webClient;

    @MockBean
    private ChatRepository chatRepository;

    @Test
    public void testExample() throws Exception {
        // List<WebWXMessage> list = new ArrayList<>();
        // WebWXMessage webWXMessage = new WebWXMessage();
        // webWXMessage.setContent("content");
        // list.add(webWXMessage);
        // given(chatRepository.findAllBy(PageRequest.of(1, 10))).willReturn(Flux.fromIterable(list));
        webClient.get().uri("/chatbot/chats?nickname=陈老师").exchange().expectStatus().isOk().expectBodyList(Chat.class);
    }
}
