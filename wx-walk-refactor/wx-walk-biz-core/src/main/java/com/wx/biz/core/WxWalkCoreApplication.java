package com.wx.biz.core;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

import com.alibaba.dubbo.config.spring.context.annotation.EnableDubbo;

import tk.mybatis.spring.annotation.MapperScan;

@SpringBootApplication
@MapperScan(basePackages = "com.wx.mapper")
@ComponentScan(basePackages = {"com.wx.service","com.wx.biz.core"})
@EnableDubbo//扫描所有包下使用dubbo提供的注解类
//或者@EnableDubbo(scanBasePackages = "top.free.dubboserviceimpl")只扫描特定包下有没有使用dubbo提供的注解
public class WxWalkCoreApplication {
	public static void main(String[] args) {
		SpringApplication.run(WxWalkCoreApplication.class,args);
	}
}
