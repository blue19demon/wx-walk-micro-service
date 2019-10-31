package com.wx.dto;

import java.io.Serializable;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserLocationDTO implements Serializable {

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
