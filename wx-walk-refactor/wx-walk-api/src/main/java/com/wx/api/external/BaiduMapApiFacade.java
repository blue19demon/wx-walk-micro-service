package com.wx.api.external;

import java.util.List;

import com.wx.api.external.resp.BaiduPlaceResp;
import com.wx.api.external.resp.UserLocationResp;

public interface BaiduMapApiFacade {

	public List<BaiduPlaceResp> searchPlace(String query, String lng, String lat);

	public String makeArticleList(List<BaiduPlaceResp> placeList, String bd09Lng, String bd09Lat);

	public UserLocationResp convertCoord(String lng, String lat);

	public String getLocation(String mjkd_latlng);
}
