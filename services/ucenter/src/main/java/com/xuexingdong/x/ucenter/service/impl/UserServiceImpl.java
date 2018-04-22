package com.xuexingdong.x.ucenter.service.impl;

import com.xuexingdong.x.common.utils.XRandomUtils;
import com.xuexingdong.x.entity.User;
import com.xuexingdong.x.ucenter.dto.UserDTO;
import com.xuexingdong.x.ucenter.repository.UserRepository;
import com.xuexingdong.x.ucenter.service.UserService;
import com.xuexingdong.x.ucenter.vo.UserVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.Objects;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public Mono<Boolean> register(Mono<UserDTO> userDTOMono) {
        userDTOMono.map(userDTO -> {
            User user = new User()
                    .setId(XRandomUtils.randomUUID())
                    .setUsername(userDTO.getUsername())
                    .setPassword(userDTO.getPassword());
            return userRepository.save(user);
        });
        return Mono.just(true);
    }

    @Override
    public Mono<UserVO> getById(String id) {
        Optional<User> user = userRepository.findById(id);
        return Mono.just(Objects.requireNonNull(user.map(UserVO::fromModel).orElse(null)));
    }
}
