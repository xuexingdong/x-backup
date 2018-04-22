package com.xuexingdong.x.ucenter.service;

import com.xuexingdong.x.ucenter.dto.UserDTO;
import com.xuexingdong.x.ucenter.model.Token;
import reactor.core.publisher.Mono;

public interface TokenService {
    Mono<Token> generateToken(final Mono<UserDTO> userDTOMono);
}
