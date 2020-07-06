package com.changhong.sei.report.expression.function.string;

import com.changhong.sei.report.builds.Context;
import com.changhong.sei.report.expression.model.data.ExpressionData;
import com.changhong.sei.report.model.Cell;

import java.util.List;

/**
 * @desc：去收尾空格
 * @author：zhaohz
 * @date：2020/7/6 9:42
 */
public class TrimFunction extends StringFunction {
	@Override
	public Object execute(List<ExpressionData<?>> dataList, Context context, Cell currentCell) {
		String text=buildString(dataList);
		return text.trim();
	}

	@Override
	public String name() {
		return "trim";
	}
}
