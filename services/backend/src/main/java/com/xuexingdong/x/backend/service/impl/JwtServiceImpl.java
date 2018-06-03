package com.xuexingdong.x.backend.service.impl;

import com.xuexingdong.x.backend.service.JwtService;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

@Service
public class JwtServiceImpl implements JwtService {
    @Override
    public String getCurrentUserId() {
        return ((String) RequestContextHolder.currentRequestAttributes().getAttribute("userId", RequestAttributes.SCOPE_REQUEST));
    }
}
