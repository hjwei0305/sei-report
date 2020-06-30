package com.changhong.sei.report.expression.model.expr;

import com.changhong.sei.report.build.Context;
import com.changhong.sei.report.expression.model.data.ExpressionData;
import com.changhong.sei.report.expression.model.data.ObjectExpressionData;
import com.changhong.sei.report.model.Cell;

/**
 * @desc：变量表达式
 * @author：zhaohz
 * @date：2020/6/30 15:00
 */
public class VariableExpression extends BaseExpression {
	private static final long serialVersionUID = 4810887743258516630L;
	private String text;
	public VariableExpression(String text) {
		this.text=text;
	}
	
	@Override
	protected ExpressionData<?> compute(Cell cell, Cell currentCell, Context context) {
		Object obj=context.getVariable(text);
		return new ObjectExpressionData(obj);
	}
}
