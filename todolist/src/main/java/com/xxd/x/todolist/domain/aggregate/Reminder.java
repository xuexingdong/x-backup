package com.xxd.x.todolist.domain.aggregate;

import com.xxd.x.ddd.AggregateRoot;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;
import java.util.Objects;

@EqualsAndHashCode(callSuper = true)
@Data
public class Reminder extends AggregateRoot {
    private String remindChannel;
    private String name;
    private String desc;
    private LocalDateTime remindTime;
    private Long userId;

    public boolean needRemind(LocalDateTime time) {
        if (Objects.nonNull(remindTime)) {
            return remindTime.isBefore(time);
        }
        return false;
    }
}
