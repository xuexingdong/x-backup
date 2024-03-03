package com.xxd.x.jwt;

import com.xxd.x.jwt.constant.RedisKeys;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

import java.security.Key;
import java.util.Base64;
import java.util.Optional;

@Service
public class JWTService {
    private static final Logger logger = LoggerFactory.getLogger(JWTService.class);

    @Autowired
    private JWTConfig jwtConfig;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    public String getCurrentUserId() {
        return ((String) RequestContextHolder.currentRequestAttributes().getAttribute("userId", RequestAttributes.SCOPE_REQUEST));
    }

    public boolean isJWTTokenExpired(String userId) {
        return BooleanUtils.isTrue(stringRedisTemplate.hasKey(RedisKeys.JWT_PREFIX + userId));
    }

    public Optional<Claims> parseJWTToken(String authorization) {
        if (StringUtils.isEmpty(authorization) || !authorization.startsWith("Bearer ")) {
            return Optional.empty();
        }
        String token = authorization.substring(7);
        byte[] encodedKey = Base64.getEncoder().encode(jwtConfig.getSecretKey().getBytes());
        Key key = Keys.hmacShaKeyFor(encodedKey);
        Claims claims = null;
        try {
            claims = Jwts.parser()
                    .setSigningKey(key)
                    .parseClaimsJws(token).getBody();
        } catch (JwtException e) {
            logger.error("JWT exception: {}", e.getMessage());
        }
        return Optional.ofNullable(claims);
    }
}
