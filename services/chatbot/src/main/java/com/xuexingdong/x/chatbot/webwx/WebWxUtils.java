package com.xuexingdong.x.chatbot.webwx;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.Pair;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class WebWxUtils {
    private static final Pattern IN_CHAT_AT_PATTERN = Pattern.compile("@(.+?)\\ufe0f");
    private static final String OUTOFCHAT_AT_UNICODE = "\u2005";
    private static final Pattern OUTOFCHAT_AT_PATTERN = Pattern.compile("@(.+?)(\\ufe0f)?\\u2005");

    private static final Pattern FROM_CHATROOM_TEXT_MESSAGE_PATTERN = Pattern.compile("@(\\w+):<br/>(.*)");

    public static boolean isPerson(String fromUsername) {
        if (StringUtils.isEmpty(fromUsername)) {
            return false;
        }
        return !fromUsername.startsWith("@@") && fromUsername.startsWith("@");
    }

    public static boolean isChatroom(String fromUsername) {
        if (StringUtils.isEmpty(fromUsername)) {
            return false;
        }
        return fromUsername.startsWith("@@");
    }

    public static boolean isPrivateChat(WebWxMessage webWxMessage) {
        return isPerson(webWxMessage.getFromUsername()) && isPerson(webWxMessage.getToUsername());
    }

    public static boolean isFromChatroom(WebWxMessage webWxMessage) {
        return isChatroom(webWxMessage.getFromUsername());
    }

    public static List<String> parseAtedUsernames(@Nonnull String text) {
        List<String> result = new ArrayList<>();
        Matcher m;
        // somebody at you when you are not in the chatroom interface
        if (text.contains(OUTOFCHAT_AT_UNICODE)) {
            m = OUTOFCHAT_AT_PATTERN.matcher(text);
        } else {
            m = IN_CHAT_AT_PATTERN.matcher(text);
        }
        while (m.find()) {
            result.add(m.group(1));
        }
        return result;
    }

    /**
     * parse the chat
     *
     * @param text
     * @return pair, which left is from username, right is the message
     */
    public static Optional<Pair<String, String>> parseFromChatroomTextMessage(@Nonnull String text) {
        Matcher m = FROM_CHATROOM_TEXT_MESSAGE_PATTERN.matcher(text);
        if (m.matches()) {
            return Optional.of(Pair.of(m.group(1), m.group(2)));
        }
        return Optional.empty();
    }

    public static void main(String[] args) {
        System.out.println(Integer.parseInt("🤖"));
        String a = "@a09db96a3ec41c6436af8dc40a094a3bc7f173bcd9d69938d84acc45a99a14e8:<br/>早上好@️🤖";
        Optional<Pair<String, String>> pairOptional = WebWxUtils.parseFromChatroomTextMessage(a);
        if (pairOptional.isPresent()) {
            Pair<String, String> pair = pairOptional.get();
            boolean atme = WebWxUtils.parseAtedUsernames(pair.getRight()).contains("🤖");
            if (atme) {
                System.out.println(1);
            }
        }
    }
}
