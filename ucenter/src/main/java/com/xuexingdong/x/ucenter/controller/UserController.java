package com.xuexingdong.x.ucenter.controller;

import com.xuexingdong.x.ucenter.service.UserService;
import com.xuexingdong.x.ucenter.vo.UserVO;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class UserController {

    private UserService userService;

    @GetMapping("/{username}")
    public UserVO a(@PathVariable String username){
        UserVO a = userService.getByUsername(username);
        return new UserVO();
    }
}
