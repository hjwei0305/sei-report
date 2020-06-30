package com.changhong.sei.report.expression.model.expr;

import com.changhong.sei.report.builds.Context;
import com.changhong.sei.report.expression.model.data.ExpressionData;
import com.changhong.sei.report.expression.model.data.ObjectExpressionData;
import com.changhong.sei.report.model.Cell;

/**
 * @desc：当前单元格值表达式
 * @author：zhaohz
 * @date：2020/6/30 13:54
 */
public class CurrentCellValueExpression extends BaseExpression {
	private static final long serialVersionUID = -653158121297142855L;

	@Override
	protected ExpressionData<?> compute(Cell cell, Cell currentCell, Context context) {
		return new ObjectExpressionData(cell.getData());
	}
}
