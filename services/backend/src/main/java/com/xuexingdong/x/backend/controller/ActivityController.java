package com.xuexingdong.x.backend.controller;

import com.xuexingdong.x.backend.dto.ActivityDTO;
import com.xuexingdong.x.backend.exception.ClientException;
import com.xuexingdong.x.backend.service.ActivityService;
import com.xuexingdong.x.backend.vo.ActivityVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@Validated
public class ActivityController {

    @Autowired
    private ActivityService activityService;

    @GetMapping
    public List<ActivityVO> getActivities() {
        return activityService.getAll();
    }

    @PostMapping("activities")
    @ResponseStatus(HttpStatus.CREATED)
    public ActivityVO apply(@Validated @RequestBody ActivityDTO activityDTO) {
        if (activityDTO.getStartDateTime().isAfter(activityDTO.getEndDateTime())) {
            throw new ClientException("开始时间不得早于结束时间");
        }
        if (activityDTO.getStartDateTime().isBefore(LocalDateTime.now().plusHours(1))) {
            throw new ClientException("开始时间距离现在不少于一小时");
        }
        Duration duration = Duration.between(activityDTO.getStartDateTime(), activityDTO.getEndDateTime());
        // 时间少于30分钟
        if (duration.toMillis() < Duration.ofMinutes(30).toMillis()) {
            throw new ClientException("持续时间不得少于30分钟");
        }
        return activityService.apply(activityDTO);
    }

    @GetMapping("users/{$userId}/activities")
    public List<ActivityVO> getByUserId(@NotEmpty @PathVariable String userId) {
        return activityService.getAllByUserId(userId);
    }

    @PatchMapping("activities/{id}/audit_status/{audit_status}")
    public boolean audit(@NotEmpty @PathVariable String id, @NotNull @PathVariable Boolean audit_status) {
        return activityService.audit(id, audit_status);
    }
}
