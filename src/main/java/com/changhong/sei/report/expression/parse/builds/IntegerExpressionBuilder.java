package com.changhong.sei.report.expression.parse.builds;

import com.changhong.sei.report.dsl.ReportParserParser;
import com.changhong.sei.report.expression.model.expr.BaseExpression;
import com.changhong.sei.report.expression.model.expr.IntegerExpression;

/**
 * @desc：整型表达式构造器
 * @author：zhaohz
 * @date：2020/6/30 17:24
 */
public class IntegerExpressionBuilder implements ExpressionBuilder {
	@Override
	public BaseExpression build(ReportParserParser.UnitContext unitContext) {
		Integer value=null;
		if(unitContext.INTEGER()!=null){
			value=Integer.valueOf(unitContext.INTEGER().getText());
		}
		return new IntegerExpression(value);
	}

	@Override
	public boolean support(ReportParserParser.UnitContext unitContext) {
		return unitContext.INTEGER()!=null;
	}

}
