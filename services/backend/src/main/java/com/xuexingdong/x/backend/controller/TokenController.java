package com.xuexingdong.x.backend.controller;

import com.xuexingdong.x.backend.dto.LoginDTO;
import com.xuexingdong.x.backend.model.Token;
import com.xuexingdong.x.backend.service.TokenService;
import com.xuexingdong.x.common.http.XMonoResp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("tokens")
public class TokenController {

    private final TokenService tokenService;

    @Autowired
    public TokenController(TokenService tokenService) {
        this.tokenService = tokenService;
    }

    @PostMapping("generate")
    public XMonoResp<Token> generate(@Validated @RequestBody LoginDTO loginDTO) {
        return XMonoResp.data(tokenService.generate(loginDTO));
    }

    @DeleteMapping
    public void clear() {
        tokenService.clear();
    }
}
