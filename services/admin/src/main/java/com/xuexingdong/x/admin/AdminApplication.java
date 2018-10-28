package com.xuexingdong.x.admin;

import com.xuexingdong.x.jwt.JWTService;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@MapperScan("com.xuexingdong.x.mapper")
@ComponentScan(basePackageClasses = {AdminApplication.class, JWTService.class})
public class AdminApplication {
    public static void main(String[] args) {
        SpringApplication.run(AdminApplication.class, args);
    }
}
