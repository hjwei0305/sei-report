package com.changhong.sei.report.expression.parse.builds;

import com.changhong.sei.report.dsl.ReportParserParser;
import com.changhong.sei.report.expression.model.expr.BaseExpression;
import com.changhong.sei.report.expression.model.expr.CurrentCellDataExpression;

/**
 * @desc：当前单元格数据表达式构造器
 * @author：zhaohz
 * @date：2020/6/30 15:41
 */
public class CurrentCellDataExpressionBuilder implements ExpressionBuilder {

	@Override
	public BaseExpression build(ReportParserParser.UnitContext unitContext) {
		ReportParserParser.CurrentCellDataContext context=unitContext.currentCellData();
		CurrentCellDataExpression expr=new CurrentCellDataExpression();
		expr.setProperty(context.property().getText());
		return expr;
	}

	@Override
	public boolean support(ReportParserParser.UnitContext unitContext) {
		return unitContext.currentCellData()!=null;
	}

}
