package com.xuexingdong.x.chatbot.plugin;

import com.xuexingdong.x.chatbot.core.ChatbotPlugin;
import com.xuexingdong.x.chatbot.webwx.WebWXResponse;
import com.xuexingdong.x.chatbot.webwx.WebWXTextMessage;
import com.xuexingdong.x.common.utils.XDateTimeUtils;
import org.springframework.stereotype.Component;

import java.time.LocalTime;
import java.util.Optional;

@Component
public class DefaultPlugin implements ChatbotPlugin {

    @Override
    public int order() {
        return 9999;
    }

    @Override
    public Optional<WebWXResponse> handleText(WebWXTextMessage textMessage) {
        if (!textMessage.getFromUsername().startsWith("@@") &&
                textMessage.getToUsername().startsWith("@@") &&
                textMessage.getFromUsername().startsWith("@")) {
            WebWXResponse response = new WebWXResponse();
            response.setToUsername(textMessage.getFromUsername());
            LocalTime morningWorkingHourStart = LocalTime.of(9, 0);
            LocalTime morningWorkingHourEnd = LocalTime.of(12, 0);
            LocalTime afternoonWorkingHourStart = LocalTime.of(13, 30);
            LocalTime afternoonWorkingHourEnd = LocalTime.of(18, 0);
            // 上班期间
            if (XDateTimeUtils.isInRange(LocalTime.now(), morningWorkingHourStart, morningWorkingHourEnd) ||
                    XDateTimeUtils.isInRange(LocalTime.now(), afternoonWorkingHourStart, afternoonWorkingHourEnd)) {
                response.setContent("上班中，请勿打扰");
                return Optional.of(response);
            }
        }
        return Optional.empty();
    }
}
