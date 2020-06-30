package com.changhong.sei.report.expression.model.expr;

import com.changhong.sei.report.builds.Context;
import com.changhong.sei.report.expression.model.data.ExpressionData;
import com.changhong.sei.report.expression.model.data.ObjectExpressionData;
import com.changhong.sei.report.model.Cell;

import java.math.BigDecimal;

/**
 * @desc：数字表达式
 * @author：zhaohz
 * @date：2020/6/30 14:55
 */
public class NumberExpression extends BaseExpression {
	private static final long serialVersionUID = 1694636614530741241L;
	private BigDecimal value;
	public NumberExpression(BigDecimal value) {
		this.value=value;
	}
	@Override
	public ExpressionData<?> compute(Cell cell, Cell currentCell, Context context) {
		return new ObjectExpressionData(value.floatValue());
	}
}
