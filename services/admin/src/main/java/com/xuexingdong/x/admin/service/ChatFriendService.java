package com.xuexingdong.x.admin.service;

import java.util.List;
import java.util.Map;

public interface ChatFriendService {
    List<Map<String, String>> getChatFriends();

    byte[] getHeadImgByUsername(String username);
}
