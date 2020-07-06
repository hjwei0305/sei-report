package com.changhong.sei.report.expression.function.string;

import com.changhong.sei.report.builds.Context;
import com.changhong.sei.report.expression.model.data.ExpressionData;
import com.changhong.sei.report.model.Cell;

import java.util.List;

/**
 * @desc：长度函数
 * @author：zhaohz
 * @date：2020/7/6 9:41
 */
public class LengthFunction extends StringFunction {

	@Override
	public Object execute(List<ExpressionData<?>> dataList, Context context, Cell currentCell) {
		String text=buildString(dataList);
		return text.length();
	}

	@Override
	public String name() {
		return "length";
	}
}
