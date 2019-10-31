
package com.wx.external.core.provider;

import java.io.File;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.web.client.RestTemplate;

import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.wx.api.external.VedioApiFacade;
import com.wx.api.external.enums.VedioAction;
import com.wx.external.core.config.disconf.AppConfiguration;
import com.wx.external.core.config.disconf.PlatformAPIConfig;
import com.wx.external.core.utils.FileDownloadUtils;
import com.wx.external.core.utils.RandomArray;

import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.api.WxConsts;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.message.WxMpXmlOutMessage;

@Service
@Slf4j
public class VedioApiprovider implements VedioApiFacade{
	@Autowired
	private PlatformAPIConfig platformAPIConfig;
	@Autowired
	private RestTemplate restTemplate;
	@Autowired
	private WxMpService wxMpService;
	@Autowired
	private AppConfiguration appConfiguration;
	@Override
	public String search(String fromUserName, String toUserName, VedioAction vedioAction) {
		try {
			log.info("传入的内容->" + vedioAction);
			String apiUrl = String.format(platformAPIConfig.getVedioSearchAPI());
			String result = restTemplate.exchange(apiUrl, HttpMethod.GET, null, String.class).getBody();
			log.info("result->" + result);
			JSONObject json = JSONObject.parseObject(result);
			if(json.getInteger("code")==200) {
				JSONArray resultArray = json.getJSONArray("result");
				if(VedioAction.ONE == vedioAction) {
					int index=(int)(Math.random()*resultArray.size());
					JSONObject videoJson = resultArray.getJSONObject(index);
					String video = videoJson.getString("video");
					File videoFile=new File(this.appConfiguration.getUploadFolder()+File.separator+"vedio_"+videoJson.getString("sid")+".mp4");
					FileDownloadUtils.download(video,videoFile);
					String thumbMediaId=wxMpService.getMaterialService().mediaUpload(WxConsts.MaterialType.VIDEO,
							videoFile).getMediaId();
					return WxMpXmlOutMessage.VIDEO()
							.fromUser(toUserName)
							.toUser(fromUserName)
							.title(videoJson.getString("text"))
							.description(videoJson.getString("name"))
							.mediaId(thumbMediaId)
							.build()
							.toXml();
				}else if(VedioAction.ALL == vedioAction){
					JSONArray data = RandomArray.createRandomList(resultArray,5);
					StringBuffer buffer = new StringBuffer();
					for (int i = 0; i < data.size(); i++) {
						JSONObject item = data.getJSONObject(i);
						buffer.append("<a href='"+item.getString("video")+"'>"+item.getString("text")+"</a>").append("\n");
						buffer.append(item.getString("name")+"("+item.getString("passtime")+")").append("\n");
						if (i != data.size() - 1) {
							buffer.append("----------------------------\n");
						}
					}
					return WxMpXmlOutMessage
							.TEXT()
							.content(buffer.toString())
							.fromUser(toUserName)
							.toUser(fromUserName)
							.build()
							.toXml();
				}else {
					throw new IllegalArgumentException("keyWord参数错误");
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return WxMpXmlOutMessage
				.TEXT()
				.content("对不起，出错了")
				.fromUser(toUserName)
				.toUser(fromUserName)
				.build()
				.toXml();
	}

	@Override
	public JSONObject satin() {
		try {
			String apiUrl = String.format(platformAPIConfig.getSatinApi());
			String result = restTemplate.exchange(apiUrl, HttpMethod.GET, null, String.class).getBody();
			log.info("result->" + result);
			JSONObject json = JSONObject.parseObject(result);
			if(json.getInteger("code")==200) {
				JSONArray resultArray = json.getJSONArray("data");
				int index=(int)(Math.random()*resultArray.size());
				return resultArray.getJSONObject(index);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}
