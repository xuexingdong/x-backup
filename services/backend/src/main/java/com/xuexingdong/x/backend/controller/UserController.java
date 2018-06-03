package com.xuexingdong.x.backend.controller;

import com.xuexingdong.x.backend.dto.UserDTO;
import com.xuexingdong.x.backend.service.UserService;
import com.xuexingdong.x.backend.vo.UserVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("users")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping
    public UserVO getByOpenid(@RequestParam String openid) {
        return userService.getByOpenid(openid);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void register(@Validated @RequestBody UserDTO userDTO) {
        userService.register(userDTO);
    }
}
