package com.xxd.x.todolist.domain.command;

public record DeleteTaskCommand(
        Long taskId,
        String reason) {
}
