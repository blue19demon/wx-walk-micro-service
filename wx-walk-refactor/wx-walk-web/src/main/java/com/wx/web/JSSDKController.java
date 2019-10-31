package com.wx.web;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.dubbo.config.annotation.Reference;
import com.wx.api.external.BaiduMapApiFacade;
import com.wx.api.external.resp.UserLocationResp;

@Controller
public class JSSDKController {
	
	
	@Reference
	private BaiduMapApiFacade baiduMapApiFacade;

	
	@GetMapping("/route")
	public ModelAndView route(String p1,String p2) {
		Map<String,Object> model=new HashMap<String,Object>();
		model.put("p1", p1);
		model.put("p2", p2);
		return new ModelAndView("route", model);
	}
	
	
	@GetMapping("/mapLocation")
	@ResponseBody
	public Object mapLocation(String latitude,String longitude) {
		UserLocationResp userLocation = baiduMapApiFacade.convertCoord(longitude, latitude);
		// 坐标转换后的经纬度
		String bd09Lng = null;
		String bd09Lat = null;
		if (null != userLocation) {
			bd09Lng = userLocation.getBd09Lng();
			bd09Lat = userLocation.getBd09Lat();
		}
		return baiduMapApiFacade.getLocation(bd09Lat.concat(",").concat(bd09Lng));
	}
	
	@GetMapping("/getLocation")
	public String getLocation(HttpServletRequest request, HttpServletResponse response) {
		return "getLocation";
	}
	
	
}
