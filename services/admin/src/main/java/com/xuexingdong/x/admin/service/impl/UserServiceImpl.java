package com.xuexingdong.x.admin.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.xuexingdong.x.admin.service.UserService;
import com.xuexingdong.x.entity.User;
import com.xuexingdong.x.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public Page<User> getPageable(Pageable pageable) {
        PageHelper.startPage(pageable.getPageNumber(), pageable.getPageSize());
        Optional<String> orderBy = pageable.getSort().stream().map(s -> s.getProperty() + " " + s.getDirection().name()).reduce((s1, s2) -> s2 + "," + s2);
        orderBy.ifPresent(PageHelper::orderBy);
        List<User> users = userMapper.findPageable();
        PageInfo<User> pageInfo = new PageInfo<>(users);
        return new PageImpl<>(users, pageable, pageInfo.getTotal());
    }
}
