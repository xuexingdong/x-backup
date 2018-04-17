package com.xuexingdong.x.ucenter.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.http.codec.ServerCodecConfigurer;
import org.springframework.http.codec.json.Jackson2JsonDecoder;
import org.springframework.http.codec.json.Jackson2JsonEncoder;
import org.springframework.util.MimeType;
import org.springframework.web.reactive.config.WebFluxConfigurer;

@Configuration
@Profile("dev")
public class WebMvcConfig implements WebFluxConfigurer {
    private static final MimeType[] MIME_TYPES = new MimeType[]{
            MimeType.valueOf("application/json"),
            MimeType.valueOf("application/stream+json")
    };

    @Autowired
    private ObjectMapper objectMapper;

    @Override
    public void configureHttpMessageCodecs(ServerCodecConfigurer configurer) {
        ServerCodecConfigurer.ServerDefaultCodecs defaultCodecs = configurer.defaultCodecs();
        defaultCodecs.jackson2JsonDecoder(new Jackson2JsonDecoder(objectMapper, MIME_TYPES));
        defaultCodecs.jackson2JsonEncoder(new Jackson2JsonEncoder(objectMapper, MIME_TYPES));

    }
}
