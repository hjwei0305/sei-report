package com.changhong.sei.report.image;

import java.io.InputStream;

/**
 * @desc：图形程序接口
 * @author：zhaohz
 * @date：2020/6/30 16:11
 */
public interface ImageProcessor<T> {
	InputStream getImage(T data);
}
