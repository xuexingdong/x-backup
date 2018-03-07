// package com.xuexingdong.x.chatbot.plugin;
//
// import com.xuexingdong.x.chatbot.core.ChatbotPlugin;
// import com.xuexingdong.x.chatbot.webwx.WebWXResponse;
// import com.xuexingdong.x.chatbot.webwx.WebWXTextMessage;
// import org.springframework.stereotype.Component;
//
// import java.util.Optional;
//
// @Component
// public class LaughCountPlugin implements ChatbotPlugin {
//
//     @Override
//     public int order() {
//         return 0;
//     }
//
//     @Override
//     public Optional<WebWXResponse> handle(WebWXTextMessage textMessage) {
//         WebWXResponse response = new WebWXResponse();
//         switch (textMessage.getContent()) {
//             case "哈哈":
//                 response.setContent("");
//                 break;
//             default:
//                 break;
//         }
//         return Optional.of(response);
//     }
// }
