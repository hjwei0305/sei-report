package com.changhong.sei.report.model;

import com.changhong.sei.report.definition.CellStyle;
import com.changhong.sei.report.definition.value.Value;
import com.changhong.sei.report.enums.Expand;

import java.util.List;

/**
 * @desc：报表单元格接口
 * @author：zhaohz
 * @date：2020/6/28 14:54
 */
public interface ReportCell {
	CellStyle getCellStyle();
	String getName();
	int getRowSpan();
	int getColSpan();
	Row getRow();
	Column getColumn();
	Object getData();
	Value getValue();
	Expand getExpand();
	List<Object> getBindData();
}