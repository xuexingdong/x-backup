package com.xuexingdong.x.ucenter.handler;


import com.xuexingdong.x.ucenter.dto.UserDTO;
import com.xuexingdong.x.ucenter.service.UserService;
import com.xuexingdong.x.ucenter.vo.UserVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.util.Objects;

@Component
public class UserHandler {

    private static final Logger logger = LoggerFactory.getLogger(UserHandler.class);

    private final UserService userService;

    @Autowired
    public UserHandler(UserService userService) {
        this.userService = userService;
    }

    public Mono<ServerResponse> register(ServerRequest request) {
        Mono<UserDTO> userDTOMono = request.bodyToMono(UserDTO.class);
        String userId = request.headers().asHttpHeaders().getFirst(HttpHeaders.AUTHORIZATION);
        UserVO userVO = userService.getById(userId);
        if (Objects.isNull(userVO)) {
            return ServerResponse.notFound().build();
        }
        return ServerResponse.ok().body(Mono.justOrEmpty(userVO), UserVO.class);
    }

    public Mono<ServerResponse> login(ServerRequest request) {
        String userId = request.headers().asHttpHeaders().getFirst(HttpHeaders.AUTHORIZATION);
        UserVO userVO = userService.getById(userId);
        if (Objects.isNull(userVO)) {
            return ServerResponse.notFound().build();
        }
        return ServerResponse.ok().body(Mono.justOrEmpty(userVO), UserVO.class);
    }
}