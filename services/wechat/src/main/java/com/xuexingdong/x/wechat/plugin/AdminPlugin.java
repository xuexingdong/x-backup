package com.xuexingdong.x.wechat.plugin;// package com.xuexingdong.wechat.plugin;
//
// import com.xuexingdong.wechat.builder.TextBuilder;
// import com.xuexingdong.wechat.com.xuexingdong.ucenter.config.WechatConfig;
// import com.xuexingdong.wechat.sdk.com.xuexingdong.ucenter.model.message.request.common.TextMessage;
// import com.xuexingdong.wechat.sdk.com.xuexingdong.ucenter.model.message.response.TextResponse;
// import com.xuexingdong.wechat.sdk.com.xuexingdong.ucenter.model.message.response.WechatResponseMessage;
// import com.xuexingdong.wechat.sdk.handler.TextHandler;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.stereotype.Component;
//
// import java.util.Optional;
//
// @Component
// public class AdminPlugin implements TextHandler {
//
//     @Autowired
//     private WechatConfig wechatConfig;
//
//     @Override
//     public Optional<? extends WechatResponseMessage> handle(TextMessage message) {
//         TextBuilder tb = new TextBuilder();
//         // 自己回复
//         if ("oNzpit-zP5SZtHCa2Eg6laxdjBbI".equals(message.getFromUserName())) {
//             TextResponse textResponse = tb
//                     .from(message.getToUserName())
//                     .to(message.getContent())
//                     .content("管理员你好")
//                     .build();
//             System.out.println(textResponse.getContent());
//             return Optional.of(textResponse);
//         }
//         return Optional.empty();
//     }
// }
