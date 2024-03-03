// package com.xxd.x.wechat.plugin;// package com.xxd.wechat.plugin;
//
// import com.xxd.x.wechat.sdk.builder.TextBuilder;
// import com.xxd.x.wechat.sdk.message.request.common.TextMessage;
// import com.xxd.x.wechat.sdk.message.request.event.SubscribeEvent;
// import com.xxd.x.wechat.sdk.message.response.TextResponse;
// import com.xxd.x.wechat.sdk.message.response.WechatResponseMessage;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.data.redis.core.StringRedisTemplate;
// import org.springframework.stereotype.Component;
//
// import java.util.Optional;
//
// @Component
// public class HelloPlugin implements WechatPlugin {
//
//     @Autowired
//     private StringRedisTemplate stringRedisTemplate;
//
//     public static final String WELCOME = "订阅号无法开通菜单，回复对应序号体验相应功能，会话有效期为5分钟，按0退出到初始会话\n" +
//             "\n" +
//             "1. 天猫商品价格监控\n" +
//             "2. 许愿墙\n" +
//             "\uD83D\uDC40. 发送语音说出你的微信名称体验隐藏功能(你信吗)\n" +
//             "";
//
//     @Override
//     public Optional<? extends WechatResponseMessage> handleText(TextMessage textMessage) {
//         if ("0".equalsIgnoreCase(textMessage.getContent())) {
//             stringRedisTemplate.delete("wechat:session:" + textMessage.getFromUserName());
//         }
//         TextResponse textResponse = new TextBuilder()
//                 .from(textMessage.getToUserName())
//                 .to(textMessage.getFromUserName())
//                 .content(WELCOME)
//                 .build();
//         return Optional.of(textResponse);
//     }
//
//     @Override
//     public int order() {
//         return 1;
//     }
//
//     @Override
//     public Optional<? extends WechatResponseMessage> handleSubscribe(SubscribeEvent event) {
//         TextResponse textResponse = new TextBuilder()
//                 .from(event.getToUserName())
//                 .to(event.getFromUserName())
//                 .content(WELCOME)
//                 .build();
//         return Optional.of(textResponse);
//     }
// }
