package com.xuexingdong.x.admin.service;

import com.xuexingdong.x.entity.ChatRecord;

import java.util.List;

public interface ChatRecordService {
    List<ChatRecord> getChatRecordsByUserId();
}
