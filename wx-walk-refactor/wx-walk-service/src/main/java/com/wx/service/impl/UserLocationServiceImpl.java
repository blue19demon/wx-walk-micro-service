package com.wx.service.impl;

import java.lang.reflect.InvocationTargetException;
import java.util.Date;

import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wx.api.external.resp.UserLocationResp;
import com.wx.domain.UserLocation;
import com.wx.mapper.UserLocationMapper;
import com.wx.service.UserLocationService;
@Service
public class UserLocationServiceImpl implements UserLocationService {
	@Autowired
    private UserLocationMapper userLocationMapper ;
	@Override
	public void saveUserLocation(UserLocation domain) {
		domain.setCreated(new Date());
		userLocationMapper.insertSelective(domain);
	}
	@Override
	public UserLocationResp getLastLocation(String fromUserName) {
		UserLocation userLocation= userLocationMapper.getLastLocation(fromUserName);
		if(userLocation==null) {
			return null;
		}
		UserLocationResp resp=new UserLocationResp();
		try {
			BeanUtils.copyProperties(resp,userLocation);
		} catch (IllegalAccessException | InvocationTargetException e) {
			e.printStackTrace();
		}
		return resp;
	}

}
