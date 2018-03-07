package com.xuexingdong.crawler.service;

import java.util.List;
import java.util.Map;

public interface TmallItemService {
    List<Map<String, String>> search(String name);
}
