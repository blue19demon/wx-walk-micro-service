package com.wx.api.external.enums;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
public enum VedioAction {
	ALL("查单个"),
	ONE("查所有");
	private String desc;

	public String getDesc() {
		return desc;
	}
}
