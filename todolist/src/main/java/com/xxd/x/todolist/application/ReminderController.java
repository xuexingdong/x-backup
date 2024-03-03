package com.xxd.x.todolist.application;

import com.xxd.x.todolist.domain.aggregate.Reminder;
import com.xxd.x.todolist.domain.command.CreateReminderCommand;
import com.xxd.x.todolist.domain.command.DeleteTaskCommand;
import com.xxd.x.todolist.domain.factory.ReminderFactory;
import com.xxd.x.todolist.domain.repository.ReminderRepository;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController("reminder")
public class ReminderController {

    private final ReminderFactory reminderFactory;

    private final ReminderRepository reminderRepository;

    public ReminderController(ReminderFactory reminderFactory, ReminderRepository reminderRepository) {
        this.reminderFactory = reminderFactory;
        this.reminderRepository = reminderRepository;
    }


    @PostMapping("create")
    public Response<CreateReminderVO> createReminder(@RequestBody CreateReminderRequest createReminderRequest) {
        Long userId = null;
        CreateReminderCommand createReminderCommand = new CreateReminderCommand(createReminderRequest.getName(), createReminderRequest.getDesc(), createReminderRequest.getRemindTime(), userId);
        Reminder reminder = reminderFactory.create(createReminderCommand);
        reminderRepository.save(reminder);
        CreateReminderVO vo = new CreateReminderVO();
        vo.setTaskId(reminder.getId());
        return Response.data(vo);
    }

    @PostMapping("delete")
    public Response<Void> deleteTask(@RequestBody DeleteTaskRequest deleteTaskRequest) {
        DeleteTaskCommand deleteTaskCommand = new DeleteTaskCommand(deleteTaskRequest.getTaskId(), deleteTaskRequest.getReason());
        reminderRepository.delete(deleteTaskCommand.taskId(), deleteTaskRequest.getReason());
        return Response.ok();
    }
}
