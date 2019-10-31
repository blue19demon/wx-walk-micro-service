package com.wx.external.core.config.disconf;

import org.springframework.context.annotation.Configuration;

import com.baidu.disconf.client.common.annotations.DisconfFile;
import com.baidu.disconf.client.common.annotations.DisconfFileItem;

@Configuration
@DisconfFile(filename = "app.properties")
//对于不经常修改的配置
//@Scope("singleton")
public class AppConfiguration {

	private String serverUrl;

	private String teplateAuthSuccessMsg;
	
	private String uploadFolder;
	
	private String note;
	
	@DisconfFileItem(name = "app.serverUrl", associateField = "serverUrl")
	public String getServerUrl() {
		return serverUrl;
	}

	public void setServerUrl(String serverUrl) {
		this.serverUrl = serverUrl;
	}
	@DisconfFileItem(name = "app.teplateAuthSuccessMsg", associateField = "teplateAuthSuccessMsg")
	public String getTeplateAuthSuccessMsg() {
		return teplateAuthSuccessMsg;
	}

	public void setTeplateAuthSuccessMsg(String teplateAuthSuccessMsg) {
		this.teplateAuthSuccessMsg = teplateAuthSuccessMsg;
	}
	@DisconfFileItem(name = "app.uploadFolder", associateField = "uploadFolder")
	public String getUploadFolder() {
		return uploadFolder;
	}

	public void setUploadFolder(String uploadFolder) {
		this.uploadFolder = uploadFolder;
	}
	@DisconfFileItem(name = "app.note", associateField = "note")
	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}
}
