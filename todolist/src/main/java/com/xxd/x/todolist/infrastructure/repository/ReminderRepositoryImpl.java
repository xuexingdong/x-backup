package com.xxd.x.todolist.infrastructure.repository;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.xxd.x.todolist.domain.aggregate.Reminder;
import com.xxd.x.todolist.domain.repository.ReminderRepository;
import com.xxd.x.todolist.infrastructure.converter.ReminderConverter;
import com.xxd.x.todolist.infrastructure.repository.mapper.TaskMapper;
import com.xxd.x.todolist.infrastructure.repository.model.ReminderDO;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class ReminderRepositoryImpl implements ReminderRepository {

    private final TaskMapper taskMapper;

    public ReminderRepositoryImpl(TaskMapper taskMapper) {
        this.taskMapper = taskMapper;
    }

    @Override
    public void save(Reminder reminder) {
        ReminderDO reminderDO = ReminderConverter.INSTANCE.domain2do(reminder);
        taskMapper.insert(reminderDO);
    }

    @Override
    public Optional<Reminder> findById(Long reminderId) {
        LambdaQueryWrapper<ReminderDO> queryWrapper = Wrappers.lambdaQuery();
        queryWrapper.eq(ReminderDO::getId, reminderId)
                .eq(ReminderDO::getIsDeleted, true);
        ReminderDO reminderDO = taskMapper.selectOne(queryWrapper);
        Reminder reminder = ReminderConverter.INSTANCE.do2domain(reminderDO);
        return Optional.ofNullable(reminder);
    }

    @Override
    public void delete(Long reminderId, String reason) {
        ReminderDO reminderDO = new ReminderDO();
        reminderDO.setId(reminderId);
        reminderDO.setIsDeleted(true);
        taskMapper.updateById(reminderDO);
    }
}
