package com.wx.external.core.provider.strategy;

import com.alibaba.fastjson.JSONObject;
import com.wx.api.external.enums.ApiManifest;

public abstract class ApiStrategy {
	
	public String doValidate(String content) {
		return null;
	}
	public abstract Boolean isJuheAPI();
	
	public abstract String buildURL(String content);
	
	public  Boolean success(JSONObject resultJson) {
		if(isJuheAPI()) {
			return resultJson.getInteger("error_code") == 0;
		}
		return success(resultJson);
	}
	
	public String handleFail(JSONObject resultJson) {
		if(isJuheAPI()) {
			return resultJson.get("error_code") + ":" + resultJson.get("reason");
		}
		return handleFail(resultJson);
	}
	
	public abstract String handleSuccess(JSONObject resultJson);
	
	public abstract ApiManifest support();
}
