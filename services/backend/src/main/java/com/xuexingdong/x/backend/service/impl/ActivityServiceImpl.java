package com.xxd.x.backend.service.impl;

import com.github.pagehelper.PageHelper;
import com.xxd.x.backend.service.ActivityService;
import com.xxd.x.backend.vo.ActivityVO;
import com.xxd.x.entity.Activity;
import com.xxd.x.mapper.ActivityMapper;
import com.xxd.x.mapper.RoleMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ActivityServiceImpl implements ActivityService {

    @Autowired
    private ActivityMapper activityMapper;

    @Autowired
    private RoleMapper roleMapper;

    @Override
    public Page<ActivityVO> getAll(Pageable pageable) {
        long total = activityMapper.countAll();
        PageHelper.startPage(pageable.getPageNumber(), pageable.getPageSize());
        PageHelper.orderBy("created_at DESC");
        List<Activity> activities = activityMapper.findAll();
        List<ActivityVO> vos = activities.stream().map(activity -> {
            ActivityVO activityVO = new ActivityVO();
            BeanUtils.copyProperties(activity, activityVO);
            return activityVO;
        }).collect(Collectors.toList());
        return new PageImpl<>(vos, pageable, total);
    }

    @Override
    public Page<ActivityVO> getAllByUserId(String userId, Pageable pageable) {
        long total = activityMapper.countAllByUserId(userId);
        PageHelper.startPage(pageable.getPageNumber(), pageable.getPageSize());
        PageHelper.orderBy("created_at DESC");
        List<Activity> activities = activityMapper.findAllByUserId(userId);
        List<ActivityVO> vos = activities.stream().map(activity -> {
            ActivityVO activityVO = new ActivityVO();
            BeanUtils.copyProperties(activity, activityVO);
            return activityVO;
        }).collect(Collectors.toList());
        return new PageImpl<>(vos, pageable, total);
    }
}
