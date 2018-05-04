package com.xuexingdong.x.gateway.filter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

// the name should end with Filter, and the config is named Logger
@Component
public class LoggerFilter implements GatewayFilter {

    private static final Logger logger = LoggerFactory.getLogger(LoggerFilter.class);

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        logger.info("uri: ", exchange.getRequest().getURI());
        logger.info("ip: ", exchange.getRequest().getRemoteAddress());
        return chain.filter(exchange);
    }

}
