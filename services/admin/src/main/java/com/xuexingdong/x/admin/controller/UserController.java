package com.xuexingdong.x.admin.controller;

import com.xuexingdong.x.admin.service.UserService;
import com.xuexingdong.x.admin.vo.UserVO;
import com.xuexingdong.x.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Validated
@RequestMapping("users")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping
    public List<UserVO> get(@RequestParam(defaultValue = "0") int page, @RequestParam(value = "per_page", defaultValue = "10") int perPage) {
        Page<User> userPage = userService.getPageable(PageRequest.of(page, perPage, Sort.by(Sort.Order.desc("created_at"))));
        return null;
    }
}
