package com.wx.api.external;

import com.wx.api.external.enums.ApiManifest;

public interface PlatformAPIFacade {
	public String excuteHttp(String content, ApiManifest apiManifest);
	
	public String getNote();

	public String getServerUrl();

	public String getTeplateAuthSuccessMsg();
}
