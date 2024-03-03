package com.xxd.x.todolist.domain.enums;

public enum TaskStatus {
    INITIAL("INITIAL"),       // 未开始或初始化状态
    COMPLETED("COMPLETED"),   // 已完成
    INCOMPLETE("INCOMPLETE"), // 未完成
    IN_PROGRESS("IN_PROGRESS"), // 进行中
    PAUSED("PAUSED"),         // 暂停
    CANCELLED("CANCELLED"),   // 取消
    POSTPONED("POSTPONED");   // 延期

    private final String status;

    TaskStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return status;
    }
}
