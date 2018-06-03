package com.xuexingdong.x.mapper;

import com.xuexingdong.x.entity.Activity;
import com.xuexingdong.x.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface ActivityMapper {

    boolean insert(Activity activity);

    List<Activity> findAll();

    List<Activity> findAllByUserId(String userId);

    int audit(@Param("id") int id, @Param("audit_status") boolean audit_status);
}
