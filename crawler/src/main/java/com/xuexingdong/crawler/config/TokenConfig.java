package com.xuexingdong.crawler.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "token")
public class TokenConfig {
    private String secretKey;

}
