package com.xuexingdong.x.backend.service;

import com.xuexingdong.x.backend.dto.ActivityDTO;
import com.xuexingdong.x.backend.vo.ActivityVO;

import java.util.List;

public interface ActivityService {
    void apply(ActivityDTO activityDTO);

    List<ActivityVO> getAll();

    List<ActivityVO> getAllByUserId(String userId);

    boolean audit(int id, boolean audit_status);
}
