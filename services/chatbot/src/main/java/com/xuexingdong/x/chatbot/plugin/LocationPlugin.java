// package com.xuexingdong.x.chatbot.plugin;
//
// import com.xuexingdong.x.chatbot.core.ChatbotPlugin;
// import com.xuexingdong.x.chatbot.model.Location;
// import com.xuexingdong.x.chatbot.repository.LocationRepository;
// import com.xuexingdong.x.chatbot.webwx.WebWxResponse;
// import com.xuexingdong.x.chatbot.webwx.WebWxTextMessage;
// import com.xuexingdong.x.chatbot.webwx.WebWxUtils;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.data.domain.PageRequest;
// import org.springframework.data.domain.Sort;
// import org.springframework.stereotype.Component;
//
// import java.util.Optional;
//
// @Component
// public class LocationPlugin implements ChatbotPlugin {
//
//     private final LocationRepository locationRepository;
//
//     @Autowired
//     public LocationPlugin(LocationRepository locationRepository) {
//         this.locationRepository = locationRepository;
//     }
//
//     @Override
//     public Optional<WebWxResponse> handleText(WebWxTextMessage textMessage) {
//         // 必须是个人号
//         if (WebWxUtils.isPerson(textMessage.getFromUsername())
//                 && "你在哪".equals(textMessage.getContent())) {
//             Optional<Location> locationOptional = locationRepository.findAll(PageRequest.of(1, 1,
//                     Sort.by(Sort.Order.desc("created_at")))).stream().findFirst();
//             WebWxResponse response = new WebWxResponse();
//             response.setToUsername(textMessage.getFromUsername());
//             if (!locationOptional.isPresent()) {
//                 response.setContent("暂无个人信息");
//             } else {
//                 response.setContent(String.format("经纬度: %f, %f",
//                         locationOptional.get().getLongitude(), locationOptional.get().getLatitude()));
//             }
//             return Optional.of(response);
//         }
//         return Optional.empty();
//     }
// }
