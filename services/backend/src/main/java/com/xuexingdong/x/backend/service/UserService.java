package com.xuexingdong.x.backend.service;

import com.xuexingdong.x.backend.dto.BindDTO;
import com.xuexingdong.x.backend.dto.UserDTO;
import com.xuexingdong.x.backend.vo.UserVO;
import com.xuexingdong.x.entity.User;

public interface UserService {

    User register(UserDTO userDTO);

    UserVO getById(String id);

    UserVO getByOpenid(String openid);

    void bind(BindDTO bindDTO);

    void reBind(BindDTO bindDTO);

    void unBind(String openid);
}
