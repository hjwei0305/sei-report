package com.changhong.sei.report.expression.model.expr;

import com.changhong.sei.report.build.Context;
import com.changhong.sei.report.expression.model.data.ExpressionData;
import com.changhong.sei.report.expression.model.data.ObjectExpressionData;
import com.changhong.sei.report.model.Cell;

/**
 * @desc：整数表达式
 * @author：zhaohz
 * @date：2020/6/30 14:51
 */
public class IntegerExpression extends BaseExpression {
	private static final long serialVersionUID = -8903608366537307519L;
	private Integer value;
	public IntegerExpression(Integer value) {
		this.value=value;
	}
	@Override
	public ExpressionData<?> compute(Cell cell, Cell currentCell, Context context) {
		return new ObjectExpressionData(value);
	}
}
