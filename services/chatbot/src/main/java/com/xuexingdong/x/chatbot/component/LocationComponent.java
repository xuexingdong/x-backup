package com.xxd.x.chatbot.component;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.xxd.x.chatbot.model.Location;
import com.xxd.x.chatbot.repository.LocationRepository;
import com.xxd.x.chatbot.util.GPSUtil;
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
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Component
public class LocationComponent {

    private static final Logger logger = LoggerFactory.getLogger(LocationComponent.class);

    private static final String GAODE_API = "https://restapi.amap.com/v3/geocode/regeo?key={key}&location={location}";

    @Autowired
    private LocationRepository locationRepository;

    @Autowired
    private RestTemplate restTemplate;

    @Value("${gaode-key}")
    private String gaodeKey;

    @Autowired
    private ObjectMapper objectMapper;

    public String getLocationInfo() {
        Optional<Location> locationOptional = locationRepository.findAll(PageRequest.of(0, 1,
                Sort.by(Sort.Order.desc("created_at")))).stream().findFirst();
        StringBuilder sb = new StringBuilder();
        if (locationOptional.isPresent()) {
            Location location = locationOptional.get();
            // 谷歌卫星到高德的坐标转换
            double[] transformedLocation = GPSUtil.gps84_To_Gcj02(location.getLatitude(), location.getLongitude());
            getAddressText(transformedLocation[0], transformedLocation[1]).ifPresent(address ->
                    sb.append(String.format("主人所在地理位置为: \n%s\n最后更新于\n"
                            + location.getCreatedAt().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME), address)));
        } else {
            sb.append("暂无地理信息");
        }
        return sb.toString();
    }

    /**
     * Get address text use gaode regeo API
     *
     * @param lat 精度
     * @param lng 纬度
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
