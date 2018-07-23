package com.xuexingdong.x.mapper;

import com.xuexingdong.x.entity.ActivityApplication;
import com.xuexingdong.x.enumeration.AuditStatus;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface ActivityApplicationMapper {

    List<ActivityApplication> getAll();

    ActivityApplication getById(Integer id);

    List<ActivityApplication> findAllByUserId(String userId);

    boolean insert(ActivityApplication application);

    boolean audit(@Param("id") Integer id, @Param("audit_status") AuditStatus audit_status);

    long countByUserId(String userId);
}
