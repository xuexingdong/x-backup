package com.xxd.x.admin.controller;

import com.xxd.x.admin.service.ChatRecordService;
import com.xxd.x.admin.vo.ChatRecordVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("chat_records")
public class ChatRecordController {

    @Autowired
    private ChatRecordService chatRecordService;

    @GetMapping
    public List<ChatRecordVO> getChatRecordByUserId() {
        return null;
    }
}
