package com.xuexingdong.x.chatbot.plugin;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.xuexingdong.x.chatbot.core.ChatbotPlugin;
import com.xuexingdong.x.chatbot.event.Event;
import com.xuexingdong.x.chatbot.event.WebWxEvents;
import com.xuexingdong.x.chatbot.model.Location;
import com.xuexingdong.x.chatbot.repository.LocationRepository;
import com.xuexingdong.x.chatbot.util.GPSUtil;
import com.xuexingdong.x.chatbot.webwx.WebWxTextMessage;
import com.xuexingdong.x.chatbot.webwx.WebWxUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Component
public class LocationPlugin implements ChatbotPlugin {

    private static final Logger logger = LoggerFactory.getLogger(LocationPlugin.class);

    private static final String GAODE_API = "https://restapi.amap.com/v3/geocode/regeo?key={key}&location={location}";

    private final LocationRepository locationRepository;

    private final RestTemplate restTemplate;

    @Value("${gaode-key}")
    private String gaodeKey;

    private final ObjectMapper objectMapper;

    @Autowired
    public LocationPlugin(LocationRepository locationRepository, RestTemplate restTemplate, ObjectMapper objectMapper) {
        this.locationRepository = locationRepository;
        this.restTemplate = restTemplate;
        this.objectMapper = objectMapper;
    }

    @Override
    public List<Event> handleText(WebWxTextMessage textMessage) {
        List<Event> events = new ArrayList<>();
        // 必须是个人号
        if (WebWxUtils.isFromPerson(textMessage)
                && "#定位".equals(textMessage.getContent())) {
            Optional<Location> locationOptional = locationRepository.findAll(PageRequest.of(0, 1,
                    Sort.by(Sort.Order.desc("created_at")))).stream().findFirst();
            StringBuilder sb = new StringBuilder();
            if (locationOptional.isPresent()) {
                Location location = locationOptional.get();
                // 谷歌卫星到高德的坐标转换
                double[] transformedLocation = GPSUtil.gps84_To_Gcj02(location.getLatitude(), location.getLongitude());
                getAddressText(transformedLocation[1], transformedLocation[0]).ifPresent(address ->
                        sb.append(String.format("主人所在地理位置为: \n%s\n最后更新于\n"
                                + location.getCreatedAt().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME), address)));
            } else {
                sb.append("暂无地理信息");
            }
            events.add(WebWxEvents.sendText(textMessage.getFromUsername(), sb.toString()));
        }
        return events;
    }


    /**
     * Get address text use gaode regeo API
     *
     * @param lat
     * @param lng
     * @return
     */
    private Optional<String> getAddressText(double lat, double lng) {
        Map<String, String> params = new HashMap<>();
        params.put("key", gaodeKey);
        params.put("location", lng + "," + lat);
        String resp = restTemplate.getForObject(GAODE_API, String.class, params);
        JsonNode node;
        try {
            node = objectMapper.readTree(resp);
        } catch (IOException e) {
            logger.error("解析json出错");
            return Optional.empty();
        }
        if (!"1".equals(node.get("status").asText())) {
            logger.error("高德API错误");
            return Optional.empty();
        }
        String address = node.get("regeocode").get("formatted_address").asText();
        return Optional.of(address);
    }
}
