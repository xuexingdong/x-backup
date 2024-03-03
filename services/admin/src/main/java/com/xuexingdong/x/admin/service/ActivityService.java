package com.xxd.x.admin.service;

import com.xxd.x.admin.vo.ActivityVO;

import java.util.List;

public interface ActivityService {

    List<ActivityVO> getAll();

    boolean audit(Integer id, boolean audit_status);
}
