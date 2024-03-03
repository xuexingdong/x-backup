package com.xxd.x.todolist.domain.command;

import java.time.LocalDateTime;

public record CreateTaskCommand(
        String taskName,
        String taskDesc,
        LocalDateTime taskDueDateTime) {
}
