package com.xuexingdong.x.wishwall.mapper;

import com.xuexingdong.x.wishwall.model.Wish;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface WishMapper {
    boolean add(Wish wish);
}
