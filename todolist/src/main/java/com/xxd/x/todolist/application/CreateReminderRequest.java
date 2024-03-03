package com.xxd.x.todolist.application;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class CreateReminderRequest {
    private String name;
    private String desc;
    private LocalDateTime remindTime;
}
