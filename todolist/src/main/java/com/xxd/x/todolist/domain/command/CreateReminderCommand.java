package com.xxd.x.todolist.domain.command;

import java.time.LocalDateTime;

public record CreateReminderCommand(
        String name,
        String desc,
        LocalDateTime remindTime,
        Long userId) {
}
