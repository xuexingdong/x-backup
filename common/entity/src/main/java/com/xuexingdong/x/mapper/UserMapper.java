package com.xuexingdong.x.mapper;

import com.xuexingdong.x.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface UserMapper {

    User findByUsernameAndPassword(@Param("username") String username, @Param("password") String password);

    User findByUsername(String username);

    User findById(String id);

    User findByOpenid(String openid);

    boolean insert(User user);

    boolean setOpenidById(@Param("id") String id, @Param("openid") String openid);

    boolean plusPoints(@Param("id") String id, @Param("points") int points);

    boolean minusPoints(@Param("id") String id, @Param("points") int points);

    boolean plusPointsToUsername(@Param("username") String username, @Param("points") int points);

    boolean minusPointsToUsername(@Param("username") String username, @Param("points") int points);

}
