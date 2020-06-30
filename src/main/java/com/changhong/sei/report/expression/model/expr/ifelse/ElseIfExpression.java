package com.changhong.sei.report.expression.model.expr.ifelse;

import com.changhong.sei.report.build.Context;
import com.changhong.sei.report.expression.model.data.ExpressionData;
import com.changhong.sei.report.expression.model.expr.BaseExpression;
import com.changhong.sei.report.expression.model.expr.ExpressionBlock;
import com.changhong.sei.report.model.Cell;

/**
 * @desc：elseif表达式
 * @author：zhaohz
 * @date：2020/6/30 15:32
 */
public class ElseIfExpression extends BaseExpression {
	private static final long serialVersionUID = -198920923804292977L;
	private ExpressionConditionList conditionList;
	
	private ExpressionBlock expression;
	@Override
	protected ExpressionData<?> compute(Cell cell, Cell currentCell, Context context) {
		return expression.execute(cell, currentCell,context);
	}
	public boolean conditionsEval(Cell cell, Cell currentCell, Context context) {
		return conditionList.eval(context, cell,currentCell);
	}
	public void setConditionList(ExpressionConditionList conditionList) {
		this.conditionList = conditionList;
	}
	public ExpressionBlock getExpression() {
		return expression;
	}
	public void setExpression(ExpressionBlock expression) {
		this.expression = expression;
	}
}
