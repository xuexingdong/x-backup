package com.xuexingdong.x.backend.service;

import com.xuexingdong.x.backend.dto.ActivityDTO;
import com.xuexingdong.x.backend.vo.ActivityVO;

import java.util.List;

public interface ActivityService {
    ActivityVO apply(ActivityDTO activityDTO);

    List<ActivityVO> getAll();

    List<ActivityVO> getAllByUserId(String userId);

    boolean audit(String id, boolean audit_status);
}
