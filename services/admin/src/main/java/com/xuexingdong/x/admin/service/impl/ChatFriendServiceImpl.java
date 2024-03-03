package com.xxd.x.admin.service.impl;

import com.xxd.x.admin.component.ChatbotComponent;
import com.xxd.x.admin.service.ChatFriendService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;
import java.util.Map;

@Service
public class ChatFriendServiceImpl implements ChatFriendService {

    @Autowired
    private ChatbotComponent chatbotComponent;

    @Override
    public List<Map<String, String>> getChatFriends() {
        return chatbotComponent.getFriends();
    }

    @Override
    public byte[] getHeadImgByUsername(String username) {
        try {
            File file = ResourceUtils.getFile("data/" + username + ".jpg");
            return Files.readAllBytes(file.toPath());
        } catch (IOException e) {
            return new byte[0];
        }
    }
}
