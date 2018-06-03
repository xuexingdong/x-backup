package com.xuexingdong.x.backend.service.impl;

import com.xuexingdong.x.backend.dto.ActivityDTO;
import com.xuexingdong.x.backend.service.ActivityService;
import com.xuexingdong.x.backend.service.JwtService;
import com.xuexingdong.x.backend.vo.ActivityVO;
import com.xuexingdong.x.entity.Activity;
import com.xuexingdong.x.enumeration.ActivityStatus;
import com.xuexingdong.x.enumeration.Role;
import com.xuexingdong.x.mapper.ActivityMapper;
import com.xuexingdong.x.mapper.RoleMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpServerErrorException;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ActivityServiceImpl implements ActivityService {

    @Autowired
    private JwtService jwtService;

    @Autowired
    private ActivityMapper activityMapper;

    @Autowired
    private RoleMapper roleMapper;

    @Override
    public void apply(ActivityDTO activityDTO) {
        String userId = jwtService.getCurrentUserId();
        Activity activity = new Activity();
        BeanUtils.copyProperties(activityDTO, activity);
        activity.setStatus(ActivityStatus.WAITING);
        activity.setCreatedBy(userId);
        activityMapper.insert(activity);
    }

    @Override
    public List<ActivityVO> getAll() {
        List<Activity> activities = activityMapper.findAll();
        List<ActivityVO> activityVOs = activities.stream().map(activity -> {
            ActivityVO activityVO = new ActivityVO();
            BeanUtils.copyProperties(activityVO, activity);
            return activityVO;
        }).collect(Collectors.toList());
        return activityVOs;
    }

    public List<ActivityVO> getAll(int currentPage, int pageSize) {
        return null;
    }

    @Override
    public List<ActivityVO> getAllByUserId(String userId) {
        return null;
    }

    @Override
    public boolean audit(int id, boolean audit_status) {
        String userId = jwtService.getCurrentUserId();
        List<String> roles = roleMapper.findAllByUserId(userId);
        // 非管理员
        if (roles.stream().noneMatch(role -> role.equals(Role.ADMIN.name()))) {
            throw new HttpServerErrorException(HttpStatus.FORBIDDEN, "非管理员");
        }
        return activityMapper.audit(id, audit_status) == 1;
    }

}
