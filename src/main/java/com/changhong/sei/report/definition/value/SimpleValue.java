package com.changhong.sei.report.definition.value;

import com.changhong.sei.report.enums.ValueType;

/**
 * @desc：普通字符串
 * @author：zhaohz
 * @date：2020/6/30 9:33
 */
public class SimpleValue implements Value {
	private String value;
	public SimpleValue(String value) {
		this.value=value;
	}
	
	@Override
	public ValueType getType() {
		return ValueType.simple;
	}

	@Override
	public String getValue() {
		return value;
	}
}
