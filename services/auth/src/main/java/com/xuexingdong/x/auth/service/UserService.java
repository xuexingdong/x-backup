package com.xuexingdong.x.auth.service;

import com.xuexingdong.x.auth.dto.PasswordLoginDTO;
import com.xuexingdong.x.auth.model.Token;

public interface UserService {

    Token passwordLogin(PasswordLoginDTO passwordLoginDTO);

    void logout(String userId);
}
