package com.xuexingdong.x.backend.config;

import com.xuexingdong.x.backend.interceptor.AuthInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.Validator;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.springframework.validation.beanvalidation.MethodValidationPostProcessor;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.i18n.FixedLocaleResolver;

import java.util.Locale;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Autowired
    private MessageSource messageSource;

    @Autowired
    private AuthInterceptor authInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(authInterceptor).addPathPatterns("/**")
                .excludePathPatterns("/users")
                .excludePathPatterns("/tokens/generate");
    }

    @Override
    public Validator getValidator() {
        return validator();
    }

    @Bean
    public LocalValidatorFactoryBean validator() {
        LocalValidatorFactoryBean localValidatorFactoryBean = new LocalValidatorFactoryBean();
        localValidatorFactoryBean.setValidationMessageSource(messageSource);
        return localValidatorFactoryBean;
    }

    @Bean
    public LocaleResolver localeResolver() {
        FixedLocaleResolver flr = new FixedLocaleResolver();
        flr.setDefaultLocale(Locale.CHINA);
        return flr;
    }

    @Bean
    MethodValidationPostProcessor MethodValidationPostProcessor() {
        return new MethodValidationPostProcessor();
    }
}