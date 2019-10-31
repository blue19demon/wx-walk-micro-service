package com.wx.mapper;
import com.wx.base.mapper.MyMapper;
import com.wx.domain.UserLocation;

public interface UserLocationMapper extends MyMapper<UserLocation> {

	UserLocation getLastLocation(String openId);
}