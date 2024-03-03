package com.xxd.x.mapper;

import com.xxd.x.entity.Activity;
import com.xxd.x.entity.Item;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ItemMapper {
    List<Item> findAll();
}
