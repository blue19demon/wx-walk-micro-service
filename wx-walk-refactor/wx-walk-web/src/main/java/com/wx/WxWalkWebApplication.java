package com.wx;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.wx.runner.ApplicationStartup;

@SpringBootApplication
public class WxWalkWebApplication {
	public static void main(String[] args) {
		SpringApplication springApplication = new SpringApplication(WxWalkWebApplication.class);
		springApplication.addListeners(new ApplicationStartup());
		springApplication.run(args);
	}

}
