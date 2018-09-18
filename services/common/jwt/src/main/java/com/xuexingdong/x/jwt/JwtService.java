package com.xuexingdong.x.jwt;

import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

@Service
public class JwtService {
    public String getCurrentUserId() {
        return ((String) RequestContextHolder.currentRequestAttributes().getAttribute("userId", RequestAttributes.SCOPE_REQUEST));
    }
}
