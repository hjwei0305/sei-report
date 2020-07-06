package com.changhong.sei.report.expression.function.math;

import com.changhong.sei.report.builds.Context;
import com.changhong.sei.report.expression.model.data.ExpressionData;
import com.changhong.sei.report.model.Cell;

import java.math.BigDecimal;
import java.util.List;

/**
 * @desc：返回以10为底的对数值
 * @author：zhaohz
 * @date：2020/7/6 9:15
 */
public class Log10Function extends MathFunction {

	@Override
	public Object execute(List<ExpressionData<?>> dataList, Context context, Cell currentCell) {
		BigDecimal data=buildBigDecimal(dataList);
		return Math.log10(data.doubleValue());
	}

	@Override
	public String name() {
		return "log10";
	}
}
