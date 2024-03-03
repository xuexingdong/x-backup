package com.xxd.x.todolist.infrastructure.repository.model;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public abstract class BaseDO {
    private Long id;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
    private Boolean isDeleted;
}
