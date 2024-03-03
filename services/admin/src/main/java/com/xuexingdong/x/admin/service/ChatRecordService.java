package com.xxd.x.admin.service;

import com.xxd.x.entity.ChatRecord;

import java.util.List;

public interface ChatRecordService {
    List<ChatRecord> getChatRecordsByUserId();
}
