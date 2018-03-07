package com.xuexingdong.x.ucenter.mapper;

import com.xuexingdong.x.ucenter.model.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface UserMapper {

    User login(@Param("username") String username, @Param("password") String password);

    User getByUsername(String username);
}
