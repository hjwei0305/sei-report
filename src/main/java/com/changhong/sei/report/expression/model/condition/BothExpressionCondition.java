package com.changhong.sei.report.expression.model.condition;

import com.changhong.sei.report.build.Context;
import com.changhong.sei.report.expression.model.Expression;
import com.changhong.sei.report.expression.model.data.ExpressionData;
import com.changhong.sei.report.model.Cell;
import org.codehaus.jackson.annotate.JsonIgnore;

/**
 * @desc：both条件表达式
 * @author：zhaohz
 * @date：2020/6/30 15:23
 */
public class BothExpressionCondition extends BaseCondition {
	private ConditionType type= ConditionType.expression;
	@JsonIgnore
	private Expression leftExpression;
	@JsonIgnore
	private Expression rightExpression;

	@Override
	Object computeLeft(Cell cell, Cell currentCell, Object obj, Context context) {
		ExpressionData<?> exprData = leftExpression.execute(cell,currentCell, context);
		return extractExpressionData(exprData);
	}

	@Override
	Object computeRight(Cell cell, Cell currentCell, Object obj, Context context) {
		ExpressionData<?> exprData = rightExpression.execute(cell,currentCell, context);
		return extractExpressionData(exprData);
	}
	
	
	@Override
	public ConditionType getType() {
		return type;
	}

	public void setLeftExpression(Expression leftExpression) {
		this.leftExpression = leftExpression;
	}

	public void setRightExpression(Expression rightExpression) {
		this.rightExpression = rightExpression;
	}
}
