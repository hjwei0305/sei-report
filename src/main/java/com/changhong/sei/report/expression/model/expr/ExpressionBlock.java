package com.changhong.sei.report.expression.model.expr;

import com.changhong.sei.report.builds.Context;
import com.changhong.sei.report.expression.model.Expression;
import com.changhong.sei.report.expression.model.data.ExpressionData;
import com.changhong.sei.report.model.Cell;

import java.util.List;

/**
 * @desc：表达式块
 * @author：zhaohz
 * @date：2020/6/30 13:56
 */
public class ExpressionBlock extends BaseExpression {
	private static final long serialVersionUID = -400528304334443664L;
	private List<Expression> expressionList;
	private Expression returnExpression;
	@Override
	protected ExpressionData<?> compute(Cell cell, Cell currentCell, Context context) {
		ExpressionData<?> data=null;
		if(expressionList!=null){
			for(Expression expr:expressionList){
				data=expr.execute(cell, currentCell, context);
			}
		}
		if(returnExpression!=null){
			data=returnExpression.execute(cell, currentCell, context);
		}
		return data;
	}
	public List<Expression> getExpressionList() {
		return expressionList;
	}
	public void setExpressionList(List<Expression> expressionList) {
		this.expressionList = expressionList;
	}
	public Expression getReturnExpression() {
		return returnExpression;
	}
	public void setReturnExpression(Expression returnExpression) {
		this.returnExpression = returnExpression;
	}
}
