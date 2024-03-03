package com.xxd.x.todolist.infrastructure.repository.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xxd.x.todolist.infrastructure.repository.model.ReminderDO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface TaskMapper extends BaseMapper<ReminderDO> {
}
