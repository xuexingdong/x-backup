package com.xuexingdong.x.chatbot.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

@Configuration
@ConfigurationProperties("plugin")
public class PluginConfig {

    private List<String> functions = new ArrayList<>();

    public List<String> getFunctions() {
        return functions;
    }
}
