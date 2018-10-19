package com.xuexingdong.x.backend.service;

import com.xuexingdong.x.backend.dto.LoginDTO;
import com.xuexingdong.x.backend.model.Token;

public interface TokenService {
    Token generate(LoginDTO loginDTO);

    boolean clear();
}
