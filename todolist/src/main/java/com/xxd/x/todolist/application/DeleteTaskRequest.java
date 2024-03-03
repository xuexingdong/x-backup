package com.xxd.x.todolist.application;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class DeleteTaskRequest {
    private Long taskId;
    private String reason;
}
