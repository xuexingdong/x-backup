package com.xuexingdong.crawler.controller;

import com.xuexingdong.crawler.service.TmallItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TmallItemController {

    @Autowired
    private TmallItemService tmallItemService;

    @GetMapping("search/tmall/items")
    public String search(String name) {
        tmallItemService.search(name);
        return "!";
    }
}
