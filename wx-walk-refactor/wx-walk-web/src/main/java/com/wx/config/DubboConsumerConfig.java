package com.wx.config;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.alibaba.dubbo.config.ApplicationConfig;
import com.alibaba.dubbo.config.ConsumerConfig;
import com.alibaba.dubbo.config.RegistryConfig;
import com.alibaba.fastjson.JSONObject;
import com.wx.config.disconf.DubboConfiguration;

@Configuration
public class DubboConsumerConfig {
	@Autowired
	private DubboConfiguration dubboConfiguration;

	/*相当于consumer.xml中的：<dubbo:application name="consumer"/>*/
    @Bean
    public ApplicationConfig myApplicationConfig(){
        ApplicationConfig ac = new ApplicationConfig();
        ac.setName(dubboConfiguration.getApplicationName());
        return ac;
    }
    /*相当于：<dubbo:registry address="39.108.125.227:2181" protocol="zookeeper"/>*/
    @Bean
    public RegistryConfig myRegistryConfig(){
        RegistryConfig rc = new RegistryConfig();
        System.out.println("------------"+JSONObject.toJSONString(dubboConfiguration, true));
        rc.setAddress(dubboConfiguration.getRegistryAddress());
        rc.setProtocol(dubboConfiguration.getRegistryProtocol());
        return rc;
    }
    
    @Bean
    public ConsumerConfig consumerConfig(){
    	ConsumerConfig cc = new ConsumerConfig();
    	cc.setRetries(0);
    	cc.setTimeout(300000);
        return cc;
    }
/*使用注解方式来代替：<dubbo:reference interface="top.free.dubboservice.TestDubboService" id="serviceImpl"></dubbo:reference>*/
}