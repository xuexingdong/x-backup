package com.xxd.x.todolist.domain.aggregate;

import com.xxd.x.todolist.domain.enums.RemindStatus;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ReminderRecord {
    private LocalDateTime remindTime;
    private RemindStatus remindStatus;
}
