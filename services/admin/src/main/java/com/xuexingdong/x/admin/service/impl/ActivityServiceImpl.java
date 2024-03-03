package com.xxd.x.admin.service.impl;

import com.xxd.x.admin.service.ActivityService;
import com.xxd.x.admin.vo.ActivityVO;
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
