package com.xuexingdong.x.mapper;

import com.xuexingdong.x.entity.Activity;
import com.xuexingdong.x.enumeration.AuditStatus;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface ActivityMapper {

    boolean insert(Activity activity);

    List<Activity> findAll();

    List<Activity> findAllByUserId(String userId);

    boolean audit(@Param("id") String id, @Param("audit_status") AuditStatus audit_status);
}
