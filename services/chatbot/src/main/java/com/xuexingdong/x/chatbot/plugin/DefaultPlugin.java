package com.xuexingdong.x.chatbot.plugin;

import com.xuexingdong.x.chatbot.core.ChatbotPlugin;
import com.xuexingdong.x.chatbot.webwx.WebWxResponse;
import com.xuexingdong.x.chatbot.webwx.WebWxTextMessage;
import com.xuexingdong.x.chatbot.webwx.WebWxUtils;
import com.xuexingdong.x.common.utils.XDateTimeUtils;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Optional;

import static java.time.DayOfWeek.SATURDAY;
import static java.time.DayOfWeek.SUNDAY;

@Component
public class DefaultPlugin implements ChatbotPlugin {
    private static LocalTime morningWorkingHourStart = LocalTime.of(9, 0);
    private static LocalTime morningWorkingHourEnd = LocalTime.of(12, 0);
    private static LocalTime afternoonWorkingHourStart = LocalTime.of(13, 30);
    private static LocalTime afternoonWorkingHourEnd = LocalTime.of(18, 0);

    @Override
    public int order() {
        return 9999;
    }

    @Override
    public Optional<WebWxResponse> handleText(WebWxTextMessage textMessage) {
        // 不是个人
        if (!WebWxUtils.isPerson(textMessage.getFromUsername())
                || !WebWxUtils.isPerson(textMessage.getToUsername())) {
            return Optional.empty();
        }
        // 是周末
        if (LocalDate.now().getDayOfWeek() == SATURDAY
                || LocalDate.now().getDayOfWeek() == SUNDAY) {
            return Optional.empty();
        }
        WebWxResponse response = new WebWxResponse();
        response.setToUsername(textMessage.getFromUsername());
        // 上班期间
        if (XDateTimeUtils.isInRange(LocalTime.now(), morningWorkingHourStart, morningWorkingHourEnd) ||
                XDateTimeUtils.isInRange(LocalTime.now(), afternoonWorkingHourStart, afternoonWorkingHourEnd)) {
            response.setContent("上班中，请勿打扰");
            return Optional.of(response);
        }
        return Optional.empty();
    }
}
