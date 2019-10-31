
package com.wx.external.core.provider;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.web.client.RestTemplate;

import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.fastjson.JSONObject;
import com.wx.api.external.PlatformAPIFacade;
import com.wx.api.external.enums.ApiManifest;
import com.wx.external.core.config.disconf.AppConfiguration;
import com.wx.external.core.provider.strategy.ApiStrategy;

import lombok.extern.slf4j.Slf4j;
@Service
@Slf4j
public class PlatformAPIProvider implements PlatformAPIFacade{
	@Autowired
	private List<ApiStrategy> apiStrategys;
	@Autowired
	private RestTemplate restTemplate;
	@Autowired
	private AppConfiguration appConfiguration;
	
	@Override
	public String excuteHttp(String content, ApiManifest apiManifest) {
		ApiStrategy apiStrategy = chooseOne(apiManifest);
		String doValidate = apiStrategy.doValidate(content);
		if (doValidate != null) {
			return doValidate;
		}
		String apiUrl = apiStrategy.buildURL(content);
		log.info("api url=" + apiUrl);
		String result = restTemplate.exchange(apiUrl, HttpMethod.GET, null, String.class).getBody();
		log.info("api result=" +result);
		JSONObject resultJson = null;
		if(apiManifest==ApiManifest.MOBILE_QUERY) {
			resultJson = JSONObject.parseObject(result.substring("__GetZoneResult_ = ".length()));
		}else {
			resultJson = JSONObject.parseObject(result);
		}
		
		Boolean success = apiStrategy.success(resultJson);
		return success ? apiStrategy.handleSuccess(resultJson) : apiStrategy.handleFail(resultJson);
	}

	private ApiStrategy chooseOne(ApiManifest apiManifest) {
		for (ApiStrategy apiStrategy : apiStrategys) {
			if (apiManifest == apiStrategy.support()) {
				return apiStrategy;
			}
		}
		throw new IllegalArgumentException("未知的API");
	}

	@Override
	public String getNote() {
		return appConfiguration.getNote();
	}

	@Override
	public String getServerUrl() {
		return appConfiguration.getServerUrl();
	}

	@Override
	public String getTeplateAuthSuccessMsg() {
		return appConfiguration.getTeplateAuthSuccessMsg();
	}

}
