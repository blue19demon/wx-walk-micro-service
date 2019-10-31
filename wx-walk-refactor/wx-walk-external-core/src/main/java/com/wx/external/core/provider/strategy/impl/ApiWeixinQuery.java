package com.wx.external.core.provider.strategy.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.wx.api.external.enums.ApiManifest;
import com.wx.external.core.config.disconf.PlatformAPIConfig;
import com.wx.external.core.provider.strategy.ApiStrategy;
import com.wx.external.core.utils.RandomArray;

@Component
public class ApiWeixinQuery extends ApiStrategy {
	@Autowired
	private PlatformAPIConfig platformAPIConfig;
	@Override
	public String buildURL(String content) {
		String apiUrl = String.format(platformAPIConfig.getWeixinQueryAPI(), 
				platformAPIConfig.getWeixinQueryKey());
		return apiUrl;
	}

	@Override
	public ApiManifest support() {
		return ApiManifest.WEIXIN_QUERY;
	}

	@Override
	public String handleSuccess(JSONObject resultJson) {
		JSONObject result = resultJson.getJSONObject("result");
		StringBuffer buffer = new StringBuffer();
		JSONArray data = RandomArray.createRandomList(result.getJSONArray("list"),5);
		if (data != null && data.size() > 0) {
			buffer.append("----------------------------\n");
			for (int i = 0; i < data.size(); i++) {
				JSONObject item = data.getJSONObject(i);
				buffer.append("<a href='"+item.getString("url")+"'>"+item.getString("title")+"</a>").append("\n");
				buffer.append("("+item.getString("source")+")").append("\n");
				if (i != data.size() - 1) {
					buffer.append("----------------------------\n");
				}
			}
		}
		return buffer.toString();
	}
	
	@Override
	public Boolean isJuheAPI() {
		return Boolean.TRUE;
	}
}
