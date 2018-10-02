// package com.xuexingdong.x.chatbot.plugin;
//
// import com.xuexingdong.x.chatbot.core.ChatbotPlugin;
// import com.xuexingdong.x.chatbot.webwx.WebWxResponse;
// import com.xuexingdong.x.chatbot.webwx.WebWxTextMessage;
// import com.xuexingdong.x.chatbot.webwx.WebWxUtils;
// import org.apache.commons.lang3.BooleanUtils;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.context.annotation.Profile;
// import org.springframework.data.redis.core.StringRedisTemplate;
// import org.springframework.stereotype.Component;
//
// import java.util.Optional;
//
// @Profile({"dev", "test"})
// @Component
// public class StatisticPlugin implements ChatbotPlugin {
//
//     private final StringRedisTemplate stringRedisTemplate;
//
//     @Autowired
//     public StatisticPlugin(StringRedisTemplate stringRedisTemplate) {
//         this.stringRedisTemplate = stringRedisTemplate;
//     }
//
//     @Override
//     public Optional<WebWxResponse> handleText(WebWxTextMessage textMessage) {
//         // 必须是个人号
//         if (WebWxUtils.isPerson(textMessage.getFromUsername())
//                 && "#统计".equals(textMessage.getContent())) {
//             WebWxResponse response = new WebWxResponse();
//             response.setToUsername(textMessage.getFromUsername());
//             // 这个chatid已经注册过
//             String chatId = textMessage.getFromUsername();
//             if (BooleanUtils.isTrue(stringRedisTemplate.opsForHash().hasKey("backend:chatid_userid_mapping", chatId))) {
//                 response.setContent("请勿重复注册");
//                 return Optional.of(response);
//             }
//             response.setContent(String.format("点击下方链接注册：http://%s/#/account?chatid=%s", siteurl, chatId));
//             return Optional.of(response);
//         }
//         return Optional.empty();
//     }
// }
