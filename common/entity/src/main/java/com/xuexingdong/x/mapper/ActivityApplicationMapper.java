package com.xxd.x.mapper;

import com.xxd.x.entity.ActivityApplication;
import com.xxd.x.enumeration.AuditStatus;
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
