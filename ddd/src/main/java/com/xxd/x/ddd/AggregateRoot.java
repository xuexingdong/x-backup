package com.xxd.x.ddd;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public abstract class AggregateRoot {
    private Long id;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}
