package com.xuexingdong.x.backend.service;

import com.xuexingdong.x.backend.dto.UserDTO;
import com.xuexingdong.x.backend.model.Token;

public interface TokenService {
    Token generate(UserDTO userDTO);

    boolean clear();
}
