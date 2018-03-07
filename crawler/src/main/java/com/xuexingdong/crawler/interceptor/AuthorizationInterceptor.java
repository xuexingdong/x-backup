package com.xuexingdong.crawler.interceptor;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureException;
import org.apache.commons.lang3.StringUtils;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.NotEmpty;
import java.util.Objects;

@Component
public class AuthorizationInterceptor implements HandlerInterceptor {

    @NotEmpty
    @Value("${token.secret-key}")
    private String secretKey;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (Objects.isNull(request.getHeader("Authorization"))) {
            return false;
        }
        String authorization = request.getHeader("Authorization").substring(7);
        if (StringUtils.isEmpty(authorization)) {
            return false;
        }
        byte[] encodedKey = Base64.decodeBase64(secretKey);
        SecretKey key = new SecretKeySpec(encodedKey, 0, encodedKey.length, "AES");
        try {
            Jwts.parser()
                    .setSigningKey(key)
                    .parseClaimsJws(authorization).getBody();
        } catch (SignatureException | ExpiredJwtException e) {
            return false;
        }
        return true;
    }
}
