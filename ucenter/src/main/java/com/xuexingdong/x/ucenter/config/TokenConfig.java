package com.xuexingdong.x.ucenter.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import javax.validation.constraints.Min;

@Configuration
@ConfigurationProperties(prefix = "token")
public class TokenConfig {

    @Value("${token.secret-key}")
    private String secretKey;

    @Value("${token.expire-time:0}")
    @Min(0)
    private long expire;

}
