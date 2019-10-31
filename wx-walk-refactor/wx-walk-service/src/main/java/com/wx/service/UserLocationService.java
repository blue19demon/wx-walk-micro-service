package com.wx.service;

import com.wx.api.external.resp.UserLocationResp;
import com.wx.domain.UserLocation;

public interface UserLocationService {
	public void saveUserLocation(UserLocation domain);

	public UserLocationResp getLastLocation(String fromUserName);
}
