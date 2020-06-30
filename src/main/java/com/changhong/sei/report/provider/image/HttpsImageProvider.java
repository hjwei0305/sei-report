package com.changhong.sei.report.provider.image;

import com.changhong.sei.report.exception.ReportException;

import javax.net.ssl.HttpsURLConnection;
import java.io.InputStream;
import java.net.URL;

/**
 * @desc：https图片来源
 * @author：zhaohz
 * @date：2020/6/30 16:14
 */
public class HttpsImageProvider implements ImageProvider {

	@Override
	public InputStream getImage(String path) {
		try{
			URL url=new URL(path);
			HttpsURLConnection connection=(HttpsURLConnection)url.openConnection();
			connection.connect();
			InputStream inputStream=connection.getInputStream();
			return inputStream;
		}catch(Exception ex){
			throw new ReportException(ex);
		}
	}

	@Override
	public boolean support(String path) {
		return path.startsWith("https:");
	}

}
