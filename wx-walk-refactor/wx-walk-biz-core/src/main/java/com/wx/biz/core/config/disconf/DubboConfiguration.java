package com.wx.biz.core.config.disconf;

import org.springframework.context.annotation.Configuration;

import com.baidu.disconf.client.common.annotations.DisconfFile;
import com.baidu.disconf.client.common.annotations.DisconfFileItem;

@Configuration
@DisconfFile(filename = "dubboProvider.properties")
public class DubboConfiguration {

	private String applicationName;

	private String protocolName;
	
	private String protocolPort;
	
	private String registryAddress;
	
	private String registryProtocol;
	@DisconfFileItem(name = "dubbo.application.name", associateField = "applicationName")
	public String getApplicationName() {
		return applicationName;
	}

	public void setApplicationName(String applicationName) {
		this.applicationName = applicationName;
	}
	@DisconfFileItem(name = "dubbo.protocol.name", associateField = "protocolName")
	public String getProtocolName() {
		return protocolName;
	}

	public void setProtocolName(String protocolName) {
		this.protocolName = protocolName;
	}
	@DisconfFileItem(name = "dubbo.protocol.port", associateField = "protocolPort")
	public String getProtocolPort() {
		return protocolPort;
	}

	public void setProtocolPort(String protocolPort) {
		this.protocolPort = protocolPort;
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