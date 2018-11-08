package com.xuexingdong.x.backend.controller;

import com.xuexingdong.x.backend.service.ActivityService;
import com.xuexingdong.x.backend.vo.ActivityVO;
import com.xuexingdong.x.jwt.JWTService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Validated
public class ActivityController {

    private final ActivityService activityService;
    private final JWTService jwtService;

    @Autowired
    public ActivityController(ActivityService activityService, JWTService jwtService) {
        this.activityService = activityService;
        this.jwtService = jwtService;
    }

    @GetMapping("activities/all")
    public List<ActivityVO> getAll(@RequestParam(defaultValue = "1") Integer page,
                                   @RequestParam(name = "per_page", defaultValue = "10") Integer perPage) {
        activityService.getAll(PageRequest.of(page, perPage));
        return null;
    }

    @GetMapping("activities/self")
    public List<ActivityVO> getByUserId(@RequestParam(required = false) String q,
                                        @RequestParam(defaultValue = "1") Integer page,
                                        @RequestParam(name = "per_page", defaultValue = "10") Integer perPage,
                                        @RequestParam(required = false) Sort sort) {
        String userId = jwtService.getCurrentUserId();
        activityService.getAllByUserId(userId, PageRequest.of(page, perPage, sort));
        return null;
    }
}
