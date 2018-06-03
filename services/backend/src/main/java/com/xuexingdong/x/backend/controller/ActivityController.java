package com.xuexingdong.x.backend.controller;

import com.xuexingdong.x.backend.dto.ActivityDTO;
import com.xuexingdong.x.backend.exception.ClientException;
import com.xuexingdong.x.backend.service.ActivityService;
import com.xuexingdong.x.backend.vo.ActivityVO;
import com.xuexingdong.x.common.http.XResp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/activities")
public class ActivityController {

    @Autowired
    private ActivityService activityService;

    @GetMapping
    public List<ActivityVO> getActivities() {
        return activityService.getAll();
    }

    @PostMapping
    public XResp apply(ActivityDTO activityDTO) {
        if (activityDTO.getStartDateTime().isBefore(LocalDateTime.now().plusHours(1))) {
            throw new ClientException("准备时间不少于一小时");
        }
        Duration duration = Duration.between(activityDTO.getStartDateTime(), activityDTO.getEndDateTime());
        // 时间少于30分钟
        if (duration.toMillis() < Duration.ofMinutes(30).toMillis()) {
            throw new ClientException("持续时间不少于30分钟");
        }
        activityService.apply(activityDTO);
        return XResp.ok();
    }

    @PatchMapping("/activities/{id}/audit_status/{audit_status}")
    public boolean apply(@PathVariable int id, @PathVariable boolean audit_status) {
        return activityService.audit(id, audit_status);
    }
}
