package com.xuexingdong.x.gateway.filter;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.security.SignatureException;
import java.time.Duration;

@Component
public class LoginFilter implements GatewayFilter {

    private static final String JWT_PREFIX = "Bearer ";

    private static final Logger logger = LoggerFactory.getLogger(LoginFilter.class);

    @Value("${jwt.secret-key}")
    private String secretKey;

    @Value("${jwt.expiration}")
    Duration expiration;

    @Autowired
    private ReactiveRedisTemplate<String, String> redisTemplate;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        // String authorization = exchange.getRequest().getHeaders().getFirst(HttpHeaders.AUTHORIZATION);
        // if (authorization == null || authorization.startsWith(JWT_PREFIX)) {
        //     // TODO return 401
        //     exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
        //     return exchange.getResponse().setComplete();
        // }
        //
        // authorization = authorization.substring(JWT_PREFIX.length());
        // logger.info("JWT token: {}", authorization);
        // byte[] encodedKey = Base64.decodeBase64(secretKey);
        // SecretKey key = new SecretKeySpec(encodedKey, 0, encodedKey.length, "AES");
        // try {
        //     Claims claims = Jwts.parser()
        //             .setSigningKey(key)
        //             .parseClaimsJws(authorization).getBody();
        //     String userId = claims.getSubject();
        //     Mono<Boolean> success = redisTemplate.hasKey(userId).e(userId, user, expiration);
        //     logger.info("User {} login", userId);
        // } catch (SignatureException | ExpiredJwtException e) {
        //     logger.error("JWT exception: {}", e);
        // }
        logger.info("login filter");
        return chain.filter(exchange);
    }
}
