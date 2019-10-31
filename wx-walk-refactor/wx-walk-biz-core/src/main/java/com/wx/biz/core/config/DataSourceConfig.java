package com.wx.biz.core.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.wx.biz.core.config.disconf.DSConfiguration;

@Configuration
public class DataSourceConfig {
     
	@Autowired
	private DSConfiguration dsConfiguration;
    @Bean
    public DataSource getDataSource() {
        DataSourceBuilder<?> dataSourceBuilder = DataSourceBuilder.create();
        dataSourceBuilder.driverClassName(dsConfiguration.getDriverClassName());
        dataSourceBuilder.url(dsConfiguration.getUrl());
        dataSourceBuilder.username(dsConfiguration.getUsername());
        dataSourceBuilder.password(dsConfiguration.getPassword());
        return dataSourceBuilder.build();
    }
    
}