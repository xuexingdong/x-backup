package com.xuexingdong.x.ucenter;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cloud.client.SpringCloudApplication;

@SpringCloudApplication
@EntityScan(basePackages = "com.xuexingdong.x.entity")
public class UCenterApplication {

    public static void main(String[] args) {
        SpringApplication.run(UCenterApplication.class, args);
    }
}
