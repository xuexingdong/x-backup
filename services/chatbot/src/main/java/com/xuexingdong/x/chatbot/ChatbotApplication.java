package com.xuexingdong.x.chatbot;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.client.RestTemplate;

@EnableScheduling
@SpringBootApplication
@MapperScan("com.xuexingdong.x.mapper")
public class ChatbotApplication {

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplateBuilder()
                .setReadTimeout(3000)
                .setConnectTimeout(3000)
                .build();
    }

    public static void main(String[] args) {

        SpringApplication.run(ChatbotApplication.class);
    }
}

