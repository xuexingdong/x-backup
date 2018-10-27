package com.xuexingdong.x.admin.service.impl;

import com.xuexingdong.x.admin.service.ActivityService;
import com.xuexingdong.x.admin.vo.ActivityVO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ActivityServiceImpl implements ActivityService {
    @Override
    public List<ActivityVO> getAll() {
        return null;
    }

    @Override
    public boolean audit(Integer id, boolean audit_status) {
        return false;
    }
}
