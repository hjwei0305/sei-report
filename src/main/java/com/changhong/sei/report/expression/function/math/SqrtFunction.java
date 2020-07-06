package com.changhong.sei.report.expression.function.math;

import com.changhong.sei.report.builds.Context;
import com.changhong.sei.report.expression.model.data.ExpressionData;
import com.changhong.sei.report.model.Cell;

import java.math.BigDecimal;
import java.util.List;

/**
 * @desc：平方根
 * @author：zhaohz
 * @date：2020/7/6 9:33
 */
public class SqrtFunction extends MathFunction {

	@Override
	public Object execute(List<ExpressionData<?>> dataList, Context context, Cell currentCell) {
		BigDecimal data=buildBigDecimal(dataList);
		return Math.sqrt(data.doubleValue());
	}

	@Override
	public String name() {
		return "sqrt";
	}
}
