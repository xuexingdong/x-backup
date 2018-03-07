package com.xuexingdong.crawler;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

@SpringBootApplication
public class CrawlerApplication {
    public static void main(String[] args) {
        new SpringApplicationBuilder(CrawlerApplication.class).run(args);
    }
}
