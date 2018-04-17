package com.xuexingdong.x.chatbot.router;

import com.xuexingdong.x.chatbot.handler.AuthHandler;
import com.xuexingdong.x.chatbot.handler.ChatHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;

import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
public class Router {
    private final ChatHandler chatHandler;

    private final AuthHandler authHandler;

    @Autowired
    public Router(ChatHandler chatHandler, AuthHandler authHandler) {
        this.chatHandler = chatHandler;
        this.authHandler = authHandler;
    }

    @Bean
    public RouterFunction<?> routerFunction() {
        return route(GET("/chatbot/auth"), authHandler::auth)
                .andRoute(GET("/chatbot/chats/{id}"), chatHandler::getChat)
                .andRoute(GET("/chatbot/chats"), chatHandler::listChat);
    }
}