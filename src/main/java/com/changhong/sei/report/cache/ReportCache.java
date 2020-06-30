package com.changhong.sei.report.cache;

/**
 * @desc：报表缓存接口
 * @author：zhaohz
 * @date：2020/6/30 16:20
 */
public interface ReportCache {
	Object getObject(String file);
	void storeObject(String file, Object obj);
	boolean disabled();
}
