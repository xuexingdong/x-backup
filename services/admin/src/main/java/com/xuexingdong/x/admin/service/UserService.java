package com.xxd.x.admin.service;

import com.xxd.x.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface UserService {
    Page<User> getPageable(Pageable pageable);
}
