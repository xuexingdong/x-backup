package com.xuexingdong.x.chatbot.config;

import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitConfig {

    public static final String RECEIVE_QUEUE = "chatbot.receive";

    public static final String SEND_QUEUE = "chatbot.send";

    @Bean
    Queue queue() {
        return new Queue(RECEIVE_QUEUE, false);
    }
}

