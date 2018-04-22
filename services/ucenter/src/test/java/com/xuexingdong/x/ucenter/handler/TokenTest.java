package com.xuexingdong.x.ucenter.handler;

import com.xuexingdong.x.ucenter.BaseTest;
import com.xuexingdong.x.ucenter.exception.BusinessException;
import com.xuexingdong.x.ucenter.exception.ClientException;
import com.xuexingdong.x.ucenter.service.TokenService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.BodyInserters;
import reactor.core.publisher.Mono;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

public class TokenTest extends BaseTest {

    @Autowired
    private WebTestClient webClient;

    @MockBean
    private TokenService tokenService;

    @Test
    public void testUsernameOrPasswordShouldNotEmpty() {
        given(tokenService.generateToken(any())).willReturn(Mono.error(new ClientException("用户名/密码不能为空")));
        webClient.post().uri("/tokens").contentType(MediaType.APPLICATION_JSON)
                .exchange().expectStatus().isBadRequest().expectBody(String.class).isEqualTo("用户名/密码不能为空");
    }

    @Test
    public void testUsernameOrPasswordError() {
        given(tokenService.generateToken(any())).willReturn(Mono.error(new BusinessException("用户名/密码错误")));
        webClient.post().uri("/tokens").contentType(MediaType.APPLICATION_JSON)
                .exchange().expectStatus().is5xxServerError().expectBody(String.class).isEqualTo("用户名/密码错误");
    }

    @Test
    public void testSuccess() {
        given(tokenService.generateToken(any())).willReturn(Mono.error(new ClientException("用户名/密码不能为空")));
        webClient.post().uri("/tokens").contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromFormData("username", "1").with("password", "1"))
                .exchange().expectBody(String.class).isEqualTo("");
    }
}