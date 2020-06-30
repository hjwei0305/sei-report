package com.changhong.sei.report.provider.image;

import com.changhong.sei.report.exception.ReportComputeException;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.util.ResourceUtils;
import org.springframework.web.context.WebApplicationContext;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * @desc：默认图片提供者
 * @author：zhaohz
 * @date：2020/6/30 16:13
 */
public class DefaultImageProvider implements ImageProvider, ApplicationContextAware {
	private ApplicationContext applicationContext;
	private String baseWebPath;
	@Override
	public InputStream getImage(String path) {
		try {
			if(path.startsWith(ResourceUtils.CLASSPATH_URL_PREFIX) || path.startsWith("/WEB-INF")){
				return applicationContext.getResource(path).getInputStream();				
			}else{
				path=baseWebPath+path;
				return new FileInputStream(path);
			}
		} catch (IOException e) {
			throw new ReportComputeException(e);
		}
	}

	@Override
	public boolean support(String path) {
		if(path.startsWith(ResourceUtils.CLASSPATH_URL_PREFIX)){
			return true;
		}else if(baseWebPath!=null && (path.startsWith("/") || path.startsWith("/WEB-INF"))){
			return true;
		}
		return false;
	}
	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		if(applicationContext instanceof WebApplicationContext){
			WebApplicationContext context=(WebApplicationContext)applicationContext;
			baseWebPath=context.getServletContext().getRealPath("/");
		}
		this.applicationContext=applicationContext;
	}
}
