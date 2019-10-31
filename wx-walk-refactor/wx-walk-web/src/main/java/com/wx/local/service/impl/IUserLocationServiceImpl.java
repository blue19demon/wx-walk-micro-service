package com.wx.local.service.impl;

import java.lang.reflect.InvocationTargetException;

import org.apache.commons.beanutils.BeanUtils;
import org.springframework.stereotype.Service;

import com.alibaba.dubbo.config.annotation.Reference;
import com.wx.api.biz.UserLocationFacade;
import com.wx.api.biz.req.UserLocationCreateReq;
import com.wx.dto.UserLocationDTO;
import com.wx.local.service.IUserLocationService;
@Service
public class IUserLocationServiceImpl implements IUserLocationService {

	@Reference
	private UserLocationFacade userLocationFacade;
	@Override
	public void save(UserLocationDTO dto) {
		UserLocationCreateReq req=new UserLocationCreateReq();
		try {
			BeanUtils.copyProperties(req,dto);
		} catch (IllegalAccessException | InvocationTargetException e) {
			e.printStackTrace();
		}
		userLocationFacade.saveUserLocation(req);
	}

}
