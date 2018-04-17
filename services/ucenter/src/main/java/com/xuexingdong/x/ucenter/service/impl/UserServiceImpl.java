package com.xuexingdong.x.ucenter.service.impl;

import com.xuexingdong.x.common.utils.XRandomUtils;
import com.xuexingdong.x.entity.User;
import com.xuexingdong.x.ucenter.dto.UserDTO;
import com.xuexingdong.x.ucenter.repository.UserRepository;
import com.xuexingdong.x.ucenter.service.UserService;
import com.xuexingdong.x.ucenter.vo.UserVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public void register(UserDTO userDTO) {
        User user = new User()
                .setId(XRandomUtils.randomUUID())
                .setUsername(userDTO.getUsername())
                .setPassword(userDTO.getPassword());
        userRepository.save(user);
    }

    @Override
    public UserVO getById(String id) {
        Optional<User> user = userRepository.findById(id);
        return user.map(UserVO::fromModel).orElse(null);
    }
}
