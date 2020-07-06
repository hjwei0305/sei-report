package com.changhong.sei.report.expression.model.expr.set;

import com.changhong.sei.report.builds.Context;
import com.changhong.sei.report.expression.model.data.ExpressionData;
import com.changhong.sei.report.expression.model.data.ObjectExpressionData;
import com.changhong.sei.report.expression.model.expr.BaseExpression;
import com.changhong.sei.report.model.Cell;

/**
 * @desc：
 * @author：zhaohz
 * @date：2020/7/1 14:27
 */
public class SimpleValueSetExpression extends BaseExpression {
	private static final long serialVersionUID = -5433811018086391838L;
	private Object simpleValue;
	public SimpleValueSetExpression(Object simpleValue) {
		this.simpleValue=simpleValue;
	}
	@Override
	protected ExpressionData<?> compute(Cell cell, Cell currentCell, Context context) {
		return new ObjectExpressionData(simpleValue);
	}
}
