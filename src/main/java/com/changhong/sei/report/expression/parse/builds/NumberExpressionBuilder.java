package com.changhong.sei.report.expression.parse.builds;

import com.changhong.sei.report.dsl.ReportParserParser;
import com.changhong.sei.report.expression.model.expr.BaseExpression;
import com.changhong.sei.report.expression.model.expr.NumberExpression;
import com.changhong.sei.report.utils.Utils;

import java.math.BigDecimal;

/**
 * @desc：数字表达式构造器
 * @author：zhaohz
 * @date：2020/6/30 17:25
 */
public class NumberExpressionBuilder implements ExpressionBuilder {
	@Override
	public BaseExpression build(ReportParserParser.UnitContext unitContext) {
		BigDecimal value= Utils.toBigDecimal(unitContext.NUMBER().getText());
		return new NumberExpression(value);
	}

	@Override
	public boolean support(ReportParserParser.UnitContext unitContext) {
		return unitContext.NUMBER()!=null;
	}
}
