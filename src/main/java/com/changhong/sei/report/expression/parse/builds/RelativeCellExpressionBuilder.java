package com.changhong.sei.report.expression.parse.builds;

import com.changhong.sei.report.dsl.ReportParserParser;
import com.changhong.sei.report.expression.model.expr.BaseExpression;
import com.changhong.sei.report.expression.model.expr.RelativeCellExpression;

/**
 * @desc：关联单元格表达式构造器
 * @author：zhaohz
 * @date：2020/6/30 17:26
 */
public class RelativeCellExpressionBuilder implements ExpressionBuilder {

	@Override
	public BaseExpression build(ReportParserParser.UnitContext unitContext) {
		ReportParserParser.RelativeCellContext ctx=unitContext.relativeCell();
		RelativeCellExpression expr=new RelativeCellExpression(ctx.Cell().getText());
		return expr;
	}

	@Override
	public boolean support(ReportParserParser.UnitContext unitContext) {
		return unitContext.relativeCell()!=null;
	}
}
