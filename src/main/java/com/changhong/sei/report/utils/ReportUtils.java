package com.changhong.sei.report.utils;

import com.changhong.sei.report.exception.ReportDesignException;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

/**
 * @desc：报表工具类
 * @author：zhaohz
 * @date：2020/7/7 9:35
 */
public class ReportUtils {
	public static String decodeFileName(String fileName){
		if(fileName==null){
			return fileName;
		}
		try {
			fileName=URLDecoder.decode(fileName, "utf-8");
			fileName=URLDecoder.decode(fileName, "utf-8");
			return fileName;
		} catch (UnsupportedEncodingException e) {
			throw new ReportDesignException(e);
		}
	}
}
