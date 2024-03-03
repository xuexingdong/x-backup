package com.xxd.x.gateway;

import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(value = "x-user", path = "/")
public interface UserClient {
    User parseToken(String authHeader);
}
