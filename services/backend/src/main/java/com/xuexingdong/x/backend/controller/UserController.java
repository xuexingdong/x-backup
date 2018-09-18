package com.xuexingdong.x.backend.controller;

import com.xuexingdong.x.backend.dto.RegisterDTO;
import com.xuexingdong.x.backend.model.Token;
import com.xuexingdong.x.backend.service.UserService;
import com.xuexingdong.x.backend.vo.UserVO;
import com.xuexingdong.x.common.http.XResp;
import com.xuexingdong.x.jwt.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("users")
public class UserController {

    private final UserService userService;

    private final JwtService jwtService;

    @Autowired
    public UserController(UserService userService, JwtService jwtService) {
        this.userService = userService;
        this.jwtService = jwtService;
    }

    @GetMapping
    public UserVO getByOpenid(@RequestParam String openid) {
        return userService.getByOpenid(openid);
    }

    @PostMapping
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

    @PostMapping("bindOpenid")
    public XResp bindOpenid(@RequestParam String openid) {
        String userId = jwtService.getCurrentUserId();
        boolean success = userService.bindOpenid(userId, openid);
        if (success) {
            return XResp.ok();
        }
        return XResp.internalServerError();
    }

    @PostMapping("register")
    @ResponseStatus(HttpStatus.CREATED)
    public UserVO register(@Validated @RequestBody RegisterDTO registerDTO) {
        return userService.register(registerDTO);
    }

    @PostMapping("password_login")
    public Token loginByPassword(@Validated @RequestBody PasswordLoginDTO passwordLoginDTO) {
        return userService.passwordLogin(passwordLoginDTO);
    }

    @PostMapping("dynamic_login")
    public Token loginByDynamicCode(@Validated @RequestBody DynamicLoginDTO dynamicLoginDTO) {
        return userService.dynamicLogin(dynamicLoginDTO);
    }

    @PostMapping("logout")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void logout() {
        userService.logout();
    }
}
