package com.xxd.x.admin.service;

import com.xxd.x.admin.dto.ItemDTO;
import com.xxd.x.entity.Item;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ItemService {

    List<Item> getAll(Pageable pageable);

    void add(ItemDTO itemDTO);

    void modify(ItemDTO itemDTO);

    void delete(int itemId);
}
