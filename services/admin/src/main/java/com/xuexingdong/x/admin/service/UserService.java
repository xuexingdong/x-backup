package com.xuexingdong.x.admin.service;

import com.xuexingdong.x.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface UserService {
    Page<User> getPageable(Pageable pageable);
}
