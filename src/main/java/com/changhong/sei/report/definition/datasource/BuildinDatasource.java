package com.changhong.sei.report.definition.datasource;

import java.sql.Connection;

/**
 * @desc：内主数据源接口
 * @author：zhaohz
 * @date：2020/6/30 17:51
 */
public interface BuildinDatasource {
	/**
	 * @return 返回数据源名称
	 */
	String name();
	/**
	 * @return 返回当前采用数据源的一个连接
	 */
	Connection getConnection();
}
