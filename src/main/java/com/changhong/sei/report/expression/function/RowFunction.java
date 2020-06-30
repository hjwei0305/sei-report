package com.changhong.sei.report.expression.function;

import com.changhong.sei.report.build.Context;
import com.changhong.sei.report.expression.model.data.ExpressionData;
import com.changhong.sei.report.model.Cell;
import com.changhong.sei.report.model.Row;

import java.util.List;

/**
 * @desc：行号函数
 * @author：zhaohz
 * @date：2020/6/30 14:24
 */
public class RowFunction implements Function {
	@Override
	public Object execute(List<ExpressionData<?>> dataList, Context context, Cell currentCell) {
		Row row=currentCell.getRow();
		return row.getRowNumber();
	}
	@Override
	public String name() {
		return "row";
	}
}
