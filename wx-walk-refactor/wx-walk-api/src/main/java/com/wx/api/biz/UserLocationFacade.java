package com.wx.api.biz;

import com.wx.api.biz.req.UserLocationCreateReq;
import com.wx.api.external.resp.UserLocationResp;

public interface UserLocationFacade {

	public void saveUserLocation(UserLocationCreateReq req);

	public UserLocationResp getLastLocation(String fromUserName);
}
