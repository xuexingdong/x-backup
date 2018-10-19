package com.xuexingdong.x.admin.service;

import com.xuexingdong.x.admin.vo.ActivityVO;

import java.util.List;

public interface ActivityService {

    List<ActivityVO> getAll();

    boolean audit(Integer id, boolean audit_status);
}
