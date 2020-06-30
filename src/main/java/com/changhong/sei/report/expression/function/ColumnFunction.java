package com.changhong.sei.report.expression.function;

import com.changhong.sei.report.builds.Context;
import com.changhong.sei.report.expression.model.data.ExpressionData;
import com.changhong.sei.report.model.Cell;
import com.changhong.sei.report.model.Column;

import java.util.List;

/**
 * @desc：列号函数
 * @author：zhaohz
 * @date：2020/6/30 14:14
 */
public class ColumnFunction implements Function {
	@Override
	public Object execute(List<ExpressionData<?>> dataList, Context context, Cell currentCell) {
		Column col=currentCell.getColumn();
		return col.getColumnNumber();
	}
	@Override
	public String name() {
		return "column";
	}
}
