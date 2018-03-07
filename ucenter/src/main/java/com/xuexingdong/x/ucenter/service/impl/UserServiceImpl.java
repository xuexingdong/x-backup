package com.xuexingdong.x.ucenter.service.impl;

import com.xuexingdong.x.ucenter.mapper.UserMapper;
import com.xuexingdong.x.ucenter.model.User;
import com.xuexingdong.x.ucenter.service.UserService;
import com.xuexingdong.x.ucenter.vo.UserVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public UserVO getByUsername(String username) {
        User user = userMapper.getByUsername(username);
        return null;
    }
}
