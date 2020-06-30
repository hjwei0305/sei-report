package com.changhong.sei.report.chart.plugins;

/**
 * @desc：chart插件接口
 * @author：zhaohz
 * @date：2020/6/30 15:47
 */
public interface Plugin {
	String toJson(String type);
	String getName();
}
