package com.xuexingdong.x.ucenter.filter;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureException;
import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.HandlerFunction;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import javax.validation.constraints.NotEmpty;

@Component
public class TokenFilter {
    private static final Logger logger = LoggerFactory.getLogger(TokenFilter.class);

    @NotEmpty
    @Value("${jwt.secret-key}")
    private String secretKey;

    public Mono<ServerResponse> filter(ServerRequest request, HandlerFunction<ServerResponse> next) {
        String authorization = request.headers().asHttpHeaders().getFirst(HttpHeaders.AUTHORIZATION);
        if (authorization != null && authorization.length() > 7) {
            authorization = authorization.substring(7);
            logger.info("Authorization: {}", authorization);
            byte[] encodedKey = Base64.decodeBase64(secretKey);
            SecretKey key = new SecretKeySpec(encodedKey, 0, encodedKey.length, "AES");
            try {
                Claims claims = Jwts.parser()
                        .setSigningKey(key)
                        .parseClaimsJws(authorization).getBody();
                logger.info("User {} login", claims.getSubject());
                return next.handle(request);
            } catch (SignatureException | ExpiredJwtException e) {
                logger.error("JWT exception: {}", e);
            }
        }
        return ServerResponse.status(HttpStatus.UNAUTHORIZED).build();
    }

}
