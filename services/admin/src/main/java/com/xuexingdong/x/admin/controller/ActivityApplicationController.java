package com.xuexingdong.x.admin.controller;

import com.xuexingdong.x.admin.service.ActivityApplicationService;
import com.xuexingdong.x.admin.vo.ActivityApplicationVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

@RestController
@Validated
public class ActivityApplicationController {

    private final ActivityApplicationService activityApplicationService;

    @Autowired
    public ActivityApplicationController(ActivityApplicationService activityApplicationService) {
        this.activityApplicationService = activityApplicationService;
    }

    @GetMapping("activity_applications")
    public List<ActivityApplicationVO> getAll(@RequestParam(defaultValue = "1") Integer page,
                                              @RequestParam(defaultValue = "10") Integer size) {
        return activityApplicationService.getAll(PageRequest.of(page, size));
    }

    @PatchMapping("activity_applications/{id}/audit_status/{audit_status}")
    public boolean audit(@NotEmpty @PathVariable Integer id, @NotNull @PathVariable Boolean audit_status) {
        return activityApplicationService.audit(id, audit_status);
    }
}
