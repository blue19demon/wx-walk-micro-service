package com.wx.api.external.resp;

import java.io.Serializable;

import lombok.Data;
@Data
public class BaiduPlaceResp implements Serializable,Comparable<BaiduPlaceResp> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	// 名称
	private String name;
	// 详细地址
	private String address;
	// 经度
	private String lng;
	// 纬度
	private String lat;
	// 联系电话
	private String telephone;
	// 距离
	private int distance;
	@Override
	public int compareTo(BaiduPlaceResp baiduPlace) {
		return this.distance - baiduPlace.getDistance();
	}
}
