package com.xuexingdong.x.wishwall.router;

import com.xuexingdong.x.wishwall.handler.WishHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;

import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RequestPredicates.POST;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
public class Router {
    private final WishHandler wishHandler;

    @Autowired
    public Router(WishHandler wishHandler) {
        this.wishHandler = wishHandler;
    }

    @Bean
    public RouterFunction<?> routerFunction() {
        return route(GET("/wishwall/wishwall"), wishHandler::get)
                .andRoute(POST("/wishwall/wish"), wishHandler::post);
    }
}