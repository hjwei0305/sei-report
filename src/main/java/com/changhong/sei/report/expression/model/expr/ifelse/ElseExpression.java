package com.changhong.sei.report.expression.model.expr.ifelse;

import com.changhong.sei.report.builds.Context;
import com.changhong.sei.report.expression.model.data.ExpressionData;
import com.changhong.sei.report.expression.model.expr.BaseExpression;
import com.changhong.sei.report.expression.model.expr.ExpressionBlock;
import com.changhong.sei.report.model.Cell;

/**
 * @desc：else表达式
 * @author：zhaohz
 * @date：2020/6/30 15:31
 */
public class ElseExpression extends BaseExpression {
	private static final long serialVersionUID = 7686136494993309779L;
	private ExpressionBlock expression;
	@Override
	protected ExpressionData<?> compute(Cell cell, Cell currentCell, Context context) {
		return expression.execute(cell, currentCell,context);
	}
	public ExpressionBlock getExpression() {
		return expression;
	}
	public void setExpression(ExpressionBlock expression) {
		this.expression = expression;
	}
}
