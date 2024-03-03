package com.xxd.x.todolist.domain.command;

import lombok.Data;

@Data
public class FinishTaskCommand {
    private Long taskId;
}
