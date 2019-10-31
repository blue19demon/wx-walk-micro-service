package com.wx.api.external.resp;

import java.io.Serializable;

import lombok.Data;
/**
 * 
 * @author Administrator
 *
 */
@Data
public class UserLocationResp implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String openId;

	private String lng;

	private String lat;

	private String bd09Lng;

	private String bd09Lat;

	private String locationCN;
}
