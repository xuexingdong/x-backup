package com.xuexingdong.x.backend.controller;

import com.xuexingdong.x.backend.dto.UserDTO;
import com.xuexingdong.x.backend.model.Token;
import com.xuexingdong.x.backend.service.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("tokens")
public class TokenController {

    @Autowired
    private TokenService tokenService;

    @PostMapping("generate")
    public Token generate(@Validated @RequestBody UserDTO userDTO) {
        return tokenService.generate(userDTO);
    }

    @DeleteMapping
    public void clear() {
        tokenService.clear();
    }
}
