package com.xxd.x.wechat.plugin;// package com.xxd.wechat.plugin;
//
// import com.xxd.wechat.builder.TextBuilder;
// import com.xxd.wechat.com.xxd.ucenter.constant.WechatConfig;
// import com.xxd.wechat.sdk.com.xxd.ucenter.model.message.request.common.TextMessage;
// import com.xxd.wechat.sdk.com.xxd.ucenter.model.message.response.TextResponse;
// import com.xxd.wechat.sdk.com.xxd.ucenter.model.message.response.WechatResponseMessage;
// import com.xxd.wechat.sdk.handler.TextHandler;
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
