package com.xxd.x.todolist.infrastructure.repository.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

@EqualsAndHashCode(callSuper = true)
@Data
public class ReminderDO extends BaseDO {
    private String name;
    private String desc;
    private LocalDateTime remindTime;
}
