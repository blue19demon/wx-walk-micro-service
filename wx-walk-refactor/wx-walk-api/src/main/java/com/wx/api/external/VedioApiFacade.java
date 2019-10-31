package com.wx.api.external;

import com.alibaba.fastjson.JSONObject;
import com.wx.api.external.enums.VedioAction;

public interface VedioApiFacade {
	public String search(String fromUserName, String toUserName,VedioAction vedioAction);
	
	public JSONObject satin();
}
