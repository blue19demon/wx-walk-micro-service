package com.wx.external.core.provider;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.binarywang.utils.qrcode.QrcodeUtils;
import com.wx.api.external.QRCodeFacade;
import com.wx.external.core.config.disconf.AppConfiguration;
import com.wx.external.core.utils.FileDownloadUtils;

import me.chanjar.weixin.common.api.WxConsts;
import me.chanjar.weixin.common.bean.result.WxMediaUploadResult;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpService;

@Service
public class QRCodeProvider implements QRCodeFacade{
	@Autowired
	private WxMpService wxMpService;
	@Autowired
	private AppConfiguration appConfiguration;
	
	@Override
	public String QRCodeCreate(String openId, String headImageUrl) {
		FileOutputStream fos = null;
		try {
			File dir=new File(this.appConfiguration.getUploadFolder());
			if(!dir.exists()) {
				dir.mkdir();
			}
			File logoFile=new File(this.appConfiguration.getUploadFolder()+File.separator+openId+".jpg");
			FileDownloadUtils.download(headImageUrl,logoFile);
			byte[] content = QrcodeUtils.createQrcode(appConfiguration.getServerUrl() + "/authorize", logoFile);
			File outFile = new File(this.appConfiguration.getUploadFolder()+File.separator+openId+"_logo.jpg");
			fos = new FileOutputStream(outFile);
			InputStream ips = new ByteArrayInputStream(content);
			IOUtils.copy(ips, fos);
			WxMediaUploadResult res = wxMpService.getMaterialService().mediaUpload(WxConsts.MaterialType.IMAGE,
					outFile);
			return res.getMediaId();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (WxErrorException e) {
			e.printStackTrace();
		} finally {
			if (fos != null) {
				try {
					fos.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return headImageUrl;
	}

}
