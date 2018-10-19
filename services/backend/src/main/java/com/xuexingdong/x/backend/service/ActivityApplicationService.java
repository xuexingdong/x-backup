package com.xuexingdong.x.backend.service;

import com.github.pagehelper.PageInfo;
import com.xuexingdong.x.backend.dto.ActivityApplicationDTO;
import com.xuexingdong.x.backend.vo.ActivityApplicationVO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface ActivityApplicationService {
    boolean apply(String userId, ActivityApplicationDTO activityApplicationDTO);

    PageInfo getAllByUserId(String userId, Pageable pageable);
}
