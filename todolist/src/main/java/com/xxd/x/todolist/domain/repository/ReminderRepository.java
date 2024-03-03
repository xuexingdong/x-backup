package com.xxd.x.todolist.domain.repository;

import com.xxd.x.todolist.domain.aggregate.Reminder;

import java.util.Optional;

public interface ReminderRepository {
    void save(Reminder reminder);

    Optional<Reminder> findById(Long reminderId);

    void delete(Long reminderId, String reason);
}
