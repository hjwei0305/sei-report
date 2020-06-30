package com.changhong.sei.report.cache;

import com.changhong.sei.report.definition.ReportDefinition;

/**
 * @desc：报表模板缓存接口
 * @author：zhaohz
 * @date：2020/6/30 16:21
 */
public interface ReportDefinitionCache {
	ReportDefinition getReportDefinition(String file);
	void cacheReportDefinition(String file, ReportDefinition reportDefinition);
}
