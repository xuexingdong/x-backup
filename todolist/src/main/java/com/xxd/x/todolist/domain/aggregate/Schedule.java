package com.xxd.x.todolist.domain.aggregate;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Schedule {
    private Long taskId;
    private LocalDateTime time;
}
