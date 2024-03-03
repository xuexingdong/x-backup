package com.xxd.x.mapper;

import com.xxd.x.entity.Activity;
import com.xxd.x.enumeration.AuditStatus;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface ActivityMapper {

    boolean insert(Activity activity);

    long countAll();

    List<Activity> findAll();

    long countAllByUserId(String userId);

    List<Activity> findAllByUserId(String userId);

    boolean audit(@Param("id") Integer id, @Param("audit_status") AuditStatus audit_status);
}
