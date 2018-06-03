package com.xuexingdong.crawler.mapper;

import com.xuexingdong.crawler.model.TmallItem;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface TmallItemMapper {

    List<TmallItem> searchByName(String itemName);
}
