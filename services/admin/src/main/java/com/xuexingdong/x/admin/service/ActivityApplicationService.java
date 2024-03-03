package com.xxd.x.admin.service;

import com.xxd.x.admin.vo.ActivityApplicationVO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ActivityApplicationService {
    boolean audit(Integer id, boolean audit_status);

    Page<ActivityApplicationVO> getAll(Pageable pageable);
}

