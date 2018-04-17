package com.xuexingdong.x.ucenter;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.BodyInserters;

@RunWith(SpringRunner.class)
@AutoConfigureWebTestClient
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class TokenTest {

    @Autowired
    private WebTestClient webClient;

    @Test
    public void testExample() {
        webClient.post().uri("/ucenter/tokens").contentType(MediaType.APPLICATION_JSON)
                .exchange().expectStatus().isBadRequest().expectBody(String.class).isEqualTo("用户名/密码不能为空");
        webClient.post().uri("/ucenter/tokens").contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromFormData("username", "1").with("password", "1"))
                .exchange().expectBody(String.class).isEqualTo("");
    }
}