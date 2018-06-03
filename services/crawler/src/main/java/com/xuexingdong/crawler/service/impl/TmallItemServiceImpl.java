package com.xuexingdong.crawler.service.impl;

import com.xuexingdong.crawler.mapper.TmallItemMapper;
import com.xuexingdong.crawler.model.TmallItem;
import com.xuexingdong.crawler.service.TmallItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;


@Service
public class TmallItemServiceImpl implements TmallItemService {

    @Autowired
    private TmallItemMapper tmallItemMapper;

    @Override
    public List<Map<String, String>> search(String name) {
        List<TmallItem> a = tmallItemMapper.searchByName(name);
        return null;
    }
}
