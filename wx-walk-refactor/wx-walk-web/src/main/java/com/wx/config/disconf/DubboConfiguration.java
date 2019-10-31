package com.wx.config.disconf;

import org.springframework.context.annotation.Configuration;

import com.baidu.disconf.client.common.annotations.DisconfFile;
import com.baidu.disconf.client.common.annotations.DisconfFileItem;

@Configuration
@DisconfFile(filename = "dubboConsomer.properties")
public class DubboConfiguration {

	private String applicationName;
	
	private String registryAddress;
	
	private String registryProtocol;
	@DisconfFileItem(name = "dubbo.application.name", associateField = "applicationName")
	public String getApplicationName() {
		return applicationName;
	}

	public void setApplicationName(String applicationName) {
		this.applicationName = applicationName;
	}
	
	@DisconfFileItem(name = "dubbo.registry.address", associateField = "registryAddress")
	public String getRegistryAddress() {
		return registryAddress;
	}
	
	public void setRegistryAddress(String registryAddress) {
		this.registryAddress = registryAddress;
	}
	@DisconfFileItem(name = "dubbo.registry.protocol", associateField = "registryProtocol")
	public String getRegistryProtocol() {
		return registryProtocol;
	}

	public void setRegistryProtocol(String registryProtocol) {
		this.registryProtocol = registryProtocol;
	}
}