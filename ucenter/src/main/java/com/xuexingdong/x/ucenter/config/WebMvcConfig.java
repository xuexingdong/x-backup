package com.xuexingdong.x.ucenter.config;

import com.xuexingdong.x.ucenter.interceptor.AuthorizationInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.web.reactive.config.WebFluxConfigurer;

@Configuration
@Profile("dev")
public class WebMvcConfig implements WebFluxConfigurer {

    @Autowired
    private AuthorizationInterceptor authorizationInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(authorizationInterceptor).excludePathPatterns("/tokens/**");
    }
}
