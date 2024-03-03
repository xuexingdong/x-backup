package com.xxd.x.todolist.domain.valueobject;

import java.time.LocalDateTime;

public record TaskInfo(
        String taskId,
        String taskName,
        String taskDesc,
        LocalDateTime taskDueDateTime
) {
}
