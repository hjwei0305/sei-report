package com.changhong.sei.report.definition.value;

import com.changhong.sei.report.enums.ValueType;

/**
 * @desc：单元格value接口
 * @author：zhaohz
 * @date：2020/6/30 9:21
 */
public interface Value {
	String getValue();
	ValueType getType();
}
