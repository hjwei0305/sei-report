package com.changhong.sei.report.expression.parse.builds;

import com.changhong.sei.report.dsl.ReportParserParser;
import com.changhong.sei.report.expression.model.expr.BaseExpression;
import com.changhong.sei.report.expression.model.expr.CurrentCellValueExpression;

/**
 * @desc：当前单元格值表达式构造器
 * @author：zhaohz
 * @date：2020/6/30 15:41
 */
public class CurrentCellValueExpressionBuilder implements ExpressionBuilder {

	@Override
	public BaseExpression build(ReportParserParser.UnitContext unitContext) {
		return new CurrentCellValueExpression();
	}

	@Override
	public boolean support(ReportParserParser.UnitContext unitContext) {
		return unitContext.currentCellValue()!=null;
	}

}
