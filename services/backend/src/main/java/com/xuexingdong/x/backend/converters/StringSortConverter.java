package com.xuexingdong.x.backend.converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.data.domain.Sort;

import java.util.ArrayList;

public class StringSortConverter implements Converter<String, Sort> {
    @Override
    public Sort convert(String s) {
        var orders = new ArrayList<Sort.Order>();
        String[] sortGroups = s.split(",");
        for (String sortGroup : sortGroups) {
            String field = sortGroup.substring(1);
            if (sortGroup.startsWith("+")) {
                orders.add(Sort.Order.asc(field));
            } else {
                orders.add(Sort.Order.desc(field));
            }
        }
        return Sort.by(orders);
    }
}
