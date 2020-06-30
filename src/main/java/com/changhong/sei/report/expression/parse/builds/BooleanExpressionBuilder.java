package com.changhong.sei.report.expression.parse.builds;

import com.changhong.sei.report.dsl.ReportParserParser;
import com.changhong.sei.report.expression.model.expr.BaseExpression;
import com.changhong.sei.report.expression.model.expr.BooleanExpression;

/**
 * @desc：布尔表达式构造器
 * @author：zhaohz
 * @date：2020/6/30 15:37
 */
public class BooleanExpressionBuilder implements ExpressionBuilder {
	@Override
	public BaseExpression build(ReportParserParser.UnitContext unitContext) {
		String text=unitContext.BOOLEAN().getText();
		return new BooleanExpression(Boolean.valueOf(text));
	}

	@Override
	public boolean support(ReportParserParser.UnitContext unitContext) {
		return unitContext.BOOLEAN()!=null;
	}
}
