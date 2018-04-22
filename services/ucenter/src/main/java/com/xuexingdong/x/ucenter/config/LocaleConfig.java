package com.xuexingdong.x.ucenter.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.config.WebFluxConfigurer;
import org.springframework.web.server.i18n.FixedLocaleContextResolver;
import org.springframework.web.server.i18n.LocaleContextResolver;

@Configuration
public class LocaleConfig implements WebFluxConfigurer {

    @Bean
    public LocaleContextResolver localeContextResolver() {
        FixedLocaleContextResolver flcr = new FixedLocaleContextResolver();
        return flcr;
    }
}