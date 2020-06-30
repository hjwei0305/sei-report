package com.changhong.sei.report.expression.model.expr;

import com.changhong.sei.report.build.Context;
import com.changhong.sei.report.expression.model.data.ExpressionData;
import com.changhong.sei.report.expression.model.data.NoneExpressionData;
import com.changhong.sei.report.model.Cell;

/**
 * @desc：空表达式
 * @author：zhaohz
 * @date：2020/6/30 14:55
 */
public class NullExpression extends BaseExpression {
	private static final long serialVersionUID = -5448531052217619991L;

	@Override
	public ExpressionData<?> compute(Cell cell, Cell currentCell, Context context) {
		return new NoneExpressionData();
	}
}
