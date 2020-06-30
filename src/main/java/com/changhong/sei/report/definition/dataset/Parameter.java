package com.changhong.sei.report.definition.dataset;

import com.changhong.sei.report.definition.datasource.DataType;

/**
 * @desc：数据及参数
 * @author：zhaohz
 * @date：2020/6/30 13:40
 */
public class Parameter {
	private String name;
	private DataType type;
	private String defaultValue;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public DataType getType() {
		return type;
	}
	public void setType(DataType type) {
		this.type = type;
	}
	public String getDefaultValue() {
		return defaultValue;
	}
	public void setDefaultValue(String defaultValue) {
		this.defaultValue = defaultValue;
	}
}
