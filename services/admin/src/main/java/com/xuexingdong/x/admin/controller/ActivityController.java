package com.xuexingdong.x.admin.controller;

import com.xuexingdong.x.admin.service.ActivityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@RestController
@Validated
public class ActivityController {

    @Autowired
    private ActivityService activityService;

    @PatchMapping("activities/{id}/audit_status/{audit_status}")
    public boolean audit(@NotEmpty @PathVariable Integer id, @NotNull @PathVariable Boolean audit_status) {
        return activityService.audit(id, audit_status);
    }
}
