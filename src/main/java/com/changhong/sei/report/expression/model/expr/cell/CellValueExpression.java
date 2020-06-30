package com.changhong.sei.report.expression.model.expr.cell;

import com.changhong.sei.report.builds.Context;
import com.changhong.sei.report.expression.model.data.ExpressionData;
import com.changhong.sei.report.expression.model.data.ObjectExpressionData;
import com.changhong.sei.report.expression.model.expr.BaseExpression;
import com.changhong.sei.report.model.Cell;

/**
 * @desc：单元格值表达式
 * @author：zhaohz
 * @date：2020/6/30 15:39
 */
public class CellValueExpression extends BaseExpression {
	private static final long serialVersionUID = 5964924636009364350L;

	@Override
	protected ExpressionData<?> compute(Cell cell, Cell currentCell, Context context) {
		while(!context.isCellPocessed(cell.getName())){
			context.getReportBuilder().buildCell(context, null);
		}
		return new ObjectExpressionData(cell.getData());
	}
}
