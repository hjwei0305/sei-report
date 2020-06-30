package com.changhong.sei.report.expression.model.expr;

import com.changhong.sei.report.builds.Context;
import com.changhong.sei.report.expression.model.data.ExpressionData;
import com.changhong.sei.report.expression.model.data.ObjectExpressionData;
import com.changhong.sei.report.model.Cell;

/**
 * @desc：字符串表达式
 * @author：zhaohz
 * @date：2020/6/30 14:58
 */
public class StringExpression extends BaseExpression {
	private static final long serialVersionUID = 4810887743258516630L;
	private String text;
	public StringExpression(String text) {
		this.text=text;
	}
	
	@Override
	protected ExpressionData<?> compute(Cell cell, Cell currentCell, Context context) {
		return new ObjectExpressionData(text);
	}
}
