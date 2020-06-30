package com.changhong.sei.report.expression.parse.builds;

import com.changhong.sei.report.dsl.ReportParserParser;
import com.changhong.sei.report.expression.model.expr.BaseExpression;
import com.changhong.sei.report.expression.model.expr.StringExpression;

/**
 * @desc：字符串表达式构造器
 * @author：zhaohz
 * @date：2020/6/30 17:30
 */
public class StringExpressionBuilder implements ExpressionBuilder {
	@Override
	public BaseExpression build(ReportParserParser.UnitContext unitContext) {
		String text=unitContext.STRING().getText();
		text=text.substring(1,text.length()-1);
		StringExpression stringExpr=new StringExpression(text);
		return stringExpr;
	}
	@Override
	public boolean support(ReportParserParser.UnitContext unitContext) {
		return unitContext.STRING()!=null;
	}
}
