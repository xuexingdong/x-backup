package com.xxd.x.admin.controller;

import com.xxd.x.admin.service.ChatFriendService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("chat_friends")
@Repository
public class ChatFriendController {

    @Autowired
    private ChatFriendService chatFriendService;

    @GetMapping
    public List<Map<String, String>> getAllChatUser() {
        return chatFriendService.getChatFriends();
    }

    @GetMapping("{username}/head_img")
    public ResponseEntity<byte[]> getHeadImg(@PathVariable String username) {
        byte[] imageBytes = chatFriendService.getHeadImgByUsername(username);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.IMAGE_JPEG);
        return new ResponseEntity<>(imageBytes, headers, HttpStatus.OK);
    }
}
