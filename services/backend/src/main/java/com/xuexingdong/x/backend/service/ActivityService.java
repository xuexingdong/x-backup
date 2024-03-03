package com.xxd.x.backend.service;

import com.xxd.x.backend.vo.ActivityVO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ActivityService {
    Page<ActivityVO> getAll(Pageable pageable);

    Page<ActivityVO> getAllByUserId(String userId, Pageable pageable);
}
