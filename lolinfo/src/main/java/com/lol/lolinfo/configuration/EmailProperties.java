package com.lol.lolinfo.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Data;

@Data
@Component
@ConfigurationProperties(prefix="custom.email")
public class EmailProperties {

	private String username; // custom.email.username
	private String password; // custom.email.password
}

