package com.xxd.x.todolist.domain.aggregate;

import com.xxd.x.todolist.domain.enums.TaskStatus;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class Task {
    private Long id;
    private String name;
    private String desc;
    private LocalDateTime startTime;
    private LocalDateTime dueDateTime;
    private TaskStatus taskStatus;
    private LocalDateTime lastModifiedTime;
    private List<Reminder> reminders;


    public void start() {
        this.taskStatus = TaskStatus.IN_PROGRESS;
        updateLastModifiedTime();
    }

    private void updateLastModifiedTime() {
        this.lastModifiedTime = LocalDateTime.now();
    }

    public void cancel() {
        this.taskStatus = TaskStatus.CANCELLED;
        updateLastModifiedTime();
    }

}
