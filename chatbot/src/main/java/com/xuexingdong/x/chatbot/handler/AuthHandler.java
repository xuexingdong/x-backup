package com.xuexingdong.x.chatbot.handler;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.util.Optional;

@Component
public class AuthHandler {

    private static final Logger logger = LoggerFactory.getLogger(AuthHandler.class);

    @Value("secretKey")
    public String secretKey;

    private final ReactiveRedisTemplate<String, String> template;

    @Autowired
    public AuthHandler(ReactiveRedisTemplate<String, String> template) {
        this.template = template;
    }

    public Mono<ServerResponse> auth(ServerRequest request) {
        Optional<String> secretKey = request.queryParam("secretKey");
        if (!secretKey.isPresent()) {
            logger.warn("密码不能为空");
            return ServerResponse.badRequest().body(BodyInserters.fromObject("密码不能为空"));
        }
        Mono<String> fromUsername = template.opsForValue().get(secretKey.get().toLowerCase());
        return ServerResponse.ok().body(BodyInserters.fromPublisher(fromUsername, String.class));
    }
}