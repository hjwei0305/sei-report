package com.changhong.sei.report.image;

import com.changhong.sei.report.exception.ReportComputeException;
import com.changhong.sei.report.provider.image.ImageProvider;
import com.changhong.sei.report.utils.Utils;
import org.springframework.context.ApplicationContext;

import java.io.IOException;
import java.io.InputStream;
import java.util.Collection;
import java.util.logging.Logger;

/**
 * @desc：静态图片程序
 * @author：zhaohz
 * @date：2020/6/30 16:12
 */
public class StaticImageProcessor implements ImageProcessor<String> {
	private Logger log=Logger.getGlobal();
	@Override
	public InputStream getImage(String path) {
		Collection<ImageProvider> imageProviders= Utils.getImageProviders();
		ImageProvider targetImageProvider=null;
		for(ImageProvider provider:imageProviders){
			if(provider.support(path)){
				targetImageProvider=provider;
				break;
			}
		}
		if(targetImageProvider==null){
			throw new ReportComputeException("Unsupport image path :"+path);
		}
		try{
			InputStream inputStream=targetImageProvider.getImage(path);
			return inputStream;			
		}catch(Exception ex){
			ApplicationContext applicationContext= Utils.getApplicationContext();
			log.warning("Image ["+path+"] not exist,use default picture.");
			String imageNotExistPath="classpath:com/bstek/ureport/image/image-not-exist.jpg";
			try {
				return applicationContext.getResource(imageNotExistPath).getInputStream();
			} catch (IOException e1) {
				throw new ReportComputeException(e1);
			}
		}
	}
}
