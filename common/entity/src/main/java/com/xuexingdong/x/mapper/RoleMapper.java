package com.xuexingdong.x.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface RoleMapper {

    @Select("SELECT role FROM rel_user_role WHERE user_id=#{userId}")
    List<String> findAllByUserId(String userId);
}
