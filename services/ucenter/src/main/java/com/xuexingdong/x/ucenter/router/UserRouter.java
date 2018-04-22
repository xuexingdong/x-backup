package com.xuexingdong.x.ucenter.router;

import com.xuexingdong.x.ucenter.filter.TokenFilter;
import com.xuexingdong.x.ucenter.handler.UserHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;

import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RequestPredicates.POST;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
public class UserRouter {

    private final UserHandler userHandler;

    private final TokenFilter tokenFilter;

    @Autowired
    public UserRouter(TokenFilter tokenFilter, UserHandler userHandler) {
        this.tokenFilter = tokenFilter;
        this.userHandler = userHandler;
    }

    @Bean
    public RouterFunction<?> routerFunction() {
        return route(POST("/users"), userHandler::register)
                .filter(tokenFilter::filter);
    }
}