package com.xuexingdong.x.admin.controller;

import com.xuexingdong.x.admin.service.UserService;
import com.xuexingdong.x.admin.vo.UserVO;
import com.xuexingdong.x.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@Validated
@RequestMapping("users")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping
    public ResponseEntity<List<UserVO>> get(@RequestParam(defaultValue = "0") int page, @RequestParam(value = "per_page", defaultValue = "10") int perPage) {
        Page<User> userPage = userService.getPageable(PageRequest.of(page, perPage, Sort.by(Sort.Order.desc("created_at"))));
        List<UserVO> userVOs = userPage.getContent().stream().map(user -> {
            UserVO userVO = new UserVO();
            userVO.setId(user.getId());
            userVO.setPoints(user.getPoints());
            userVO.setRemarkName(user.getRemarkName());
            userVO.setCreatedAt(user.getCreatedAt());
            return userVO;
        }).collect(Collectors.toList());
        return ResponseEntity.ok()
                .header("X-Total-Count", String.valueOf(userPage.getTotalElements()))
                .body(userVOs);
    }
}
