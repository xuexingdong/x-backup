package com.xuexingdong.x.admin.component;

import okhttp3.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.io.*;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class ChatbotComponent implements CommandLineRunner {

    private static final Logger logger = LoggerFactory.getLogger(ChatbotComponent.class);

    private static final String REDIS_KEY_PREFIX = "chatbot:client:";

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    private List<Map<String, String>> friends;

    public List<Map<String, String>> getFriends() {
        return friends;
    }

    static class LocalCookieJar implements CookieJar {
        private List<Cookie> cookies = new ArrayList<>();

        @Override
        public void saveFromResponse(HttpUrl url, List<Cookie> cookies) {
            this.cookies = cookies;
        }

        @Override
        public List<Cookie> loadForRequest(HttpUrl url) {
            if (cookies != null)
                return cookies;
            return new ArrayList<>();
        }
    }

    @Override
    public void run(String... args) {
        friends = initFriends();
        Map<String, String> cookieMap = getCookies();
        // TODO 删除原有头像
        if (!cookieMap.isEmpty()) {
            LocalCookieJar cookieJar = new LocalCookieJar();
            for (Map.Entry<String, String> entry : getCookies().entrySet()) {
                Cookie cookie = new Cookie.Builder()
                        .domain("wx2.qq.com")
                        .path("/")
                        .name(entry.getKey())
                        .value(entry.getValue())
                        .build();
                cookieJar.cookies.add(cookie);
            }
            OkHttpClient client = new OkHttpClient().newBuilder().cookieJar(cookieJar).build();
            for (Map<String, String> friend : friends) {
                String url = getFullHeadImgUrl("https://wx2.qq.com" + friend.get("head_img_url"));
                Request request = new Request.Builder()
                        .url(url)
                        .build();
                Call call = client.newCall(request);
                call.enqueue(new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        logger.error("Call error when get head pic {}", call.request().url());
                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        ResponseBody body = response.body();
                        if (body != null) {
                            OutputStream os = Files.newOutputStream(new File(String.format("data/%s.jpg", friend.get("username"))).toPath());
                            os.write(body.bytes());
                            os.close();
                        } else {
                            logger.error("Body is null when get head pic {}", call.request().url());
                        }
                    }
                });
            }
        }
    }


    /**
     * get wechat full head pic url instead of thumbnail
     *
     * @param url
     * @return
     */
    private String getFullHeadImgUrl(String url) {
        return url.replace("type=slave", "");
    }

    private List<Map<String, String>> initFriends() {
        Set<String> keys = stringRedisTemplate.keys(REDIS_KEY_PREFIX + "friend:*");
        if (keys == null) {
            return new ArrayList<>();
        }
        return keys.parallelStream().map(key -> {
            Map<Object, Object> map = stringRedisTemplate.opsForHash().entries(key);
            return map.entrySet().parallelStream().collect(Collectors.toMap(k -> (String) k.getKey(), v -> (String) v.getValue()));
        }).collect(Collectors.toList());
    }

    private Map<String, String> getCookies() {
        return stringRedisTemplate.opsForHash().entries(REDIS_KEY_PREFIX + "cookie")
                .entrySet().parallelStream().collect(Collectors.toMap(k -> (String) k.getKey(), v -> (String) v.getValue()));
    }
}
