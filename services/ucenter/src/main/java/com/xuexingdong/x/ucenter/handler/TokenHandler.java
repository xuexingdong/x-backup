package com.xuexingdong.x.ucenter.handler;


import com.xuexingdong.x.ucenter.dto.UserDTO;
import com.xuexingdong.x.ucenter.model.Token;
import com.xuexingdong.x.ucenter.service.TokenService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Component
public class TokenHandler {

    private static final Logger logger = LoggerFactory.getLogger(TokenHandler.class);

    private final TokenService tokenService;

    @Autowired
    public TokenHandler(TokenService tokenService) {
        this.tokenService = tokenService;
    }

    public Mono<ServerResponse> generateToken(ServerRequest request) {
        Mono<Token> tokenMono = tokenService.generateToken(request.bodyToMono(UserDTO.class));
        return ServerResponse.ok().body(tokenMono, Token.class);
    }
}