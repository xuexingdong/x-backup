package com.xuexingdong.x.backend.filter;

import com.xuexingdong.x.backend.config.JwtConfig;
import com.xuexingdong.x.backend.exception.BusinessException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureException;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.HttpHeaders;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Base64;

public class JwtFilter extends OncePerRequestFilter {

    private static final Logger logger = LoggerFactory.getLogger(JwtConfig.class);

    @Autowired
    private JwtConfig jwtConfig;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {
        String authorization = httpServletRequest.getHeader(HttpHeaders.AUTHORIZATION);
        logger.info("Authorization: {}", authorization);
        if (StringUtils.isEmpty(authorization) && authorization.startsWith("Bearer ")) {
            throw new BusinessException("未登录");
        }
        String token = authorization.substring(7);
        byte[] encodedKey = Base64.getEncoder().encode(jwtConfig.getSecretKey().getBytes());
        SecretKey key = new SecretKeySpec(encodedKey, 0, encodedKey.length, "AES");
        Claims claims;
        try {
            claims = Jwts.parser()
                    .setSigningKey(key)
                    .parseClaimsJws(token).getBody();
        } catch (SignatureException | ExpiredJwtException e) {
            logger.error("JWT exception: {}", e);
            throw new BusinessException("未登录");
        }
        String userId = claims.getSubject();
        if (BooleanUtils.isFalse(stringRedisTemplate.hasKey(userId))) {
            throw new BusinessException("未登录");
        }
        httpServletRequest.setAttribute("userId", userId);
    }
}
