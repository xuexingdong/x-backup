package com.xxd.x.todolist.domain.aggregate;

import com.xxd.x.ddd.AggregateRoot;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

@EqualsAndHashCode(callSuper = true)
@Data
public class Event extends AggregateRoot {
    private String name;
    private String desc;
    private LocalDateTime happenedTime;
    private Long userId;
}
