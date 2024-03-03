package com.xxd.x.todolist.domain.enums;

import lombok.Getter;

@Getter
public enum RemindStatus {
    INITIAL(0),
    PROCESSING(1),
    FAILED(-1),
    SUCCESS(2),
    ;

    private final int type;

    RemindStatus(int type) {
        this.type = type;
    }

}
