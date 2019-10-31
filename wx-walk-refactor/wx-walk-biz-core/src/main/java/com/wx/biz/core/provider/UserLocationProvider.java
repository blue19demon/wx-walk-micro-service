package com.wx.biz.core.provider;

import java.lang.reflect.InvocationTargetException;

import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.dubbo.config.annotation.Service;
import com.wx.api.biz.UserLocationFacade;
import com.wx.api.biz.req.UserLocationCreateReq;
import com.wx.api.external.resp.UserLocationResp;
import com.wx.domain.UserLocation;
import com.wx.service.UserLocationService;

@Service
public class UserLocationProvider implements UserLocationFacade{

	@Autowired
    private UserLocationService userLocationService ;
	@Override
	public void saveUserLocation(UserLocationCreateReq req) {
		UserLocation domain=new UserLocation();
		try {
			BeanUtils.copyProperties(domain,req);
		} catch (IllegalAccessException | InvocationTargetException e) {
			e.printStackTrace();
		}
		userLocationService.saveUserLocation(domain);
	}
	@Override
	public UserLocationResp getLastLocation(String fromUserName) {
		return userLocationService.getLastLocation(fromUserName);
	}

}
