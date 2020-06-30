package com.changhong.sei.report.cache;

import com.changhong.sei.report.definition.ReportDefinition;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @desc：默认缓存报表模板
 * @author：zhaohz
 * @date：2020/6/30 16:21
 */
public class DefaultMemoryReportDefinitionCache implements ReportDefinitionCache {
	private Map<String, ReportDefinition> reportMap=new ConcurrentHashMap<String, ReportDefinition>();
	@Override
	public ReportDefinition getReportDefinition(String file) {
		return reportMap.get(file);
	}
	@Override
	public void cacheReportDefinition(String file, ReportDefinition reportDefinition) {
		if(reportMap.containsKey(file)){
			reportMap.remove(file);
		}
		reportMap.put(file, reportDefinition);
	}
}
