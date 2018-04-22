package com.xuexingdong.x.ucenter.service;

import com.xuexingdong.x.ucenter.dto.UserDTO;
import com.xuexingdong.x.ucenter.vo.UserVO;
import reactor.core.publisher.Mono;

public interface UserService {

    Mono<Boolean> register(Mono<UserDTO> userDTOMono);

    Mono<UserVO> getById(String id);
}
