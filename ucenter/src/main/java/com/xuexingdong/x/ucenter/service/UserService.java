package com.xuexingdong.x.ucenter.service;

import com.xuexingdong.x.ucenter.vo.UserVO;

public interface UserService {

    UserVO getByUsername(String username);
}
