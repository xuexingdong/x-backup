package com.xuexingdong.x.mapper;

import com.xuexingdong.x.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.domain.Pageable;

import java.util.List;

@Mapper
public interface UserMapper {

    User findByUsernameAndPassword(@Param("username") String username, @Param("password") String password);

    User findByUsername(String username);

    User findById(String id);

    User findByOpenid(String openid);

    void insert(User user);

    boolean setOpenidById(@Param("id") String id, @Param("openid") String openid);

    boolean plusPoints(@Param("id") String id, @Param("points") int points);

    boolean minusPoints(@Param("id") String id, @Param("points") int points);

    boolean plusPointsToUser(@Param("userId") String userId, @Param("points") int points);

    boolean minusPointsToUser(@Param("userId") String userId, @Param("points") int points);

    List<User> findAll();

    List<User> findPageable();

    User findByRemarkName(String remarkName);
}
