package com.xxd.x.todolist.domain.factory;

import com.xxd.x.todolist.domain.aggregate.Reminder;
import com.xxd.x.todolist.domain.command.CreateReminderCommand;
import org.springframework.stereotype.Component;

@Component
public class ReminderFactory {


    private final IdGenerator idGenerator;

    public ReminderFactory(IdGenerator idGenerator) {
        this.idGenerator = idGenerator;
    }

    public Reminder create(CreateReminderCommand createReminderCommand) {
        Reminder reminder = new Reminder();
        reminder.setId(idGenerator.generate(Reminder.class));
        reminder.setName(createReminderCommand.name());
        reminder.setDesc(createReminderCommand.desc());
        reminder.setRemindTime(createReminderCommand.remindTime());
        reminder.setUserId(createReminderCommand.userId());
        return reminder;
    }
}
