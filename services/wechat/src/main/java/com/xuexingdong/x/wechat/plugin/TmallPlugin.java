// package com.xuexingdong.x.wechat.plugin;
//
// import com.xuexingdong.x.wechat.sdk.builder.TextBuilder;
// import com.xuexingdong.x.wechat.sdk.handler.TextHandler;
// import com.xuexingdong.x.wechat.sdk.message.request.common.TextMessage;
// import com.xuexingdong.x.wechat.sdk.message.response.TextResponse;
// import com.xuexingdong.x.wechat.sdk.message.response.WechatResponseMessage;
// import org.slf4j.Logger;
// import org.slf4j.LoggerFactory;
// import org.springframework.amqp.core.AmqpAdmin;
// import org.springframework.amqp.core.AmqpTemplate;
// import org.springframework.amqp.core.Queue;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.stereotype.Component;
//
// import javax.annotation.PostConstruct;
// import java.util.Optional;
//
// @Component
// public class TmallPlugin implements TextHandler {
//
//     private static final Logger logger = LoggerFactory.getLogger(TmallPlugin.class);
//
//     public static final String RABBITMQ_QUEUE_NAME = "crawler:tmall";
//
//     @Autowired
//     private AmqpTemplate amqpTemplate;
//
//     @Autowired
//     private AmqpAdmin amqpAdmin;
//
//     @Override
//     public Optional<? extends WechatResponseMessage> handleText(TextMessage textMessage) {
//         amqpTemplate.convertAndSend(RABBITMQ_QUEUE_NAME, textMessage.getContent());
//         TextBuilder tb = new TextBuilder();
//         TextResponse textResponse = tb
//                 .from(textMessage.getToUserName())
//                 .to(textMessage.getFromUserName())
//                 .content(String.format("成功监听天猫链接: %s", textMessage.getContent()))
//                 .build();
//         logger.info("Tmall link: {}", textMessage.getContent());
//         return Optional.of(textResponse);
//     }
//
//     @PostConstruct
//     public void init() {
//         Queue queue = new Queue(RABBITMQ_QUEUE_NAME, false);
//         amqpAdmin.declareQueue(queue);
//     }
// }
