package com.wx.external.core.provider.strategy.impl;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONObject;
import com.wx.api.external.enums.ApiManifest;
import com.wx.external.core.config.disconf.PlatformAPIConfig;
import com.wx.external.core.provider.strategy.ApiStrategy;

@Component
public class ApiTuling extends ApiStrategy {
	@Autowired
	private PlatformAPIConfig platformAPIConfig;
	@Override
	public String buildURL(String content) {
		String apiUrl = null;
		try {
			apiUrl = String.format(platformAPIConfig.getTulingAPI(), platformAPIConfig.getTulingKey(),
					URLEncoder.encode(content, "utf-8"));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return apiUrl;
	}

	@Override
	public ApiManifest support() {
		return ApiManifest.TULING;
	}

	@Override
	public Boolean success(JSONObject resultJson) {
		return ((null != resultJson)
				&& ((100000 == resultJson.getInteger("code")) || (100000 == resultJson.getInteger("code"))));
	}

	@Override
	public String handleFail(JSONObject resultJson) {
		if (null == resultJson) {
			return "对不起，你说的话真是太高深了……";
		}
		return "待开发有点麻烦！\n";
	}

	@Override
	public String handleSuccess(JSONObject json) {
		StringBuffer bf = new StringBuffer();
		String s = "";
		if (100000 == json.getInteger("code")) {
			s = json.getString("text");
			bf.append(s);
		} else if (200000 == json.getInteger("code")) {
			s = json.getString("text");
			bf.append(s);
			bf.append("\n");
			s = json.getString("url");
			bf.append(s);
		}
		String result = bf.toString();
		if("".equals(result)) {
			result="无法回答你的问题";
		}
		return result;
	}

	@Override
	public Boolean isJuheAPI() {
		return Boolean.FALSE;
	}
}
