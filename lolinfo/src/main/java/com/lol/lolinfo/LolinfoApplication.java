package com.lol.lolinfo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

// Spring Security 자동 설정 제외
@SpringBootApplication
public class LolinfoApplication extends SpringBootServletInitializer {

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(LolinfoApplication.class);
    }

    public static void main(String[] args) {
        SpringApplication.run(LolinfoApplication.class, args);
    }
}