package com.xuexingdong.x.admin.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.xuexingdong.x.admin.service.ActivityApplicationService;
import com.xuexingdong.x.admin.vo.ActivityApplicationVO;
import com.xuexingdong.x.entity.Activity;
import com.xuexingdong.x.entity.ActivityApplication;
import com.xuexingdong.x.enumeration.AuditStatus;
import com.xuexingdong.x.mapper.ActivityApplicationMapper;
import com.xuexingdong.x.mapper.ActivityMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
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
    public Page<ActivityApplicationVO> getAll(Pageable pageable) {
        PageHelper.startPage(pageable.getPageNumber(), pageable.getPageSize()).setOrderBy("created_at DESC");
        PageHelper.orderBy("created_at DESC");
        List<ActivityApplication> activityApplications = activityApplicationMapper.getAll();
        PageInfo<ActivityApplication> pageInfo = new PageInfo<>(activityApplications);
        List<ActivityApplicationVO> vos = activityApplications.stream().map(activityApplication -> {
            ActivityApplicationVO vo = new ActivityApplicationVO();
            BeanUtils.copyProperties(activityApplication, vo);
            return vo;
        }).collect(Collectors.toList());
        return new PageImpl<>(vos, pageable, pageInfo.getTotal());
    }
}
