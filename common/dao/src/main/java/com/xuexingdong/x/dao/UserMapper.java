package com.xuexingdong.x.dao;

import com.xuexingdong.x.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.Optional;

@Mapper
public interface UserMapper {

    @Select("SELECT * FROM user WHERE username=#{username} AND password=#{password}")
    Optional<User> findOneByUsernameAndPassword(String username, String password);
}
