package com.xuexingdong.x.backend.controller;

import com.xuexingdong.x.backend.dto.ActivityApplicationDTO;
import com.xuexingdong.x.backend.exception.ClientException;
import com.xuexingdong.x.backend.service.ActivityApplicationService;
import com.xuexingdong.x.backend.vo.ActivityApplicationVO;
import com.xuexingdong.x.common.http.XFluxResp;
import com.xuexingdong.x.common.http.XResp;
import com.xuexingdong.x.jwt.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.Duration;
import java.time.LocalDateTime;

@RestController
@Validated
public class ActivityApplicationController {

    private final ActivityApplicationService activityApplicationService;

    private final JwtService jwtService;

    @Autowired
    public ActivityApplicationController(ActivityApplicationService activityApplicationService, JwtService jwtService) {
        this.activityApplicationService = activityApplicationService;
        this.jwtService = jwtService;
    }

    @PostMapping("activity_applications")
    @ResponseStatus(HttpStatus.CREATED)
    public XResp apply(@Validated @RequestBody ActivityApplicationDTO activityApplicationDTO) {
        if (activityApplicationDTO.getStartDateTime().isAfter(activityApplicationDTO.getEndDateTime())) {
            throw new ClientException("开始时间不得早于结束时间");
        }
        if (activityApplicationDTO.getStartDateTime().isBefore(LocalDateTime.now().plusHours(1))) {
            throw new ClientException("开始时间距离现在不少于1小时");
        }
        Duration duration = Duration.between(activityApplicationDTO.getStartDateTime(), activityApplicationDTO.getEndDateTime());
        // 时间少于30分钟
        if (duration.toMillis() < Duration.ofMinutes(30).toMillis()) {
            throw new ClientException("活动时间不得少于30分钟");
        }
        boolean success = activityApplicationService.apply(jwtService.getCurrentUserId(), activityApplicationDTO);
        if (success) {
            return XResp.ok();
        }
        return XResp.internalServerError();
    }

    /**
     * 分页获取自己创建的活动申请
     *
     * @param page
     * @param size
     * @return
     */
    @GetMapping("activity_applications")
    public XFluxResp<ActivityApplicationVO> getAll(@RequestParam(defaultValue = "1") Integer page,
                                                   @RequestParam(defaultValue = "10") Integer size) {
        return null;
    }
}
