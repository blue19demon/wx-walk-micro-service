package com.wx.api.biz.req;

import java.io.Serializable;

import lombok.Data;
/**
 * 
 * 创建数据 Req
 * @author Administrator
 *
 */
@Data
public class UserLocationCreateReq implements Serializable {

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
