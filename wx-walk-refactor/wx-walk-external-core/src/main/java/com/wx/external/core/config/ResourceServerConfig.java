package com.wx.external.core.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class ResourceServerConfig {

	@Bean
	public RestTemplate restTemplate() {
		return new RestTemplate();
	}

}