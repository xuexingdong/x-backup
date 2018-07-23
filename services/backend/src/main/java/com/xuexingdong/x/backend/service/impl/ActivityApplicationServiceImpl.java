package com.xuexingdong.x.backend.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.xuexingdong.x.backend.dto.ActivityApplicationDTO;
import com.xuexingdong.x.backend.exception.Exceptions;
import com.xuexingdong.x.backend.service.ActivityApplicationService;
import com.xuexingdong.x.backend.vo.ActivityApplicationVO;
import com.xuexingdong.x.entity.ActivityApplication;
import com.xuexingdong.x.entity.User;
import com.xuexingdong.x.enumeration.AuditStatus;
import com.xuexingdong.x.mapper.ActivityApplicationMapper;
import com.xuexingdong.x.mapper.UserMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ActivityApplicationServiceImpl implements ActivityApplicationService {

    private static final Logger logger = LoggerFactory.getLogger(ActivityApplicationServiceImpl.class);

    private static final int a = 100;
    @Autowired
    private ActivityApplicationMapper activityApplicationMapper;

    @Autowired
    private UserMapper userMapper;

    @Override
    @Transactional
    public boolean apply(String userId, ActivityApplicationDTO activityApplicationDTO) {
        User user = userMapper.findById(userId);
        // 积分不足
        if (user.getPoints() < 100) {
            throw Exceptions.POINT_NOT_ENOUGH;
        }
        ActivityApplication activityApplication = new ActivityApplication();
        BeanUtils.copyProperties(activityApplicationDTO, activityApplication);
        activityApplication.setCreatedBy(userId);
        activityApplication.setAuditStatus(AuditStatus.WAITING);
        boolean insertSuccess = activityApplicationMapper.insert(activityApplication);
        if (!insertSuccess) {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return false;
        }
        boolean minusSuccess = userMapper.minusPoints(userId, a);
        if (!minusSuccess) {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return false;
        }
        return true;
    }

    @Override
    public PageInfo<ActivityApplicationVO> getAllByUserId(String userId, Pageable pageable) {
        PageHelper.startPage(pageable.getPageNumber(), pageable.getPageSize());
        PageHelper.orderBy("created_at DESC");
        List<ActivityApplication> activityApplications = activityApplicationMapper.findAllByUserId(userId);
        List<ActivityApplicationVO> vos = activityApplications.stream().map(activityApplication -> {
            ActivityApplicationVO vo = new ActivityApplicationVO();
            BeanUtils.copyProperties(activityApplication, vo);
            return vo;
        }).collect(Collectors.toList());
        return new PageInfo<>(vos);
    }
}
