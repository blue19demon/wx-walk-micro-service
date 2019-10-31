package com.wx.external.core.provider;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Base64.Decoder;
import java.util.Collections;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.web.client.RestTemplate;

import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.fastjson.JSONObject;
import com.wx.api.external.BaiduMapApiFacade;
import com.wx.api.external.resp.BaiduPlaceResp;
import com.wx.api.external.resp.UserLocationResp;
import com.wx.external.core.config.disconf.AppConfiguration;
import com.wx.external.core.config.disconf.BaiduApiConfiguration;

@Service
public class BaiduMapApiProvider implements BaiduMapApiFacade{
	@Autowired
	private BaiduApiConfiguration baiduApiConfiguration;
	@Autowired
	private AppConfiguration appConfiguration;
	@Autowired
	private RestTemplate restTemplate;
	/**
	 * 圆形区域检索
	 * 
	 * @param query 检索关键词
	 * @param lng 经度
	 * @param lat 纬度
	 * @return List<BaiduPlace>
	 * @throws UnsupportedEncodingException
	 */
	@Override
	public List<BaiduPlaceResp> searchPlace(String query, String lng, String lat) {
		// 拼装请求地址
		String api = String.format(baiduApiConfiguration.getMapUrlPlaceSearch(),query,lat+","+lng, baiduApiConfiguration.getMapAk());
		// 调用Place API圆形区域检索
		String respXml = httpRequest(api);
		// 解析返回的xml
		List<BaiduPlaceResp> placeList = parsePlaceXml(respXml);
		return placeList;
	}

	/**
	 * 发送http请求
	 * 
	 * @param requestUrl 请求地址
	 * @return String
	 */
	public String httpRequest(String requestUrl) {
		return restTemplate.exchange(requestUrl, HttpMethod.GET, null, String.class).getBody();
	}

	/**
	 * 根据百度地图返回的流解析出地址信息
	 * 
	 * @param inputStream 输入流
	 * @return List<BaiduPlace>
	 */
	private  List<BaiduPlaceResp> parsePlaceXml(String xml) {
		List<BaiduPlaceResp> placeList = null;
		try {
			Document document = DocumentHelper.parseText(xml);
			// 得到xml根元素
			Element root = document.getRootElement();
			// 从根元素获取<results>
			Element resultsElement = root.element("results");
			// 从<results>中获取<result>集合
			List<Element> resultElementList = resultsElement.elements("result");
			// 判断<result>集合的大小
			if (resultElementList.size() > 0) {
				placeList = new ArrayList<BaiduPlaceResp>();
				// POI名称
				Element nameElement = null;
				// POI地址信息
				Element addressElement = null;
				// POI经纬度坐标
				Element locationElement = null;
				// POI电话信息
				Element telephoneElement = null;
				// POI扩展信息
				Element detailInfoElement = null;
				// 距离中心点的距离
				Element distanceElement = null;
				// 遍历<result>集合
				for (Element resultElement : resultElementList) {
					nameElement = resultElement.element("name");
					addressElement = resultElement.element("address");
					locationElement = resultElement.element("location");
					telephoneElement = resultElement.element("telephone");
					detailInfoElement = resultElement.element("detail_info");

					BaiduPlaceResp place = new BaiduPlaceResp();
					place.setName(nameElement.getText());
					place.setAddress(addressElement.getText());
					place.setLng(locationElement.element("lng").getText());
					place.setLat(locationElement.element("lat").getText());
					// 当<telephone>元素存在时获取电话号码
					if (null != telephoneElement)
						place.setTelephone(telephoneElement.getText());
					// 当<detail_info>元素存在时获取<distance>
					if (null != detailInfoElement) {
						distanceElement = detailInfoElement.element("distance");
						if (null != distanceElement)
							place.setDistance(Integer.parseInt(distanceElement.getText()));
					}
					placeList.add(place);
				}
				// 按距离由近及远排序
				Collections.sort(placeList);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return placeList;
	}

	/**
	 * 根据Place组装图文列表
	 * 
	 * @param placeList
	 * @param bd09Lng 经度
	 * @param bd09Lat 纬度
	 * @return List<Article>
	 */
	@Override
	public String makeArticleList(List<BaiduPlaceResp> placeList, String bd09Lng, String bd09Lat) {
		// 项目的根路径
		String basePath = appConfiguration.getServerUrl();
		BaiduPlaceResp place = null;
		StringBuffer buffer = new StringBuffer();
		for (int i = 0; i < placeList.size(); i++) {
			place = placeList.get(i);
			buffer.append(place.getName() + "\n");
			String route = String.format(basePath + "/route?p1=%s,%s&p2=%s,%s", bd09Lng, bd09Lat, place.getLng(), place.getLat());
			buffer.append("<a href='"+route+"'>点击导航</a>").append("\n");
		}
		return buffer.toString();
	}

	/**
	 * 将微信定位的坐标转换成百度坐标（GCJ-02 -> Baidu）
	 * 
	 * @param lng 经度
	 * @param lat 纬度
	 * @return UserLocation
	 */
	@Override
	public UserLocationResp convertCoord(String lng, String lat) {
		// 百度坐标转换接口
		String api = String.format(baiduApiConfiguration.getMapConvertUrl(),lng, lat);
		UserLocationResp location = new UserLocationResp();
		try {
			String jsonCoord = httpRequest(api);
			JSONObject jsonObject = JSONObject.parseObject(jsonCoord);
			// 对转换后的坐标进行Base64解码
			Decoder decoder = Base64.getDecoder();
			location.setBd09Lng(new String(decoder.decode(jsonObject.getString("x").getBytes())));
			location.setBd09Lat(new String(decoder.decode(jsonObject.getString("y").getBytes())));
		} catch (Exception e) {
			location = null;
			e.printStackTrace();
		}
		return location;
	}
	
	/**
	 * 获取位置
	 * 
	 * @param lng 经度
	 * @param lat 纬度
	 * @return UserLocation
	 */
	@Override
	public String getLocation(String mjkd_latlng) {
		// 百度位置检索
		String api = String.format(baiduApiConfiguration.getMapUrlGetLocation(),mjkd_latlng, baiduApiConfiguration.getMapAk());
		try {
			String jsonCoord = httpRequest(api);
			String formatted_address =JSONObject.parseObject(jsonCoord).getJSONObject("result").getString("formatted_address");
			return formatted_address;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "未知地";
	}

}
