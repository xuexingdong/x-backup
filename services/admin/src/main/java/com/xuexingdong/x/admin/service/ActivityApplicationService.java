package com.xuexingdong.x.admin.service;

import com.xuexingdong.x.admin.vo.ActivityApplicationVO;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ActivityApplicationService {
    boolean audit(Integer id, boolean audit_status);

    List<ActivityApplicationVO> getAll(Pageable pageable);
}

