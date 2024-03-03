package com.xxd.x.admin.interceptor;

import com.xxd.x.admin.constant.Role;
import com.xxd.x.jwt.JWTService;
import io.jsonwebtoken.Claims;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        // ignore option request
        if (HttpMethod.OPTIONS.name().equals(request.getMethod())) {
            return true;
        }
        String authorization = request.getHeader(HttpHeaders.AUTHORIZATION);
        Optional<Claims> claimsOptional = jwtService.parseJWTToken(authorization);
        if (!claimsOptional.isPresent()) {
            return false;
        }
        String userId = claimsOptional.get().getSubject();
        if (jwtService.isJWTTokenExpired(userId)) {
            logger.error("User login status is expired");
        }
        String role = (String) claimsOptional.get().get("role");
        if (!Role.ADMIN.value().equals(role)) {
            logger.error("User {} is not admin", userId);
            response.setStatus(HttpStatus.FORBIDDEN.value());
            return false;
        }
        request.setAttribute("userId", userId);
        return true;
    }
}
