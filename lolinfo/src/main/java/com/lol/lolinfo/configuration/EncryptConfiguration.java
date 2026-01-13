package com.lol.lolinfo.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
public class EncryptConfiguration {
	@Bean
	public BCryptPasswordEncoder encoder() {
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		return new BCryptPasswordEncoder();
	}
}
