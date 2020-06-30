package com.changhong.sei.report.expression.parse.builds;

import com.changhong.sei.report.dsl.ReportParserParser;
import com.changhong.sei.report.expression.model.expr.BaseExpression;
import com.changhong.sei.report.expression.model.expr.NullExpression;

/**
 * @desc：空表达式构造器
 * @author：zhaohz
 * @date：2020/6/30 17:25
 */
public class NullExpressionBuilder implements ExpressionBuilder {
	@Override
	public BaseExpression build(ReportParserParser.UnitContext unitContext) {
		return new NullExpression();
	}

	@Override
	public boolean support(ReportParserParser.UnitContext unitContext) {
		return unitContext.NULL()!=null;
	}
}
