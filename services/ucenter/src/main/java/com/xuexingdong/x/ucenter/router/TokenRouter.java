package com.xuexingdong.x.ucenter.router;

import com.xuexingdong.x.ucenter.handler.TokenHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;

import static org.springframework.web.reactive.function.server.RequestPredicates.POST;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
public class TokenRouter {

    @Bean
    public RouterFunction<?> tokenRoutes(TokenHandler tokenHandler) {
        return route(POST("/tokens"), tokenHandler::generateToken);
    }
}
