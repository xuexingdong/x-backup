package com.xuexingdong.x.ucenter;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cloud.client.SpringCloudApplication;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;


@EnableZuulProxy
@SpringCloudApplication
@EntityScan(basePackages = "com.xuexingdong.x.entity")
public class UCenterApplication {
    public static void main(String[] args) {
        SpringApplication.run(UCenterApplication.class, args);
    }
}
