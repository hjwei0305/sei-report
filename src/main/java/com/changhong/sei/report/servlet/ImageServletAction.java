package com.changhong.sei.report.servlet;

import com.changhong.sei.report.cache.ResourceCache;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * @desc：图片
 * @author：zhaohz
 * @date：2020/7/6 17:31
 */
public class ImageServletAction implements ServletAction {
	public static final String URL="/image";
	@Override
	public void execute(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String key=req.getParameter("_key");
		if(StringUtils.isNotBlank(key)){
			byte[] bytes=(byte[]) ResourceCache.getObject(key);

			try (InputStream input=new ByteArrayInputStream(bytes);
				 OutputStream output=resp.getOutputStream()) {
				resp.setContentType("image/png");
				IOUtils.copy(input, output);
			}
		}else{
			//processImage(req, resp);			
		}
	}

	@Override
	public String url() {
		return URL;
	}
}
