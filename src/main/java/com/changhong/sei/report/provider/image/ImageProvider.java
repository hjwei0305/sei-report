package com.changhong.sei.report.provider.image;

import java.io.InputStream;

/**
 * @desc：图片来源接口
 * @author：zhaohz
 * @date：2020/6/30 16:14
 */
public interface ImageProvider {
	InputStream getImage(String path);
	boolean support(String path);
}
