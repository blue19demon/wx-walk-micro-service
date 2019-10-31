package com.wx.external.core;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

import com.alibaba.dubbo.config.spring.context.annotation.EnableDubbo;


@SpringBootApplication
@ComponentScan(basePackages = {"com.wx.external.core"})
@EnableDubbo
public class WxWalkExternalApplication {
	public static void main(String[] args) {
		SpringApplication.run(WxWalkExternalApplication.class,args);
	}

}
