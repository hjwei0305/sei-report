package com.changhong.sei.report.expression.parse.builds;

import com.changhong.sei.report.dsl.ReportParserParser;
import com.changhong.sei.report.expression.model.expr.BaseExpression;
import com.changhong.sei.report.expression.model.expr.cell.CellObjectExpression;

/**
 * @desc：单元格表达式构造器
 * @author：zhaohz
 * @date：2020/6/30 15:37
 */
public class CellObjectExpressionBuilder implements ExpressionBuilder {

	@Override
	public BaseExpression build(ReportParserParser.UnitContext unitContext) {
		ReportParserParser.CellContext ctx=unitContext.cell();
		String property=null;
		ReportParserParser.PropertyContext propCtx=ctx.property();
		if(propCtx!=null){
			property=propCtx.getText();
		}
		CellObjectExpression expr=new CellObjectExpression(property);
		expr.setExpr(ctx.getText());
		return expr;
	}

	@Override
	public boolean support(ReportParserParser.UnitContext unitContext) {
		return unitContext.cell()!=null;
	}
}
