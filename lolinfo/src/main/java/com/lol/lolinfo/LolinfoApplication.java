package com.lol.lolinfo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.security.autoconfigure.SecurityAutoConfiguration;

@SpringBootApplication(exclude = { SecurityAutoConfiguration.class })
public class LolinfoApplication {

    public static void main(String[] args) {
        SpringApplication.run(LolinfoApplication.class, args);
    }
}