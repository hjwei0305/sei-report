package com.changhong.sei.report.expression.parse.builds;

import com.changhong.sei.report.dsl.ReportParserParser;
import com.changhong.sei.report.expression.model.expr.BaseExpression;
import com.changhong.sei.report.expression.model.expr.VariableExpression;

/**
 * @desc：变量表达式构造器
 * @author：zhaohz
 * @date：2020/6/30 17:31
 */
public class VariableExpressionBuilder implements ExpressionBuilder {
	@Override
	public BaseExpression build(ReportParserParser.UnitContext unitContext) {
		String text=unitContext.variable().Identifier().getText();
		VariableExpression varExpr=new VariableExpression(text);
		return varExpr;
	}
	@Override
	public boolean support(ReportParserParser.UnitContext unitContext) {
		return unitContext.variable()!=null;
	}
}
