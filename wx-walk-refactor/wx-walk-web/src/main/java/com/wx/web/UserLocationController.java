package com.wx.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.wx.dto.UserLocationDTO;
import com.wx.local.service.IUserLocationService;

import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
public class UserLocationController {
	@Autowired
	protected IUserLocationService userLocationService;

	@GetMapping("/userLocation/save")
	public String save() {
		UserLocationDTO dto = UserLocationDTO.builder().openId("1").lat("2").lng("3").bd09Lat("c").bd09Lng("d")
				.locationCN("F").build();
		log.info("dto:" + JSONObject.toJSONString(dto, true));
		userLocationService.save(dto);
		return "OK";
	}
}
