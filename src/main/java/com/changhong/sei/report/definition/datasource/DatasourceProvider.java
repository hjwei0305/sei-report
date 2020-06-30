package com.changhong.sei.report.definition.datasource;

import java.sql.Connection;

/**
 * @desc：数据源提供者接口
 * @author：zhaohz
 * @date：2020/6/30 10:26
 */
public interface DatasourceProvider {
	Connection getConnection();
	String getName();
}
