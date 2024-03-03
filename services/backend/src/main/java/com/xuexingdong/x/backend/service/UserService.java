package com.xxd.x.backend.service;

import com.xxd.x.backend.dto.RegisterDTO;
import com.xxd.x.backend.vo.UserVO;

public interface UserService {

    boolean register(RegisterDTO registerDTO);

    UserVO getById(String id);
}
