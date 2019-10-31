package com.wx.web;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSONObject;
import com.wx.api.external.PlatformAPIFacade;

import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.bean.WxJsapiSignature;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpService;

@Controller
@Slf4j
public class ShareController {
	
	@Autowired
	private WxMpService wxMpService;

	@Reference
	private PlatformAPIFacade platformAPIFacade;
	@GetMapping("/index")
	public ModelAndView index() {
		Map<String,Object> model=new HashMap<String,Object>();
		model.put("serverUrl", platformAPIFacade.getServerUrl());
		return new ModelAndView("index", model);
	}
	
	@GetMapping("/card")
	public String card(HttpServletRequest request, HttpServletResponse response) {
		return "card";
	}
	
	@PostMapping("/wxConfig")
	@ResponseBody
	public WxJsapiSignature wxConfig(HttpServletRequest request, HttpServletResponse response) {
		try {
			WxJsapiSignature wxJsapiSignature = wxMpService.createJsapiSignature(request.getParameter("url"));
			log.info("WxJsapiSignature=" + JSONObject.toJSONString(wxJsapiSignature,true));
			return wxJsapiSignature;
		} catch (WxErrorException e) {
			e.printStackTrace();
		}
		return null;
	}
}
