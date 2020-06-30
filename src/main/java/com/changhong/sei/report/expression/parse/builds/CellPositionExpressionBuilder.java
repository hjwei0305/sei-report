package com.changhong.sei.report.expression.parse.builds;

import com.changhong.sei.report.dsl.ReportParserParser;
import com.changhong.sei.report.expression.model.expr.BaseExpression;
import com.changhong.sei.report.expression.model.expr.CellPositionExpression;

/**
 * @desc：单元格位置表达式构造器
 * @author：zhaohz
 * @date：2020/6/30 15:40
 */
public class CellPositionExpressionBuilder implements ExpressionBuilder {

	@Override
	public BaseExpression build(ReportParserParser.UnitContext unitContext) {
		ReportParserParser.CellPositionContext ctx=unitContext.cellPosition();
		CellPositionExpression expr=new CellPositionExpression(ctx.Cell().getText());
		return expr;
	}

	@Override
	public boolean support(ReportParserParser.UnitContext unitContext) {
		return unitContext.cellPosition()!=null;
	}
}
