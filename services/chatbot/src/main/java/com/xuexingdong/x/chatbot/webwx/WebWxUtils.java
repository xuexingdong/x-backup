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
    private static final Pattern IN_CHAT_AT_PATTERN = Pattern.compile("@(.+?)\\ufe0f ?");
    private static final String OUTOFCHAT_AT_UNICODE = "\u2005";
    // PC wechat use char '\u2005' to split ated members
    // PC wechat use char ' ' to split ated members
    private static final Pattern OUTOFCHAT_AT_PATTERN = Pattern.compile("@(.+?)(\\ufe0f)?([\\u2005 ])");

    private static final Pattern FROM_CHATROOM_TEXT_MESSAGE_PATTERN = Pattern.compile("(@\\w+):<br/>(.*)");

    private static boolean isPerson(String username) {
        if (StringUtils.isEmpty(username)) {
            return false;
        }
        return !username.startsWith("@@") && username.startsWith("@");
    }

    private static boolean isChatroom(String username) {
        if (StringUtils.isEmpty(username)) {
            return false;
        }
        return username.startsWith("@@");
    }

    public static boolean isPrivateChat(WebWxMessage webWxMessage) {
        return isPerson(webWxMessage.getFromUsername()) && isPerson(webWxMessage.getToUsername());
    }

    public static boolean isFromChatroom(WebWxMessage webWxMessage) {
        String fromUsername = webWxMessage.getFromUsername();
        return isChatroom(fromUsername);
    }

    public static boolean isFromPerson(WebWxMessage webWxMessage) {
        String fromUsername = webWxMessage.getFromUsername();
        return isPerson(fromUsername);
    }

    public static Pair<List<String>, String> parseAtedUsernamesAndRealContent(@Nonnull String text) {
        // TODO 电脑版微信在@人和正文之前有个空格
        List<String> result = new ArrayList<>();
        Matcher m;
        // somebody at you when you are not in the chatroom interface
        if (text.contains(OUTOFCHAT_AT_UNICODE)) {
            m = OUTOFCHAT_AT_PATTERN.matcher(text);
        } else {
            m = IN_CHAT_AT_PATTERN.matcher(text);
        }
        int lastIndex = 0;
        while (m.find()) {
            result.add(m.group(1));
            lastIndex = m.end();
        }
        return Pair.of(result, text.substring(lastIndex));
    }

    /**
     * parse the chat
     *
     * @param text text to parse
     * @return pair, which left is from username, right is the message
     */
    public static Optional<Pair<String, String>> parseFromChatroomContent(@Nonnull String text) {
        Matcher m = FROM_CHATROOM_TEXT_MESSAGE_PATTERN.matcher(text);
        if (m.matches()) {
            return Optional.of(Pair.of(m.group(1), m.group(2)));
        }
        return Optional.empty();
    }

    public static void main(String[] args) {
        String a = "@85bc8d0b0c84e4f5adfe73e18f4066c30a0c6b3a40ca42345115f1f739c6ce6a:<br/>@🤖️  统计";
        System.out.println(a.toCharArray());
        Optional<Pair<String, String>> pairOptional = WebWxUtils.parseFromChatroomContent(a);
        if (pairOptional.isPresent()) {
            Pair<String, String> pair = pairOptional.get();
            System.out.println(pair.getLeft());
            System.out.println(parseAtedUsernamesAndRealContent(pair.getRight()));
        }
    }
}
