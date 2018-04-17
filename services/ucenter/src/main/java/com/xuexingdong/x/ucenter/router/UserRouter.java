package com.xuexingdong.x.ucenter.router;

import com.xuexingdong.x.ucenter.filter.TokenFilter;
import com.xuexingdong.x.ucenter.handler.UserHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;

import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RequestPredicates.POST;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
public class UserRouter {

    @Bean
    public RouterFunction<?> userRoutes(UserHandler userHandler, TokenFilter tokenFilter) {
        return route(POST("/ucenter/users/"), userHandler::register)
                .andRoute(GET("/ucenter/users/{id}"), userHandler::login)
                .filter(tokenFilter::filter);
    }
}