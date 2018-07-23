package com.xuexingdong.x.backend.service;

import com.xuexingdong.x.backend.dto.RegisterDTO;
import com.xuexingdong.x.backend.vo.UserVO;

public interface UserService {

    boolean register(RegisterDTO registerDTO);

    UserVO getById(String id);

    UserVO getByOpenid(String openid);

    boolean bindOpenid(String userId, String openid);
}
