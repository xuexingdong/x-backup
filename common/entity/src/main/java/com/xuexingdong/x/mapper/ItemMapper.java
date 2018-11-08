package com.xuexingdong.x.mapper;

import com.xuexingdong.x.entity.Activity;
import com.xuexingdong.x.entity.Item;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ItemMapper {
    List<Item> findAll();
}
