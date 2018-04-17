package com.xuexingdong.x.ucenter.service;

import com.xuexingdong.x.ucenter.dto.UserDTO;
import com.xuexingdong.x.ucenter.vo.UserVO;

public interface UserService {

    void register(UserDTO userDTO);

    UserVO getById(String id);
}
