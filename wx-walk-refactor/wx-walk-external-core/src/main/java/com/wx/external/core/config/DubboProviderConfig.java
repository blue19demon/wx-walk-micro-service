package com.wx.external.core.config;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.alibaba.dubbo.config.ApplicationConfig;
import com.alibaba.dubbo.config.ProtocolConfig;
import com.alibaba.dubbo.config.RegistryConfig;
import com.alibaba.fastjson.JSONObject;
import com.wx.external.core.config.disconf.DubboConfiguration;

@Configuration
public class DubboProviderConfig {
	@Autowired
	private DubboConfiguration dubboConfiguration;

	/*相当于xml中服务提供者的别名：<dubbo:application name="provider" />*/
    @Bean
    public ApplicationConfig myApplicationConfig(){
    	System.out.println("======="+JSONObject.toJSONString(dubboConfiguration, true));
        ApplicationConfig ac = new ApplicationConfig();
        ac.setName(dubboConfiguration.getApplicationName());
        return ac;
    }
    /*相当于注册中心配置：<dubbo:registry address="XXXXXXXX:2181" protocol="zookeeper"/>*/
    @Bean
    public RegistryConfig myregistryConfig(){
        RegistryConfig rc = new RegistryConfig();
        rc.setAddress(dubboConfiguration.getRegistryAddress());
        rc.setProtocol(dubboConfiguration.getRegistryProtocol());
        rc.setTimeout(3000);
       
        return rc;
    }
 /*相当于暴露服务的协议以及端口：<dubbo:protocol name="dubbo" port="20881"></dubbo:protocol>*/
    @Bean
    public ProtocolConfig myProtocolConfig(){
        ProtocolConfig pc = new ProtocolConfig();
        pc.setName(dubboConfiguration.getProtocolName());
        pc.setPort(Integer.parseInt(dubboConfiguration.getProtocolPort()));
        return pc;
    }
    /*在实现类上加注解来代替：暴露的服务<dubbo:service interface="top.free.dubboservice.TestDubboService" ref="serviceImpl" timeout="60000"></dubbo:service>*/
}