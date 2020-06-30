package com.changhong.sei.report.expression.model.expr;

import com.changhong.sei.report.builds.Context;
import com.changhong.sei.report.expression.model.data.ExpressionData;
import com.changhong.sei.report.expression.model.data.ObjectExpressionData;
import com.changhong.sei.report.model.Cell;

/**
 * @desc：布尔表达式
 * @author：zhaohz
 * @date：2020/6/30 13:48
 */
public class BooleanExpression extends BaseExpression {
	private static final long serialVersionUID = -7372409829479132080L;
	private Boolean value;
	public BooleanExpression(Boolean value) {
		this.value=value;
	}
	@Override
	public ExpressionData<?> compute(Cell cell, Cell currentCell, Context context) {
		return new ObjectExpressionData(value);
	}
}
