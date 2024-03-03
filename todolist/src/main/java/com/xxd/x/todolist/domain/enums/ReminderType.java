package com.xxd.x.todolist.domain.enums;

import lombok.Getter;

@Getter
public enum ReminderType {
    CERTAIN_TIME(1),
    BEFORE_TIME(2),
    ;

    private final int type;

    ReminderType(int type) {
        this.type = type;
    }

}
