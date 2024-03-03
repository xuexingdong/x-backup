package com.xxd.x.todolist.infrastructure.converter;

import com.xxd.x.todolist.domain.aggregate.Reminder;
import com.xxd.x.todolist.domain.aggregate.Task;
import com.xxd.x.todolist.infrastructure.repository.model.ReminderDO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ReminderConverter {
    ReminderConverter INSTANCE = Mappers.getMapper(ReminderConverter.class);

    ReminderDO domain2do(Reminder reminder);

    Reminder do2domain(ReminderDO reminderDO);
}
