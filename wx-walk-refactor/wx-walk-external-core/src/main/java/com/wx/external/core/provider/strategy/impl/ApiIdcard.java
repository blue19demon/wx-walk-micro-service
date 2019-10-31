package com.wx.external.core.provider.strategy.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONObject;
import com.wx.api.external.enums.ApiManifest;
import com.wx.external.core.config.disconf.PlatformAPIConfig;
import com.wx.external.core.provider.strategy.ApiStrategy;

@Component
public class ApiIdcard extends ApiStrategy {
	@Autowired
	private PlatformAPIConfig platformAPIConfig;
	@Override
	public String buildURL(String idcard) {
		return String.format(platformAPIConfig.getIdcardAPI(), platformAPIConfig.getIdcardKey(), idcard);
	}

	@Override
	public ApiManifest support() {
		return ApiManifest.IDCARD;
	}

	@Override
	public String handleSuccess(JSONObject resultJson) {
		JSONObject result = resultJson.getJSONObject("result");
		StringBuffer buffer = new StringBuffer();
		buffer.append("地区:").append(result.getString("area")).append("\n");
		buffer.append("性别:").append(result.getString("sex")).append("\n");
		buffer.append("出生日期:").append(result.getString("birthday")).append("\n");
		return buffer.toString();
	}
	@Override
	public Boolean isJuheAPI() {
		return Boolean.TRUE;
	}
}
