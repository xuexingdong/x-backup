package com.xuexingdong.x.backend.controller;

import com.xuexingdong.x.backend.dto.RegisterDTO;
import com.xuexingdong.x.backend.service.UserService;
import com.xuexingdong.x.backend.vo.UserVO;
import com.xuexingdong.x.common.http.XResp;
import com.xuexingdong.x.jwt.JWTService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("users")
public class UserController {

    private final UserService userService;

    private final JWTService jwtService;

    @Autowired
    public UserController(UserService userService, JWTService jwtService) {
        this.userService = userService;
        this.jwtService = jwtService;
    }

    @PostMapping("register")
    @ResponseStatus(HttpStatus.CREATED)
    public XResp register(@Validated @RequestBody RegisterDTO registerDTO) {
        boolean success = userService.register(registerDTO);
        if (success) {
            return XResp.ok();
        }
        return XResp.internalServerError();
    }

    @PostMapping("profile")
    public UserVO profile() {
        String userId = jwtService.getCurrentUserId();
        return userService.getById(userId);
    }
}
