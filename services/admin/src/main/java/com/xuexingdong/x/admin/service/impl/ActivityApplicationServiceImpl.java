package com.xuexingdong.x.admin.service.impl;

import com.github.pagehelper.PageHelper;
import com.xuexingdong.x.admin.service.ActivityApplicationService;
import com.xuexingdong.x.admin.vo.ActivityApplicationVO;
import com.xuexingdong.x.entity.Activity;
import com.xuexingdong.x.entity.ActivityApplication;
import com.xuexingdong.x.enumeration.AuditStatus;
import com.xuexingdong.x.jwt.JwtService;
import com.xuexingdong.x.mapper.ActivityApplicationMapper;
import com.xuexingdong.x.mapper.ActivityMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ActivityApplicationServiceImpl implements ActivityApplicationService {

    @Autowired
    private ActivityApplicationMapper activityApplicationMapper;

    @Autowired
    private ActivityMapper activityMapper;

    @Transactional
    @Override
    public boolean audit(Integer id, boolean audit_status) {
        ActivityApplication activityApplication = activityApplicationMapper.getById(id);
        // 只能审核一个处于待审核状态的申请
        if (AuditStatus.WAITING != activityApplication.getAuditStatus()) {
            return false;
        }
        AuditStatus status = audit_status ? AuditStatus.ACCEPTED : AuditStatus.REJECTED;
        activityApplicationMapper.audit(id, status);
        Activity activity = new Activity();
        activity.setCreatedBy(activityApplication.getCreatedBy());
        return activityMapper.insert(activity);
    }

    @Override
    public List<ActivityApplicationVO> getAll(Pageable pageable) {
        PageHelper.startPage(pageable.getPageNumber(), pageable.getPageSize());
        PageHelper.orderBy("created_at DESC");
        List<ActivityApplication> activityApplications = activityApplicationMapper.getAll();
        return activityApplications.stream().map(activityApplication -> {
            ActivityApplicationVO vo = new ActivityApplicationVO();
            BeanUtils.copyProperties(activityApplication, vo);
            return vo;
        }).collect(Collectors.toList());
    }
}
