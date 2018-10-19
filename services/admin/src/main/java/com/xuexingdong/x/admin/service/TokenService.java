package com.xuexingdong.x.admin.service;


import com.xuexingdong.x.admin.dto.UserDTO;
import com.xuexingdong.x.admin.model.Token;

public interface TokenService {
    Token generate(UserDTO userDTO);

    boolean clear();
}
