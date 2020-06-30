package com.changhong.sei.report.definition.datasource;

import com.changhong.sei.report.definition.dataset.DatasetDefinition;
import com.changhong.sei.report.enums.DatasourceType;

import java.util.List;

/**
 * @desc：数据源模板
 * @author：zhaohz
 * @date：2020/6/30 11:20
 */
public interface DatasourceDefinition {
	String getName();
	List<DatasetDefinition> getDatasets();
	DatasourceType getType();
}
