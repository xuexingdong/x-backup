// package com.xuexingdong.x.chatbot.plugin;
//
// import com.xuexingdong.x.chatbot.core.ChatbotPlugin;
// import com.xuexingdong.x.chatbot.webwx.WebWxResponse;
// import com.xuexingdong.x.chatbot.webwx.WebWxTextMessage;
// import com.xuexingdong.x.chatbot.webwx.WebWxUtils;
// import com.xuexingdong.x.common.utils.XDateTimeUtils;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.data.redis.core.StringRedisTemplate;
// import org.springframework.stereotype.Component;
//
// import java.time.LocalDate;
// import java.time.LocalTime;
// import java.util.Optional;
//
// import static java.time.DayOfWeek.SATURDAY;
// import static java.time.DayOfWeek.SUNDAY;
//
// @Component
// public class DefaultPlugin implements ChatbotPlugin {
//     private static LocalTime morningWorkingHourStart = LocalTime.of(9, 0);
//     private static LocalTime morningWorkingHourEnd = LocalTime.of(12, 0);
//     private static LocalTime afternoonWorkingHourStart = LocalTime.of(13, 30);
//     private static LocalTime afternoonWorkingHourEnd = LocalTime.of(18, 0);
//
//     private final StringRedisTemplate stringRedisTemplate;
//
//     @Autowired
//     public DefaultPlugin(StringRedisTemplate stringRedisTemplate) {
//         this.stringRedisTemplate = stringRedisTemplate;
//     }
//
//     @Override
//     public int order() {
//         return 9999;
//     }
//
//     @Override
//     public Optional<WebWxResponse> handleText(WebWxTextMessage textMessage) {
//         String self = stringRedisTemplate.opsForValue().get("chatbot:self_chatid");
//         // 必须是一个个人发给我的
//         if (WebWxUtils.isPerson(textMessage.getFromUsername())
//                 && textMessage.getToUsername().equals(self)) {
//             // 是周末
//             if (LocalDate.now().getDayOfWeek() == SATURDAY
//                     || LocalDate.now().getDayOfWeek() == SUNDAY) {
//                 return Optional.empty();
//             }
//             WebWxResponse response = new WebWxResponse();
//             response.setToUsername(textMessage.getFromUsername());
//             // 上班期间
//             if (XDateTimeUtils.isInRange(LocalTime.now(), morningWorkingHourStart, morningWorkingHourEnd) ||
//                     XDateTimeUtils.isInRange(LocalTime.now(), afternoonWorkingHourStart, afternoonWorkingHourEnd)) {
//                 response.setContent("上班中，请勿打扰");
//                 return Optional.of(response);
//             }
//         }
//         return Optional.empty();
//     }
// }
