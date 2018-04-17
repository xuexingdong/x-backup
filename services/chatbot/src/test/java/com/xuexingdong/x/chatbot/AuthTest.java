package com.xuexingdong.x.chatbot;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.springframework.data.redis.core.ReactiveValueOperations;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

import static org.mockito.BDDMockito.given;

@RunWith(SpringRunner.class)
@AutoConfigureWebTestClient
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class AuthTest {

    @Autowired
    private WebTestClient webClient;

    @MockBean
    private ReactiveRedisTemplate<String, String> template;

    @MockBean
    private ReactiveValueOperations<String, String> opsForValue;

    @Test
    public void testExample() throws Exception {
        given(template.opsForValue()).willReturn(opsForValue);
        given(template.opsForValue().get("test")).willReturn(Mono.just("test"));
        webClient.get().uri("/chatbot/auth?secretKey=test").exchange().expectStatus().isOk().expectBody(String.class).isEqualTo("test");
    }
}
