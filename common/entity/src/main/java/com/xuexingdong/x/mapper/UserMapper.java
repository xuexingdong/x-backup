package com.xuexingdong.x.mapper;

import com.xuexingdong.x.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.Optional;

@Mapper
public interface UserMapper {

    @Select("SELECT * FROM user WHERE username=#{username} AND password=#{password}")
    User findOneByUsernameAndPassword(@Param("username") String username, @Param("password") String password);

    @Select("SELECT * FROM user WHERE username=#{username}")
    User findOneByUsername(@Param("username") String username);

    @Select("SELECT * FROM user WHERE id=#{id}")
    User findById(String id);

    User findByOpenid(String openid);

    boolean insert(User user);

    boolean updatePointsByOpenid(String openid, int points);
}
