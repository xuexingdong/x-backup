// package com.xuexingdong.x.wechat.plugin;
//
// import com.xuexingdong.x.wechat.sdk.message.request.common.TextMessage;
// import com.xuexingdong.x.wechat.sdk.message.response.TextResponse;
// import com.xuexingdong.x.wechat.sdk.message.response.WechatResponseMessage;
// import org.apache.commons.lang3.BooleanUtils;
// import org.slf4j.Logger;
// import org.slf4j.LoggerFactory;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.data.redis.core.StringRedisTemplate;
// import org.springframework.stereotype.Component;
//
// import java.time.LocalTime;
// import java.util.Optional;
// import java.util.concurrent.TimeUnit;
//
// @Component
// public class FunctionPlugin implements WechatPlugin {
//
//     private static final Logger logger = LoggerFactory.getLogger(FunctionPlugin.class);
//
//     @Autowired
//     private StringRedisTemplate stringRedisTemplate;
//
//     @Override
//     public Optional<? extends WechatResponseMessage> handleText(TextMessage textMessage) {
//         TextResponse response = null;
//         if (textMessage.getContent().startsWith("#")) {
//             String keyword = textMessage.getContent().substring(1);
//             StringBuilder sb = new StringBuilder();
//             switch (keyword) {
//                 case "帮助":
//                     sb.append("功能列表如下\n");
//                     for (String function : pluginConfig.getFunctions()) {
//                         sb.append("#").append(function).append("\n");
//                     }
//                     response.setContent(sb.toString());
//                     break;
//                 case "密码":
//                     // 生成随机六位数
//                     String pointsRedisKey = "chatbot:secret:" + textMessage.getFromUsername();
//                     int points = 0;
//                     Integer pointsRedis = redisTemplate.opsForValue().get(pointsRedisKey);
//                     if (pointsRedis != null) {
//                         points = pointsRedis;
//                     }
//                     response.setContent("积分: " + points);
//                     break;
//                 case "积分":
//                     String pointsRedisKey = "chatbot:points:" + textMessage.getFromUsername();
//                     int points = 0;
//                     Integer pointsRedis = redisTemplate.opsForValue().get(pointsRedisKey);
//                     if (pointsRedis != null) {
//                         points = pointsRedis;
//                     }
//                     response.setContent("积分: " + points);
//                     break;
//                 case "签到":
//                     String redisKey = "sign:" + textMessage.getFromUsername();
//                     if (BooleanUtils.isTrue(redisTemplate.hasKey(redisKey))) {
//                         response.setContent("一天一次，再签无效");
//                     } else {
//                         int seconds = LocalTime.now().toSecondOfDay();
//                         logger.info("用户【{}】签到");
//                         Long newPoints = redisTemplate.opsForValue().increment("points:" + textMessage.getFromUsername(), 3);
//                         redisTemplate.opsForValue().set(redisKey, 1, seconds, TimeUnit.SECONDS);
//                         response.setContent(String.format("签到成功，积分+%d，当前积分为: %d", 3, newPoints));
//                     }
//                     break;
//                 default:
//                     response.setContent("没有该功能");
//             }
//             return Optional.of(response);
//         }
//
//         return Optional.empty();
//     }
// }
