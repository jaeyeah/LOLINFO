package com.lol.lolinfo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.security.autoconfigure.SecurityAutoConfiguration;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.cache.annotation.EnableCaching;

// Spring Security 자동 설정 제외
@EnableCaching
@SpringBootApplication(exclude = {SecurityAutoConfiguration.class})
public class LolinfoApplication extends SpringBootServletInitializer {

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(LolinfoApplication.class);
    }

    public static void main(String[] args) {
        SpringApplication.run(LolinfoApplication.class, args);
    }
}