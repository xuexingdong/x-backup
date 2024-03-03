package com.xxd.x.auth.service;

import com.xxd.x.auth.dto.PasswordLoginDTO;
import com.xxd.x.auth.model.Token;

public interface UserService {

    Token passwordLogin(PasswordLoginDTO passwordLoginDTO);

    void logout(String userId);
}
