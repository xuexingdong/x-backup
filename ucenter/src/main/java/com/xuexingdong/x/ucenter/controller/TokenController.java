package com.xuexingdong.x.ucenter.controller;

import com.xuexingdong.x.common.http.XMonoResp;
import com.xuexingdong.x.ucenter.dto.UserDTO;
import com.xuexingdong.x.ucenter.model.Token;
import com.xuexingdong.x.ucenter.service.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
public class TokenController {

    @Autowired
    private TokenService tokenService;

    @PostMapping("/tokens")
    public XMonoResp<Token> generateToken(@Valid @RequestBody UserDTO userDTO) {
        Token token = tokenService.generateToken(userDTO);
        return XMonoResp.data(token);
    }
}
