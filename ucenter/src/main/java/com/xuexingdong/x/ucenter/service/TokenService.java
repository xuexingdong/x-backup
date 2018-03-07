package com.xuexingdong.x.ucenter.service;

import com.xuexingdong.x.ucenter.dto.UserDTO;
import com.xuexingdong.x.ucenter.model.Token;

public interface TokenService {
    Token generateToken(UserDTO userDTO);
}
