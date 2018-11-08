package com.xuexingdong.x.backend.interceptor;

import com.xuexingdong.x.jwt.JWTService;
import io.jsonwebtoken.Claims;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Optional;

@Component
public class AuthInterceptor implements HandlerInterceptor {

    private static final Logger logger = LoggerFactory.getLogger(AuthInterceptor.class);

    @Autowired
    private JWTService jwtService;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        // ignore option request
        if (HttpMethod.OPTIONS.name().equals(request.getMethod())) {
            return true;
        }
        String authorization = request.getHeader(HttpHeaders.AUTHORIZATION);
        String token = authorization.substring(7);
        logger.info("Authorization: {}", authorization);
        if (StringUtils.isEmpty(authorization) || !authorization.startsWith("Bearer ")) {
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            return false;
        }
        Optional<Claims> claimsOptional = jwtService.parseJWTToken(token);
        if (!claimsOptional.isPresent()) {
            return false;
        }
        String userId = claimsOptional.get().getSubject();
        if (BooleanUtils.isNotTrue(stringRedisTemplate.hasKey("auth:jwt:" + userId))) {
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            return false;
        }
        request.setAttribute("userId", userId);
        return true;
    }
}
