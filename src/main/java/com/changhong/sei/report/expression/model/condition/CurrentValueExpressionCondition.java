package com.changhong.sei.report.expression.model.condition;

import com.changhong.sei.report.build.Context;
import com.changhong.sei.report.expression.model.Expression;
import com.changhong.sei.report.expression.model.data.ExpressionData;
import com.changhong.sei.report.model.Cell;
import org.codehaus.jackson.annotate.JsonIgnore;

/**
 * @desc：当前值条件表达式
 * @author：zhaohz
 * @date：2020/6/30 15:24
 */
public class CurrentValueExpressionCondition extends BaseCondition {
	private ConditionType type= ConditionType.current;
	@JsonIgnore
	private Expression rightExpression;
	@Override
	Object computeLeft(Cell cell, Cell currentCell, Object obj, Context context) {
		return obj;
	}

	@Override
	Object computeRight(Cell cell, Cell currentCell, Object obj, Context context) {
		ExpressionData<?> exprData=rightExpression.execute(cell, currentCell,context);
		return extractExpressionData(exprData);
	}
	
	@Override
	public ConditionType getType() {
		return type;
	}
	public void setRightExpression(Expression rightExpression) {
		this.rightExpression = rightExpression;
	}
	public Expression getRightExpression() {
		return rightExpression;
	}
}
